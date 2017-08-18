package com.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.gui.ClientUI;
import com.gui.DialogImage;
import com.gui.LoginUI;
import com.model.ItemImage;
import com.model.User;
import com.socket.SocketClient;

public class Controller {
	//Configuration data
	public static String HOST_ADDRESS = "localhost";
	public static int HOST_PORT = 13000;
	
	//Client data
	public static String USERNAME = "";
	public static SocketClient client = null;
	public static Thread clientThread = null;

	//UI data
	public static LoginUI loginFrame = null;
	public static ClientUI MainFrame = null;
	public static ArrayList<String> listTheme = null;
	
	//Client thread
	private static boolean startClientThread(){
		if (client != null && clientThread != null)
			return true;
		
		try {
			client = new SocketClient("localhost", 13000);
			clientThread = new Thread(client);
			clientThread.start();
		} catch (IOException e) {
			stopClientThread();
			System.out.println(e);
			return false;
		}
		return true;
	}
	private static void stopClientThread(){
		if (client != null){
			client.close();
			client = null;
		}
		if (clientThread != null){
			clientThread.stop();
			clientThread = null;
		}
	}
	
	public static void closeProgram(){
		if (client != null)
			client.requestLogout();
		stopClientThread();
		System.exit(0);
	}
	
	//Upload
	public static void Upload(File file, String theme, String note){
		client.requestUpload(file, theme, note);
	}
	
	//Login
	public static boolean Login(String username, String password){
		if (!startClientThread())
			return false;
		
		USERNAME = username;
		client.requestLogin(username, password);
		return true;
	}
	public static void LoginSuccess(){
		loginFrame.setVisible(false);
		MainFrame = new ClientUI(USERNAME);
		MainFrame.setVisible(true);
		
		client.requestSearch("", "");
		client.requestThemeList();
		client.requestUserList();
	}
	
	//Logout
	public static void Logout(){
		MainFrame.setVisible(false);
		MainFrame = null;
		loginFrame.setVisible(true);
		client.requestLogout();
		stopClientThread();
	}
	
	//Register
	public static boolean Register(String username, String password){
		if (!startClientThread())
			return false;
		
		Controller.client.requestRegister(username, password);
		return true;
	}
	
	//Get data
	public static void initImageList(int total){
		MainFrame.initHolder(total);
	}
	public static void updateImageList(int index, String name, String author, 
			String theme, String note, String extension, int size, byte[] image){
		MainFrame.updateHolder(index, name, author, theme, note, extension, size, image);
	}
	public static void updateThemeList(ArrayList<String> list){
		MainFrame.panelFilter.updateThemeList(list);
	}
	public static void updateUserList(ArrayList<String> list){
		ArrayList<User> listUser = new ArrayList<User>();
		for (int i = 0; i < list.size(); i++){
			String tokens[] = list.get(i).split(";", -1);
			User user = new User(tokens[0], (tokens[1].equals("ONLINE") ? true : false));
			listUser.add(user);
		}
		MainFrame.panelFilter.updateUserList(listUser);
	}
	
	//Refresh
	public static void Search(String author, String theme){
		client.requestSearch(author, theme);
	}
	public static void RefreshUserList(){
		client.requestUserList();
	}
	public static void RefreshHistory(){
		MainFrame.clearHolder();
		Controller.Search(Controller.USERNAME, "");
	}
	
}
