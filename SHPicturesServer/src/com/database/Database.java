package com.database;

import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

public class Database {
	public String directory;

	public Database(String directory){
		this.directory = directory;
	}

	//User database
	public boolean userExist(String username){
		try{
			File userlist = new File(directory + "/userlist.xml");
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(userlist);
			doc.getDocumentElement().normalize();

			NodeList list = doc.getElementsByTagName("user");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					if(getTagValue("username", element).equals(username)){
						return true;
					}
				}
			}
			return false;
		}
		catch(Exception e){
			System.out.println("[EXCEPTION]Database: " + e);
			return false;
		}
	}

	public boolean userLogin(String username, String password){
		try{
			File userlist = new File(directory + "/userlist.xml");
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(userlist);
			doc.getDocumentElement().normalize();

			NodeList list = doc.getElementsByTagName("user");

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					if(getTagValue("username", element).equals(username) && getTagValue("password", element).equals(password)){
						return true;
					}
				}
			}
			return false;
		}
		catch(Exception e){
			System.out.println("[EXCEPTION]Database: " + e);
			return false;
		}
	}

	public boolean addUser(String username, String password){
		//Add user to user list
		try {
			File userlist = new File(directory + "/userlist.xml");
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(userlist);

			Node data = doc.getFirstChild();
			//Add data
			Element newuser = doc.createElement("user");
			Element newusername = doc.createElement("username");
			newusername.setTextContent(username);
			Element newpassword = doc.createElement("password");
			newpassword.setTextContent(password);

			newuser.appendChild(newusername);
			newuser.appendChild(newpassword);
			data.appendChild(newuser);

			//Overwrite data
			TransformerFactory.newInstance().newTransformer().transform(
					new DOMSource(doc), 
					new StreamResult(userlist));

			//Last check
			if (!userExist(username))
				return false;
		} 
		catch(Exception e){
			System.out.println("[EXCEPTION]Database: " + e);
			return false;
		}

		//Create user data folder and files
		try{
			File userFile = new File(directory + "/dbUser/" + username + "/data.xml");
			userFile.getParentFile().mkdirs(); 
			userFile.createNewFile();

			BufferedWriter bw = new BufferedWriter(new FileWriter(userFile));
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
			bw.write("<data>\n</data>");
			bw.close();
		} catch(Exception e){
			System.out.println("[EXCEPTION]Database: " + e);
			return false;
		}
		return true;
	}
	
	//Get list
	private ArrayList<String> getList(String filePath, String tagName, String elementName){
		ArrayList<String> result = new ArrayList<String>();
		try{
			File themelist = new File(filePath);
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(themelist);
			doc.getDocumentElement().normalize();

			NodeList list = doc.getElementsByTagName(tagName);
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element item = (Element) node;
					result.add(getTagValue(elementName, item));
				}
			}
		}
		catch(Exception e){
			System.out.println("[EXCEPTION]Database: " + e);
			return null;
		}
		return result;
	}
	
	public ArrayList<String> getThemeList(){
		return getList(directory + "/themelist.xml", "theme", "name");
	}
	
	public ArrayList<String> getUserList(){
		return getList(directory + "/userlist.xml", "user", "username");
	}

	public ArrayList<String> getImageList(String author, String theme){
		ArrayList<String> result = new ArrayList<String>();

		//Search for all
		if (theme.isEmpty() && author.isEmpty()){
			try{
				//Get image list file
				File imagelist = new File(directory + "/imagelist.xml");
				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = dBuilder.parse(imagelist);
				doc.getDocumentElement().normalize();

				NodeList list = doc.getElementsByTagName("image");
				//Find the images
				for (int i = 0; i < list.getLength(); i++) {
					Node node = list.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element item = (Element) node;
						result.add("/dbUser/" + getTagValue("author", item) + "/" + getTagValue("name", item));
					}
				}
			}
			catch(Exception e){
				System.out.println("[EXCEPTION]Database: " + e);
				return null;
			}
		}

		//Search by author (and theme)
		if (!author.isEmpty() && userExist(author)){
			try{
				//Get image list file
				File userFile = new File(directory + "/dbUser/" + author + "/data.xml");
				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = dBuilder.parse(userFile);
				doc.getDocumentElement().normalize();

				NodeList list = doc.getElementsByTagName("image");

				//Find the folders by date
				for (int i = 0; i < list.getLength(); i++) {
					Node node = list.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element item = (Element) node;
						if (theme.isEmpty() || getTagValue("theme", item).equals(theme))
							result.add("/dbUser/" + author + "/" + getTagValue("name", item));
					}
				}
			}
			catch(Exception e){
				System.out.println("[EXCEPTION]Database: " + e);
				return null;
			}
		}
		
		//Search by theme
		if (!theme.isEmpty() && author.isEmpty()){
			try{
				//Get image list file
				File imagelist = new File(directory + "/imagelist.xml");
				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = dBuilder.parse(imagelist);
				doc.getDocumentElement().normalize();

				NodeList list = doc.getElementsByTagName("image");
				//Find the images
				for (int i = 0; i < list.getLength(); i++) {
					Node node = list.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element item = (Element) node;
						if (getTagValue("theme", item).equals(theme))
							result.add("/dbUser/" + getTagValue("author", item) + "/" + getTagValue("name", item));
					}
				}
			}
			catch(Exception e){
				System.out.println("[EXCEPTION]Database: " + e);
				return null;
			}
		}
		return result;
	}

	//Upload file
	public boolean upLoadImage(String fileAuthor, String fileTheme, String fileNote, String fileExtension, byte[] file){
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
		try{
			//Write file to user folder
			String fileName = fileAuthor + "_" + df.format(date) + "." + fileExtension;
			FileOutputStream streamOut = new FileOutputStream(directory + "/dbUser/" + fileAuthor + "/" + fileName);
			streamOut.write(file);
			streamOut.close();

			
			//Write file data to user data.xml
			File userFile = new File(directory + "/dbUser/" + fileAuthor + "/data.xml");
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(userFile);

			Node data = doc.getFirstChild();
			Element newImage = doc.createElement("image");
			Element iName = doc.createElement("name");
			iName.setTextContent(fileName);
			Element iAuthor = doc.createElement("author");
			iAuthor.setTextContent(fileAuthor);
			Element iTheme = doc.createElement("theme");
			iTheme.setTextContent(fileTheme);
			Element iNote = doc.createElement("note");
			iNote.setTextContent(fileNote);
			Element iExtension = doc.createElement("extension");
			iExtension.setTextContent(fileExtension);
			Element iSize = doc.createElement("size");
			iSize.setTextContent(file.length  + "");

			newImage.appendChild(iName);
			newImage.appendChild(iAuthor);
			newImage.appendChild(iTheme);
			newImage.appendChild(iNote);
			newImage.appendChild(iExtension);
			newImage.appendChild(iSize);
			data.insertBefore(newImage, data.getFirstChild());

			TransformerFactory.newInstance().newTransformer().transform(
					new DOMSource(doc), 
					new StreamResult(userFile));
			
			
			//Write file data to imagelist.xml
			File imagelist = new File(directory + "/imagelist.xml");
			doc = dBuilder.parse(imagelist);

			data = doc.getFirstChild();
			newImage = doc.createElement("image");
			iName = doc.createElement("name");
			iName.setTextContent(fileName);
			iAuthor = doc.createElement("author");
			iAuthor.setTextContent(fileAuthor);
			iTheme = doc.createElement("theme");
			iTheme.setTextContent(fileTheme);
			
			newImage.appendChild(iName);
			newImage.appendChild(iAuthor);
			newImage.appendChild(iTheme);
			data.insertBefore(newImage, data.getFirstChild());

			TransformerFactory.newInstance().newTransformer().transform(
					new DOMSource(doc), 
					new StreamResult(imagelist));
		}
		catch (Exception e){
			System.out.println("[EXCEPTION]Database: " + e);
			return false;
		}
		return true;
	}

	//Download file
	public byte[] getFileData(String filePath){
		try{
			File file = new File(directory + filePath);
			return Files.readAllBytes(file.toPath());
		}
		catch(Exception e){
			System.out.println("[EXCEPTION]Database: " + e);
		}
		return null;
	}

	public String getFileInformation(String filePath){
		String result = "";
		try{
			//Get user data file
			File file = new File(directory + filePath);
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dBuilder.parse(new File(file.getParentFile() + "/data.xml"));
			doc.getDocumentElement().normalize();

			NodeList list = doc.getElementsByTagName("image");

			//Get image information
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				Element item = (Element) node;
				if (node.getNodeType() == Node.ELEMENT_NODE && getTagValue("name", item).equals(file.getName())) {
					result += getTagValue("name", item) + ";";
					result += getTagValue("author", item) + ";";
					result += getTagValue("theme", item) + ";";
					result += getTagValue("note", item) + ";";
					result += getTagValue("extension", item) + ";";
					result += getTagValue("size", item);
				}
			}
		}
		catch (Exception e){
			System.out.println("[EXCEPTION]Database: " + e);
		}
		return result;
	}

	//Support function
	private static String getTagValue(String tag, Element element) {
		NodeList list = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node note = (Node) list.item(0);
		return note.getNodeValue();
	}
}
