package student;

import java.awt.EventQueue;

import javax.swing.JPanel;
import homepage.Index;
import ems.ConnectionProvider;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;

public class StudentLogin extends JPanel {

	
	private JTextField usernameField;
	private JPasswordField passwordField;
	public static String email;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentLogin frame = new StudentLogin();
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
	public StudentLogin() {
		setBackground(new Color(232, 208, 208));
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1270, 767);
		// contentPane = new JPanel();
		// setBorder(new EmptyBorder(5, 5, 5, 5));

		// setContentPane(contentPane);
		// contentPane.setLayout(null);
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(232, 208, 208));
		panel.setBounds(0, 10, 1256, 710);
		panel.setLayout(null);

		JLabel usernamelbl = new JLabel("Email");
		usernamelbl.setBounds(475, 226, 93, 27);
		usernamelbl.setForeground(new Color(0, 64, 128));
		usernamelbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(usernamelbl);

		usernameField = new JTextField();
		usernameField.setBounds(383, 263, 323, 37);
		panel.add(usernameField);
		usernameField.setColumns(10);

		JLabel passwordlbl = new JLabel("Password");
		passwordlbl.setBounds(475, 300, 116, 43);
		passwordlbl.setForeground(new Color(0, 64, 128));
		passwordlbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(passwordlbl);

		passwordField = new JPasswordField();
		passwordField.setBounds(383, 353, 323, 37);
		panel.add(passwordField);

		JLabel titleLabel = new JLabel("Student Login Page");
		titleLabel.setBackground(new Color(0, 64, 128));
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
		titleLabel.setForeground(new Color(0, 64, 128));
		titleLabel.setBounds(383, 44, 367, 50);
		panel.add(titleLabel);

		JLabel iconlbl = new JLabel("");
		iconlbl.setIcon(new ImageIcon(StudentLogin.class.getResource("../Images/index student.png")));
		iconlbl.setBounds(482, 92, 109, 124);
		panel.add(iconlbl);

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
		panel.add(chkboxlbl);

		JButton loginbtn = new JButton("Login");
		loginbtn.setForeground(new Color(255, 255, 255));
		loginbtn.setBackground(new Color(0, 128, 192));
		loginbtn.setBorderPainted(false);
		// loginbtn.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// if(usernameField.getText().equals("student_id") &&
		// passwordField.getText().equals("password")) {
		// setVisible(false);
		// new InstructorHome().setVisible(true);
		// }
		// else {
		// ImageIcon icon = new ImageIcon("../Images/Incorrect Password.png");
		// JOptionPane.showMessageDialog(null, "<html><b style=\"color:red; font-size:
		// 10px\">Incorrect
		// Credential</b></html>","Show",JOptionPane.INFORMATION_MESSAGE,icon);
		// }
		// }
		// });
		// loginbtn.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// if(usernameField.getText().equals("student") &&
		// passwordField.getText().equals("password")) {
		//
		// }
		// });

		loginbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				email = usernameField.getText();
				String password = new String(passwordField.getPassword());

				// query the student table to check if the email and password are correct
				boolean isCredentialsValid = false;
				try {
					Connection con = ConnectionProvider.getCon();
					PreparedStatement stmt = con
							.prepareStatement("SELECT * FROM student WHERE email = ? AND password = ?");
					stmt.setString(1, email);
					stmt.setString(2, password);
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						isCredentialsValid = true;
					}
					con.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}

				if (isCredentialsValid) {
					// setVisible(false);

					// Display student options
					// setVisible(false);
					// Display student options

					Index.cardLayout.show(Index.contentPane, "studenthome");
				} else {
					ImageIcon icon = new ImageIcon("../Images/Incorrect Password.png");
					JOptionPane.showMessageDialog(null,
							"<html><b style=\"color:red; font-size: 10px\">Incorrect Credential</b></html>", "Show",
							JOptionPane.INFORMATION_MESSAGE, icon);
				}

			}
		});

		loginbtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		loginbtn.setIcon(new ImageIcon(StudentLogin.class.getResource("../Images/login.png")));
		loginbtn.setBounds(440, 461, 109, 29);
		panel.add(loginbtn);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new Index().setVisible(true);
			}
		});
		btnBack.setIcon(new ImageIcon(StudentLogin.class.getResource("../Images/Back.png")));
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBack.setBounds(559, 461, 109, 29);
		panel.add(btnBack);
		add(panel);
	}
}
