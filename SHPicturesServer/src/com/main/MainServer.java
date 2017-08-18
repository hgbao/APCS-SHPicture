package com.main;

import javax.swing.UIManager;

import com.gui.ServerUI;

public class MainServer {

	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){
			System.out.println("[EXCEPTION]Server: " + e);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ServerUI("SHPictures Server").setVisible(true);
			}
		});
	}

}
