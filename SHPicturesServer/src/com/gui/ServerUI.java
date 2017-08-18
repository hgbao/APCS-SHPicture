package com.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import com.socket.SocketServer;

import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class ServerUI extends JFrame {
	private JButton btnDeploy;
	private JButton btnBrowse;
	private JLabel jLabel3;
	private JScrollPane jScrollPane1;
	private JTextArea txtDescription;
	private JTextField txtDirectory;
	
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenu mnOption;

	private SocketServer server;
	private String database;

	public ServerUI(String title) {
		this.setTitle(title);
		this.addControl();
		this.addEvent();
		this.addMenu();

		btnDeploy.setEnabled(false);
		txtDirectory.setEditable(false);
		txtDirectory.setBackground(Color.WHITE);
		txtDescription.setEditable(false);	
	}

	private void addControl() {
		btnDeploy = new JButton();
		jScrollPane1 = new JScrollPane();
		txtDescription = new JTextArea();
		jLabel3 = new JLabel();
		txtDirectory = new JTextField();
		btnBrowse = new JButton();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		btnDeploy.setText("Start Server");

		txtDescription.setColumns(20);
		txtDescription.setFont(new java.awt.Font("Consolas", 0, 12));
		txtDescription.setRows(5);
		jScrollPane1.setViewportView(txtDescription);

		jLabel3.setText("Database Directory : ");

		btnBrowse.setText("Browse...");

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane1)
								.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addComponent(jLabel3)
										.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(txtDirectory, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btnBrowse, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(btnDeploy)))
										.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(txtDirectory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel3)
								.addComponent(btnBrowse)
								.addComponent(btnDeploy))
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
								.addContainerGap())
				);

		pack();
	}

	private void addEvent(){
		btnDeploy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				server = new SocketServer(ServerUI.this, 13000, database);
				btnDeploy.setEnabled(false);
				btnBrowse.setEnabled(false);
			}
		});

		btnBrowse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
				fileChooser.showDialog(ServerUI.this, "Select");
				File file = fileChooser.getSelectedFile();

				if(file != null){
					database = file.getPath();
					txtDirectory.setText(database);
					btnDeploy.setEnabled(true);
				}
			}
		});
	}

	private void addMenu(){
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mnOption = new JMenu("Option");
		menuBar.add(mnOption);
	}
	
	public void RetryStart(int port){
		if(server != null){
			server.stop();
		}
		server = new SocketServer(this, port, database);
	}
	
	public void showLog(String log){
		this.txtDescription.append(log + "\n");
	}

}
