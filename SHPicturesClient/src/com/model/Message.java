package com.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	public int requestID = 0;
	public String type, sender, content;
	
	public int holderIndex;
	
	public ArrayList<String> dataList;
	public byte[] fileContent;

	public Message(String type, String sender, String content){
		this.type = type;
		this.sender = sender;
		this.content = content;
	}
	
	public boolean isRegister(){
		return this.type.equalsIgnoreCase("register");
	}
	
	public boolean isLogin(){
		return this.type.equalsIgnoreCase("login");
	}

	public boolean isLogout(){
		return this.type.equalsIgnoreCase("logout");
	}
	
	public boolean isUploadRequest(){
		return this.type.equalsIgnoreCase("upload_req");
	}
	
	public boolean isUpload(){
		return this.type.equalsIgnoreCase("upload");
	}

	public boolean isDownload(){
		return this.type.equalsIgnoreCase("download");
	}
	
	public boolean isSearch(){
		return this.type.equalsIgnoreCase("search");
	}
	
	public boolean isThemeList(){
		return this.type.equalsIgnoreCase("theme_list");
	}
	
	public boolean isUserList(){
		return this.type.equalsIgnoreCase("user_list");
	}
	
	public boolean isSuccess(){
		return this.content.equalsIgnoreCase("TRUE");
	}

	@Override
	public String toString(){
		return "{type='"+type+"', sender='"+sender+"', content='"+content+"'}";
	}
}
