package com.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import com.main.Controller;
import com.model.ItemImage;

import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ClientUI extends JFrame {
	public JPanel panelTab, panelProfile, panelShow;
	private JButton btnProfile, btnExplore, btnUpload;
	public PanelFilter panelFilter;

	private ArrayList<PanelImage> holderImage = new ArrayList<PanelImage>();
	private ArrayList<ItemImage> listImage = new ArrayList<ItemImage>();

	public ClientUI(String title) {
		this.setTitle(title);
		this.setResizable(false);

		addControl();
		addEvent();

		this.setMinimumSize(new Dimension(960, 600));
		this.setLocationRelativeTo(null);
		
		//Explore page
		panelProfile.setVisible(false);
		btnExplore.setEnabled(false);
	}

	private void addControl(){
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_0 = new JPanel();
		getContentPane().add(panel_0, BorderLayout.NORTH);
		panel_0.setLayout(new BoxLayout(panel_0, BoxLayout.Y_AXIS));

		panelTab = new JPanel();
		panel_0.add(panelTab);

		btnProfile = new JButton("Profile");
		btnExplore = new JButton("Explore");
		GroupLayout gl_panelTab = new GroupLayout(panelTab);
		gl_panelTab.setHorizontalGroup(
				gl_panelTab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTab.createSequentialGroup()
						.addContainerGap()
						.addComponent(btnExplore, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addGap(801)
						.addComponent(btnProfile)
						.addContainerGap())
				);
		gl_panelTab.setVerticalGroup(
				gl_panelTab.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelTab.createSequentialGroup()
						.addGap(12)
						.addGroup(gl_panelTab.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnExplore, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
								.addComponent(btnProfile, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addContainerGap())
				);
		panelTab.setLayout(gl_panelTab);
		
		panelProfile = new JPanel();
		panel_0.add(panelProfile);
		
		JLabel lbProfileUsername = new JLabel(Controller.USERNAME);
		lbProfileUsername.setFont(new Font("iCiel Nabila", Font.PLAIN, 14));
		lbProfileUsername.setHorizontalAlignment(SwingConstants.CENTER);
		
		JSeparator separator = new JSeparator();
		
		btnUpload = new JButton("Upload Image");
		btnUpload.setFont(new Font("iCiel Samsung Sharp Sans Medium", Font.PLAIN, 12));
		GroupLayout gl_panelProfile = new GroupLayout(panelProfile);
		gl_panelProfile.setHorizontalGroup(
			gl_panelProfile.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelProfile.createSequentialGroup()
					.addGroup(gl_panelProfile.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelProfile.createSequentialGroup()
							.addContainerGap()
							.addComponent(separator, GroupLayout.DEFAULT_SIZE, 934, Short.MAX_VALUE))
						.addGroup(gl_panelProfile.createSequentialGroup()
							.addGap(327)
							.addComponent(lbProfileUsername, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelProfile.createSequentialGroup()
							.addGap(416)
							.addComponent(btnUpload)))
					.addContainerGap())
		);
		gl_panelProfile.setVerticalGroup(
			gl_panelProfile.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelProfile.createSequentialGroup()
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(9)
					.addComponent(lbProfileUsername, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnUpload, GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
					.addContainerGap())
		);
		panelProfile.setLayout(gl_panelProfile);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		scrollPane.setViewportView(panel_1);

		panelShow = new JPanel();
		panel_1.add(panelShow);
		panelShow.setLayout(new GridLayout(0, 5, 10, 20));

		panelFilter = new PanelFilter(ClientUI.this);
		getContentPane().add(panelFilter, BorderLayout.WEST);
	}

	private void addEvent(){
		//Change tab
		btnProfile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelFilter.setVisible(false);
				panelProfile.setVisible(true);
				panelShow.setLayout(new GridLayout(0, 6, 20, 20));
				btnProfile.setEnabled(false);
				btnExplore.setEnabled(true);
				
				clearHolder();
				Controller.Search(Controller.USERNAME, "");
			}
		});
		btnExplore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panelFilter.setVisible(true);
				panelProfile.setVisible(false);
				panelShow.setLayout(new GridLayout(0, 5, 10, 20));
				btnExplore.setEnabled(false);
				btnProfile.setEnabled(true);
				
				clearHolder();
				Controller.Search("", "");
			}
		});
		
		btnUpload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new DialogImage("Upload", null).show();
			}
		});
		
		//Close
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				Controller.closeProgram();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void clearHolder(){
		holderImage.clear();
		holderImage = new ArrayList<PanelImage>();
		listImage.clear();
		listImage = new ArrayList<ItemImage>();
		panelShow.removeAll();
	}
	public void initHolder(int total){
		for (int i = 0; i < total; i++){
			PanelImage imagePanel = new PanelImage(ClientUI.this);
			imagePanel.setPreferredSize(new Dimension(140, 210));
			holderImage.add(imagePanel);

			ItemImage imageItem = new ItemImage();
			listImage.add(imageItem);
			panelShow.add(imagePanel);
		}
	}
	public void updateHolder(int index, String name, String author, String theme, String note, String extension, int size, byte[] image){
		if (holderImage.size() == 0 || listImage.size() == 0)
			return;
		ItemImage itemImage = listImage.get(index);
		itemImage.setName(name);
		itemImage.setAuthor(author);
		itemImage.setTheme(theme);
		itemImage.setNote(note);
		itemImage.setExtension(extension);
		itemImage.setSize(size);
		itemImage.setImage(image);
		holderImage.get(index).updatePanel(listImage.get(index));
	}
}
