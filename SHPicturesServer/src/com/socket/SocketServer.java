package com.socket;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.StringTokenizer;

import com.database.Database;
import com.gui.ServerUI;
import com.model.Message;

public class SocketServer implements Runnable {
	private ServerSocket server = null;
	private int port;

	private int clientCount = 0;
	private ThreadServer clients[];
	private Thread thread = null;
	private ServerUI ui;
	private Database db;

	public SocketServer(ServerUI ui, int port, String database){
		this.clients = new ThreadServer[50];
		this.ui = ui;
		this.port = port;
		this.db = new Database(database);

		try{  
			this.server = new ServerSocket(port);
			this.port = server.getLocalPort();
			ui.showLog("Server IP: " + InetAddress.getLocalHost() + ", Port: " + this.server.getLocalPort());

			thread = new Thread(this); 
			thread.start();
		}
		catch(IOException e){  
			ui.showLog("[EXCEPTION]Server: SocketServer() - " + e); 
			ui.RetryStart(this.port);
		}
	}

	public void run(){  
		while (thread != null){  
			try{  
				ui.showLog("- Ready to connect..."); 
				addThread(server.accept()); 
			}
			catch(Exception e){ 
				System.out.println("[EXCEPTION]Server: accept() - " + e);
				ui.RetryStart(this.port);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void stop(){  
		if (thread != null){  
			thread.stop(); 
			thread = null;
		}
	}

	public synchronized void handle(int ID, Message msg){
		ThreadServer client = clients[findClient(ID)];

		if (msg.isLogin()){
			if (findClientThread(msg.sender) == null){
				if(db.userLogin(msg.sender, msg.content)){
					client.username = msg.sender;
					client.send(new Message("login", "SERVER", "TRUE"));
				}
				else
					client.send(new Message("login", "SERVER", "FALSE"));
			}
			else
				client.send(new Message("login", "SERVER", "FALSE"));
		}
		
		if (msg.isLogout()){
			removeThread(ID);
		}

		if(msg.isRegister()){
			if (!db.userExist(msg.sender) && db.addUser(msg.sender, msg.content)){
				client.send(new Message("register", "SERVER", "TRUE"));
			}
			else
				client.send(new Message("register", "SERVER", "FALSE"));
		}
		
		if (msg.isSearch()){
			Message message = new Message("search", "SERVER", "");
			String[] tokens = msg.content.split(";", -1);
			message.dataList = db.getImageList(tokens[0], tokens[1]);
			client.send(message);
		}
		
		if (msg.isDownload()){
			Message message = new Message("download", "SERVER", db.getFileInformation(msg.content));
			message.holderIndex = msg.holderIndex;
			message.fileContent = db.getFileData(msg.content);
			message.requestID = msg.requestID;
			client.send(message);
		}
		
		if (msg.isThemeList()){
			Message message = new Message("theme_list", "SERVER", "");
			message.dataList = db.getThemeList();
			client.send(message);
		}
		
		if (msg.isUserList()){
			Message message = new Message("user_list", "SERVER", "");
			message.dataList = db.getUserList();
			for (int i = 0; i < message.dataList.size(); i++){
				String username = message.dataList.get(i);
				if (findClientThread(username) != null)
					message.dataList.set(i, username + ";ONLINE");
				else
					message.dataList.set(i, username + ";OFFLINE");
			}
			client.send(message);
		}
		System.out.println(msg);
		if (msg.isUploadRequest()){
			client.send(new Message("upload_req", "SERVER", "TRUE"));
		}
		
		if (msg.isUpload()){
			System.out.println(1);
			String tokens[] = msg.content.split(";", -1);
			if (db.upLoadImage(msg.sender, tokens[0], tokens[1], tokens[2], msg.fileContent))
				client.send(new Message("upload", "SERVER", "TRUE"));
			else
				client.send(new Message("upload", "SERVER", "FALSE"));
		}
	}

	//Find clients
	private ThreadServer findClientThread(String username){
		for (int i = 0; i < clientCount; i++){
			if (clients[i].username.equals(username)){
				return clients[i];
			}
		}
		return null;
	}

	private int findClient(int ID){  
		for (int i = 0; i < clientCount; i++){
			if (clients[i].getID() == ID){
				return i;
			}
		}
		return -1;
	}

	//Add and remove thread
	public synchronized void removeThread(int ID){
		int pos = findClient(ID);
		ThreadServer thread = clients[pos];
		if (pos >= 0){
			//Remove from client list
			ui.showLog("Removing client thread " + ID + " at " + pos);
			if (pos < clientCount - 1){
				for (int i = pos+1; i < clientCount; i++){
					clients[i-1] = clients[i];
				}
			}
			clientCount--;

			//Close thread
			try{
				thread.close(); 
			}
			catch(IOException e){  
				System.out.println("[EXCEPTION]Server: removeThread() " + e); 
			}
			thread.stop();
		}
	}

	private void addThread(Socket socket){  
		if (clientCount < clients.length){  
			ui.showLog("Client connected: " + socket);
			clients[clientCount] = new ThreadServer(SocketServer.this, socket, this.ui);
			try{  
				clients[clientCount].open(); 
				clients[clientCount].start();  
				clientCount++; 
			}
			catch(Exception e){  
				System.out.println("[EXCEPTION]Server: addThread() - " + e); 
			} 
		}
		else{
			ui.showLog("[ERROR] Maximum number of clients reached...");
		}
	}
}
