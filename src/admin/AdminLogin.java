package admin;

import java.awt.EventQueue;

import javax.swing.JPanel;
import homepage.Index;
import ems.ConnectionProvider;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;

public class AdminLogin extends JPanel {

	// private JTextField usernameField;
	private JPasswordField passwordField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminLogin frame = new AdminLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminLogin() {
		setBackground(new Color(232, 208, 208));

		setBounds(0, 10, 1256, 710);
		setLayout(null);
		JLabel passwordlbl = new JLabel("Password");
		passwordlbl.setBounds(475, 300, 116, 43);
		passwordlbl.setForeground(new Color(0, 64, 128));
		passwordlbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(passwordlbl);

		passwordField = new JPasswordField();
		passwordField.setBounds(383, 353, 323, 37);
		add(passwordField);

		JLabel titleLabel = new JLabel("Admin Login Page");
		titleLabel.setBackground(new Color(0, 64, 128));
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
		titleLabel.setForeground(new Color(0, 64, 128));
		titleLabel.setBounds(383, 44, 367, 50);
		add(titleLabel);

		JLabel iconlbl = new JLabel("");
		iconlbl.setIcon(new ImageIcon(AdminLogin.class.getResource("../Images/index admin.png")));
		iconlbl.setBounds(482, 92, 109, 124);
		add(iconlbl);

		JCheckBox chkboxlbl = new JCheckBox("Show Password");
		chkboxlbl.setForeground(new Color(0, 64, 128));
		chkboxlbl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkboxlbl.isSelected()) {
					passwordField.setEchoChar((char) 0);
				} else {
					passwordField.setEchoChar('*');
				}
			}
		});
		chkboxlbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		chkboxlbl.setBounds(445, 408, 161, 21);
		add(chkboxlbl);

		JButton loginbtn = new JButton("Login");
		loginbtn.setForeground(new Color(255, 255, 255));
		loginbtn.setBackground(new Color(0, 128, 192));
		loginbtn.setBorderPainted(false);
		loginbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String password = new String(passwordField.getPassword());
				boolean isCredentialsValid = false;
				try {
					Connection con = ConnectionProvider.getCon();
					PreparedStatement stmt = con.prepareStatement("SELECT * FROM admin WHERE password = ?");
					stmt.setString(1, password);
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						isCredentialsValid = true;
					}
					con.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}

				if (isCredentialsValid) {
					Index.cardLayout.show(Index.contentPane, "adminhome");
					Index.adminMenuBar.setVisible(true);

				} else {
					ImageIcon icon = new ImageIcon("../Images/Incorrect Password.png");
					JOptionPane.showMessageDialog(null,
							"<html><b style=\"color:red; font-size: 10px\">Incorrect Credential</b></html>", "Show",
							JOptionPane.INFORMATION_MESSAGE, icon);
				}

			}
		});

		loginbtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		loginbtn.setIcon(new ImageIcon(AdminLogin.class.getResource("../Images/login.png")));
		loginbtn.setBounds(440, 461, 109, 29);
		add(loginbtn);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Index.cardLayout.show(Index.contentPane, "home");
			}
		});
		btnBack.setIcon(new ImageIcon(AdminLogin.class.getResource("../Images/Back.png")));
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBack.setBounds(559, 461, 109, 29);
		add(btnBack);
	}
}
