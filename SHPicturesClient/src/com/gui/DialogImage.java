package com.gui;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.main.Controller;
import com.model.Dialog;
import com.model.ItemImage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DialogImage extends JDialog {

	private JLabel lblImage;
	private JTextField txtAuthor, txtFileSize;
	private JComboBox<String> cbTheme;
	private JTextArea txtNote;
	private JButton btnDownload;

	private ItemImage image;
	private File file;

	public DialogImage(String title, ItemImage image) {
		this.setTitle(title);
		this.image = image;
		this.addControl();
		this.addEvent();
		this.initContent();

		txtAuthor.setEnabled(false);
		txtFileSize.setEnabled(false);

		//Set dialog
		this.setMinimumSize(new Dimension(600, 420));
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

	private void addControl(){
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(128, 128, 128), 1, true), "Image View", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JLabel lblTheme = new JLabel("Theme:");
		lblTheme.setFont(new Font("iCiel Nabila", Font.PLAIN, 14));

		cbTheme = new JComboBox<String>();

		JLabel lblNote = new JLabel("Note:");
		lblNote.setFont(new Font("iCiel Samsung Sharp Sans Medium", Font.PLAIN, 12));

		JScrollPane scrollPane = new JScrollPane();

		btnDownload = new JButton("Download");

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(192, 192, 192), 1, true), "Information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

		JSeparator separator = new JSeparator();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 369, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblTheme)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(cbTheme, 0, 142, Short.MAX_VALUE))
										.addComponent(lblNote, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addComponent(separator, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
										.addComponent(btnDownload, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
										.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblTheme)
												.addComponent(cbTheme, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(7)
												.addComponent(lblNote, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnDownload)))
												.addContainerGap())
				);
		panel.setLayout(null);

		lblImage = new JLabel("Click to upload");
		lblImage.setFont(new Font("UTM Penumbra", Font.PLAIN, 20));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(10, 18, 350, 350);
		panel.add(lblImage);

		JLabel lblUploader = new JLabel("Uploader:");
		lblUploader.setFont(new Font("UTM Penumbra", Font.PLAIN, 11));

		txtAuthor = new JTextField();
		txtAuthor.setColumns(10);

		JLabel lblSize = new JLabel("File size:");
		lblSize.setFont(new Font("UTM Penumbra", Font.PLAIN, 11));

		txtFileSize = new JTextField();
		txtFileSize.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUploader)
								.addComponent(lblSize))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addComponent(txtFileSize, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
										.addComponent(txtAuthor, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
										.addContainerGap())
				);
		gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
						.addContainerGap(17, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblUploader)
								.addComponent(txtAuthor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblSize)
										.addComponent(txtFileSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addContainerGap())
				);
		panel_1.setLayout(gl_panel_1);

		txtNote = new JTextArea();
		scrollPane.setViewportView(txtNote);
		getContentPane().setLayout(groupLayout);
	}

	private void addEvent(){
		//Button upload/download
		if (image != null){
			btnDownload.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
					fileChooser.showDialog(DialogImage.this, "Select");
					File file = fileChooser.getSelectedFile();

					if(file != null){
						try{
							FileOutputStream fos = new FileOutputStream(file.getPath() + "/" + image.getName());
							fos.write(image.getImage());
							fos.close();
							Dialog.SuccessDownload();
						}
						catch (Exception ex){
							System.out.println("[EXCEPTION]DialogImage: " + ex);
							Dialog.InvalidDownload();
						}
						DialogImage.this.setVisible(false);
					}
				}
			});
		}
		else {
			btnDownload.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Controller.Upload(file, cbTheme.getSelectedItem().toString(), txtNote.getText());
					DialogImage.this.setVisible(false);
					Controller.RefreshHistory();
				}
			});
			lblImage.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
					fileChooser.showDialog(DialogImage.this, "Select");
					file = fileChooser.getSelectedFile();

					if (file != null){
						try {
							Image tmp = ImageIO.read(file);
							fitImage(tmp);
							txtFileSize.setText(file.length()/1024 + "KB");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
		}
	}

	private void initContent(){
		//User information
		txtAuthor.setText(Controller.USERNAME);
		//List theme
		for (int i = 0; i < Controller.listTheme.size(); i++){
			cbTheme.addItem(Controller.listTheme.get(i));
		}

		if (image != null){
			//Image content
			try{
				Image tmp = ImageIO.read(new ByteArrayInputStream(image.getImage()));
				this.fitImage(tmp);
			} catch (IOException e) {
				System.out.println("[EXCEPTION]Client: " + e);
			}
			//Image information
			txtFileSize.setText(image.getSize()/1024 + "KB");
			txtFileSize.setEnabled(false);
			cbTheme.setSelectedItem(image.getTheme());
			cbTheme.setEnabled(false);
			txtNote.setText(image.getNote());
			txtNote.setEnabled(false);
		}
		else{
			btnDownload.setText("Upload");
		}
	}

	private void fitImage(Image image){
		//Resize image
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		double scale = (width < height) ? height/340.0 : width/340.0;
		width /= scale;
		height /= scale;
		image = image.getScaledInstance(width, height,  Image.SCALE_SMOOTH);

		//Set to icon
		lblImage.setIcon(new ImageIcon(image));
		lblImage.setText("");
	}
}
