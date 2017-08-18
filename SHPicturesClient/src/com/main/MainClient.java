package com.main;

import javax.swing.UIManager;

import com.gui.DialogImage;
import com.gui.LoginUI;

public class MainClient {

	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
			System.out.println("[EXCEPTION]Client: " + e);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	Controller.loginFrame = new LoginUI("SHPictures Client");
            	Controller.loginFrame.setVisible(true);
            }
        });
	}
}