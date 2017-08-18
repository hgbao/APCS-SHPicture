package com.socket;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;

import com.main.Controller;
import com.model.Dialog;
import com.model.Message;

public class SocketClient implements Runnable{
	private Socket socket = null;
	
	private ObjectInputStream streamIn = null;
	private ObjectOutputStream streamOut = null;
	
	private int currentDownload = -1;
	
	private byte[] pendingFile = null;
	private Message pendingMessage = null;

	public SocketClient(String serverAddr, int serverPort) throws IOException{
		socket = new Socket(InetAddress.getByName(serverAddr), serverPort);

		streamOut = new ObjectOutputStream(socket.getOutputStream());
		streamOut.flush();
		streamIn = new ObjectInputStream(socket.getInputStream());
	}
	
	public void close(){
		try {
			if (socket != null)
				socket.close();
			if (streamIn != null)
				streamIn.close();
			if (streamOut != null)
				streamOut.close();
		} catch (IOException e) {
			System.out.println("[EXCEPTION]Client: " + e);
		}
	}

	@Override
	public void run() {
		while(true){
			try {
				Message msg = (Message) streamIn.readObject();
                System.out.println("Incoming: "+ msg.toString());

				if(msg.isLogin()){
					if(msg.isSuccess())
						Controller.LoginSuccess();
					else
						Dialog.InvalidLogin();
				}
				
				if(msg.isRegister()){
					if(msg.isSuccess())
						Dialog.SuccessRegister();
					else
						Dialog.InvalidRegister();
				}
				
				if (msg.isSearch()){
					currentDownload++;
					Controller.initImageList(msg.dataList.size());
					for (int i = 0; i < msg.dataList.size(); i++){
						Message message = new Message("download", "", msg.dataList.get(i));
						message.holderIndex = i;
						message.requestID = currentDownload;
						send(message);
					}
				}

				if (msg.isDownload()){
					if (msg.requestID == currentDownload){
						String tokens[] = msg.content.split(";", -1);
						Controller.updateImageList(msg.holderIndex, 
								tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]),
								msg.fileContent);
					}
				}
				
				if (msg.isThemeList()){
					Controller.listTheme = msg.dataList;
					Controller.updateThemeList(msg.dataList);
				}
				
				if (msg.isUserList()){
					Controller.updateUserList(msg.dataList);
				}
				
				if (msg.isUploadRequest()){
					if (msg.isSuccess()){
						Message message = new Message("upload", Controller.USERNAME, pendingMessage.content);
						message.fileContent = pendingFile;	
						send(message);
					}
					else
						Dialog.InvalidUpload();
					pendingMessage = null;
					pendingFile = null;
				}
				
				if (msg.isUpload()){
					if (msg.isSuccess())
						Dialog.SuccessUpload();
					else
						Dialog.InvalidUpload();
				}
			}
			catch(Exception e) {
				System.out.println("[EXCEPTION]Client: " + e);
				break;
			}
		}
	}

	public void send(Message msg){
		try {
			streamOut.writeObject(msg);
			streamOut.flush();
			System.out.println("Outgoing: " + msg.toString());
		} 
		catch (IOException e) {
			System.out.println("[EXCEPTION]Client: " + e);
		}
	}
	
	public void requestLogin(String username, String password){
		send(new Message("login", username, password));
	}
	
	public void requestLogout(){
		send(new Message("logout", Controller.USERNAME, ""));
	}
	
	public void requestRegister(String username, String password){
		send(new Message("register", username, password));
	}
	
	public void requestSearch(String author, String theme){
		send(new Message("search", Controller.USERNAME, author + ";" + theme));
	}
	
	public void requestUpload(File file, String theme, String note){
		try {
			Message message = new Message("upload_req", Controller.USERNAME, 
					theme + ";" + note + ";" + file.getName().substring(file.getName().lastIndexOf(".") + 1));
			pendingFile = Files.readAllBytes(file.toPath());
			pendingMessage = message;
			send(message);
		} catch (IOException e) {
			System.out.println("[EXCEPTION]Client: " + e);
		}
	}
	public void requestThemeList(){
		send(new Message("theme_list", Controller.USERNAME, ""));
	}
	public void requestUserList(){
		send(new Message("user_list", Controller.USERNAME, ""));
	}
}
