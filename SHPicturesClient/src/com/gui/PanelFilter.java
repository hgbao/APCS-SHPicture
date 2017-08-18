package com.gui;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextField;
import javax.swing.JList;

import com.main.Controller;
import com.model.ItemImage;
import com.model.User;

import javax.swing.LayoutStyle.ComponentPlacement;

public class PanelFilter extends JPanel {
	private JComboBox<String> cbTheme;
	private JTextField txtFilterUser;
	private JList<User> jListUser;
	private JButton btnSearch, btnRefresh;

	private ClientUI mainUI;

	public PanelFilter(ClientUI mainUI) {
		this.mainUI = mainUI;
		this.addControl();
		this.addEvent();

	}

	private void addControl(){
		this.setBorder(new MatteBorder(1, 0, 0, 0, (Color) Color.GRAY));

		JLabel label = new JLabel("Theme:");
		label.setFont(new Font("iCiel Nabila", Font.PLAIN, 14));

		cbTheme = new JComboBox<String>();

		btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("iCiel Samsung Sharp Sans Medium", Font.PLAIN, 11));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "User", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		txtFilterUser = new JTextField();
		txtFilterUser.setColumns(10);

		jListUser = new JList<User>();
		
		btnRefresh = new JButton("Refresh");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(jListUser, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addComponent(txtFilterUser, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnRefresh))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtFilterUser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRefresh))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jListUser, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		GroupLayout gl_panel = new GroupLayout(this);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 185, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(label)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(cbTheme, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
								.addContainerGap()
								.addComponent(btnSearch, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
								.addContainerGap())
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
				);
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 523, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
						.addGap(15)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(label)
								.addComponent(cbTheme, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(10)
								.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
				);
		this.setLayout(gl_panel);
	}

	private void addEvent(){
		//User list
		jListUser.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					if (jListUser.isEnabled())
						txtFilterUser.setText(jListUser.getSelectedValue().getUsername());
				}
			}
		});

		//Search
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainUI.clearHolder();
				String theme = cbTheme.getSelectedItem().toString();
				String author = txtFilterUser.getText();
				Controller.Search(author, theme);
			}
		});
		
		//Refresh user list
		btnRefresh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.RefreshUserList();
			}
		});
	}

	public void updateThemeList(ArrayList<String> list){
		cbTheme.addItem("");
		for (int i = 0; i < list.size(); i++){
			cbTheme.addItem(list.get(i));
		}
	}
	public void updateUserList(ArrayList<User> list){
		txtFilterUser.setText("");
		User[] array = new User[list.size()];
		list.toArray(array);
		
		jListUser.setEnabled(false);
		jListUser.setListData(array);
		jListUser.setEnabled(true);
	}
}
