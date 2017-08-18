package com.gui;

import java.awt.Image;
import java.awt.Panel;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.model.ItemImage;

import java.awt.FlowLayout;

import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;

import java.awt.event.MouseAdapter;

public class PanelImage extends Panel {
	private JPanel panelImage;
	private JLabel lbImage, lbUsername, lbTheme;
	private ItemImage image;

	private ClientUI mainUI;
	
	public PanelImage(ClientUI mainUI) {
		this.mainUI = mainUI;
		
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(10);
		addControl();
	}

	private void addControl(){
		JPanel panel_0 = new JPanel();
		panelImage = new JPanel();
		panelImage.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.GRAY));
		lbUsername = new JLabel("Loading");
		lbUsername.setForeground(Color.GRAY);
		lbUsername.setFont(new Font("iCiel Samsung Sharp Sans Bold", Font.PLAIN, 14));
		GroupLayout groupLayout = new GroupLayout(panel_0);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panelImage, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(55, Short.MAX_VALUE)
					.addComponent(lbUsername)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panelImage, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lbUsername)
					.addGap(5))
		);
		panelImage.setLayout(null);

		lbTheme = new JLabel("Loading");
		lbTheme.setForeground(Color.WHITE);
		lbTheme.setOpaque(true);
		lbTheme.setBackground(new Color(0, 0, 0, 150));
		lbTheme.setHorizontalAlignment(SwingConstants.CENTER);
		lbTheme.setBounds(0, 130, 120, 30);
		panelImage.add(lbTheme);
		lbTheme.setFont(new Font("iCiel Nabila", Font.PLAIN, 14));

		lbImage = new JLabel("");
		lbImage.setBounds(0, 0, 120, 160);
		panelImage.add(lbImage);
		panel_0.setLayout(groupLayout);
		this.add(panel_0);
	}

	public void updatePanel(ItemImage image){
		this.lbUsername.setText(image.getAuthor());
		this.lbTheme.setText(image.getTheme());

		//Resize image
		
		try {
			Image tmp = ImageIO.read(new ByteArrayInputStream(image.getImage()));
			int width = tmp.getWidth(null);
			int height = tmp.getHeight(null);
			double scale = (width/120.0 > height/160.0) ? height/160.0 : width/120.0;
			width /= scale;
			height /= scale;
			tmp = tmp.getScaledInstance(width, height,  Image.SCALE_SMOOTH);

			//Set to icon
			this.lbImage.setIcon(new ImageIcon(tmp));
		} catch (IOException e) {
			System.out.println("[EXCEPTION]Client: " + e);
		}
		
		lbImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new DialogImage(image.getName(), image).show();
			}
		});
	}
}
