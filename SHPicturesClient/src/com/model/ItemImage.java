package com.model;

public class ItemImage {
	private String name;
	private String theme;
	private String author;
	private String note;
	private String extension;
	private int size;
	private byte[] image;
	
	public ItemImage(){
		super();
		this.name = "";
		this.theme = "";
		this.author = "";
		this.note = "";
		this.extension = "";
		this.image = null;
	}
	
	public ItemImage(String name, String theme, String author, String note,
			String extension, int size, byte[] image) {
		this.name = name;
		this.theme = theme;
		this.author = author;
		this.note = note;
		this.extension = extension;
		this.size = size;
		this.image = image;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public byte[] getImage() {
		return image;
	}
	public int getSize(){
		return this.size;
	}
	public void setSize(int size){
		this.size = size;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
}
