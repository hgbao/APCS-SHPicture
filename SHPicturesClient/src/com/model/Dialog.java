package com.model;

import javax.swing.JOptionPane;

public class Dialog {
	//Error
	private static void createErrorDialog(String message){
		JOptionPane.showMessageDialog(null, message, "Error!",
				JOptionPane.ERROR_MESSAGE);
	}
	public static void InvalidNetwork(){
		createErrorDialog("Cannot connect to server");
	}
	public static void InvalidLogin(){
		createErrorDialog("Wrong/Invalid username or password");
	}
	public static void InvalidRegister() {
		createErrorDialog("Username/password are invalid or already existed");
	}
	public static void InvalidUpload(){
		createInformationDialog("Failed to upload file");
	}
	public static void InvalidDownload(){
		createInformationDialog("Failed to download file");
	}
	
	
	//Success
	private static void createInformationDialog(String message){
		JOptionPane.showMessageDialog(null, message, "Success!",
				JOptionPane.INFORMATION_MESSAGE);
	}
	public static void SuccessRegister(){
		createInformationDialog("Register new account successfully");
	}
	public static void SuccessUpload(){
		createInformationDialog("Upload file successfully");
	}
	public static void SuccessDownload(){
		createInformationDialog("Download file successfully");
	}

	
	//Confirm
	private static boolean createConfirmDialog(String message){
		int option = JOptionPane.showConfirmDialog(null, message, "Confirm",
				JOptionPane.OK_CANCEL_OPTION);
		return (option == JOptionPane.OK_OPTION);
	}
	public static boolean ConfirmExit(){
		return createConfirmDialog("Continue without saving?");
	}
}
