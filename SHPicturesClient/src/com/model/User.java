package com.model;

public class User {
	private String username;
	private boolean online;
	
	public User(String username, boolean online) {
		super();
		this.username = username;
		this.online = online;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
	
	@Override
	public String toString() {
		return username + " - " + (online ? "Online" : "Offline");
	}
}
