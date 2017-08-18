package com.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.gui.ServerUI;
import com.model.Message;

class ThreadServer extends Thread { 
	private SocketServer server;
	private Socket socket;

	private int ID = -1;
	public String username = "";

	private ObjectInputStream streamIn;
	private ObjectOutputStream streamOut;
	private ServerUI ui;

	public ThreadServer(SocketServer server, Socket socket, ServerUI ui){  
		super();
		this.server = server;
		this.socket = socket;
		this.ui = ui;
		ID = socket.getPort();
	}

	public void send(Message msg){
		try {
			streamOut.writeObject(msg);
			streamOut.flush();
		} 
		catch (IOException e) {
			System.out.println("[EXCEPTION]Server thread " + ID + ": " + e);
		}
	}

	public int getID(){  
		return ID;
	}

	@SuppressWarnings("deprecation")
	public void run(){
		ui.showLog("Server thread " + ID + " is running.");
		while (true){
			try{
				Message msg = (Message) streamIn.readObject();
				server.handle(ID, msg);
			}
			catch(Exception e){
				System.out.println("[EXCEPTION]Server thread " + ID + ": " + e);
				server.removeThread(ID);
				stop();
			}
		}
	}

	public void open() throws IOException {  
		streamOut = new ObjectOutputStream(socket.getOutputStream());
		streamOut.flush();
		streamIn = new ObjectInputStream(socket.getInputStream());
	}

	public void close() throws IOException {  
		if (socket != null)
			socket.close();
		if (streamIn != null)
			streamIn.close();
		if (streamOut != null)
			streamOut.close();
	}
}