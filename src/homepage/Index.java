package homepage;

import admin.*;
import instructor.InstructorSignup;
import instructor.LoginInstructor;
import student.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Index extends JFrame {

	public static Container contentPane;
	public static CardLayout cardLayout;
	public static JPanel homePanel;
	public static JMenu createGroup, groupDetails, memberDetails;
	public static JButton logoutButton;
	public static JMenuBar adminMenuBar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Index frame = new Index();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Index() {
		adminMenuBar = new JMenuBar();
		createGroup = new JMenu("Create Group");
		createGroup.setFont(new Font("Segoe UI", Font.BOLD, 14));
		groupDetails = new JMenu("Group Details");
		groupDetails.setFont(new Font("Segoe UI", Font.BOLD, 14));
		memberDetails = new JMenu("Member Details");
		memberDetails.setFont(new Font("Segoe UI", Font.BOLD, 14));
		adminMenuBar.add(createGroup);
		adminMenuBar.add(groupDetails);
		adminMenuBar.add(memberDetails);
		logoutButton = new JButton("Logout");
		logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
		adminMenuBar.add(logoutButton);
		setJMenuBar(adminMenuBar);
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Index.logoutButton) {
					int opt = JOptionPane.showConfirmDialog(contentPane, "Do you really want to logout? ", "Message",
							JOptionPane.YES_NO_OPTION);
					if (opt == 0) {
						cardLayout.show(contentPane, "home");
						adminMenuBar.setVisible(false);
					}
				}
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1500, 800);
		contentPane = getContentPane();
		cardLayout = new CardLayout();
		contentPane.setLayout(cardLayout);
		homePanel = new JPanel();

		JButton studentButton = new JButton("Student");
		studentButton.setForeground(new Color(0, 64, 128));
		studentButton.setBounds(968, 232, 276, 187);
		studentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPane, "studentlogin");
			}
		});
		homePanel.setLayout(null);
		studentButton.setFont(new Font("Dialog", Font.BOLD, 20));
		studentButton.setIcon(new ImageIcon(Index.class.getResource("../Images/index student.png")));
		homePanel.add(studentButton);

		JButton adminbtn = new JButton("Admin");
		adminbtn.setForeground(new Color(0, 64, 128));
		adminbtn.setBounds(204, 232, 276, 187);
		adminbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPane, "adminlogin");
			}
		});
		adminbtn.setIcon(new ImageIcon(Index.class.getResource("../Images/index admin.png")));
		adminbtn.setFont(new Font("Dialog", Font.BOLD, 20));
		homePanel.add(adminbtn);

		JButton instructorbtn = new JButton("Instructor");
		instructorbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new LoginInstructor().setVisible(true);
				// cardLayout.show(contentPane, "instructorlogin");
			}
		});
		instructorbtn.setFont(new Font("Dialog", Font.BOLD, 20));
		instructorbtn.setForeground(new Color(0, 64, 128));
		instructorbtn.setIcon(new ImageIcon(Index.class.getResource("../Images/index admin.png")));
		instructorbtn.setBounds(596, 232, 276, 187);
		homePanel.add(instructorbtn);

		JLabel titleLabel = new JLabel("EMS-EXAM MANAGEMENT SYSTEM");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		titleLabel.setForeground(new Color(64, 0, 128));
		titleLabel.setBounds(446, 47, 546, 36);
		homePanel.add(titleLabel);

		JPanel panel = new JPanel();
		panel.setBounds(114, 95, 1257, 566);
		homePanel.add(panel);
		panel.setLayout(null);

		JLabel subtitleLabel = new JLabel("SIGN IN TO PROCEED");
		subtitleLabel.setBounds(519, 57, 202, 22);
		panel.add(subtitleLabel);
		subtitleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		subtitleLabel.setForeground(new Color(0, 64, 128));

		JLabel checkingactLabel = new JLabel("Don't have an account?");
		checkingactLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		checkingactLabel.setForeground(new Color(128, 0, 64));
		checkingactLabel.setBounds(321, 383, 208, 46);
		panel.add(checkingactLabel);

		JButton signupbtn = new JButton("Sign Up");


		// Add an action listener to the sign-up button
		signupbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Display a dialog box with two options for instructor and student
				String[] options = { "Instructor", "Student" };
				int choice = JOptionPane.showOptionDialog(contentPane, "Choose your role:", "Sign Up",
						JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				// Perform different actions based on the user's choice
				if (choice == 0) {
					// If the user chooses instructor, do something
					setVisible(false);
					new InstructorSignup().setVisible(true);
					// cardLayout.show(contentPane, "instructorsignup");
				} else if (choice == 1) {

					cardLayout.show(contentPane, "studentsignup");
				}
			}
		});

		signupbtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		signupbtn.setBorderPainted(false);
		signupbtn.setBackground(new Color(0, 128, 255));
		signupbtn.setForeground(new Color(255, 255, 255));
		signupbtn.setBounds(539, 385, 202, 46);
		panel.add(signupbtn);

		AdminLogin adminLogin = new AdminLogin();
		GroupDetails groupDetails = new GroupDetails();
		MemberDetails memberDetails = new MemberDetails();
		AdminPanel adminPanel = new AdminPanel();
		
		InstructionStudent instructionStudent=new InstructionStudent();
		studentHome studentHome=new studentHome();
		StudentLogin studentLogin=new StudentLogin();
		StudentSignup studentSignup=new StudentSignup();
		// LoginInstructor loginInstructor=new LoginInstructor();
		// InstructorSignup instructorSignup=new InstructorSignup();
		// InstructorHome instructorHome=new InstructorHome();

		
		contentPane.add(adminLogin, "adminlogin");
		contentPane.add(homePanel, "home");
		contentPane.add(adminPanel, "adminhome");
		contentPane.add(groupDetails, "gpdetail");
		contentPane.add(memberDetails, "memdetail");
		contentPane.add(instructionStudent, "instructionstudent");
		contentPane.add(studentHome, "studenthome");
		contentPane.add(studentLogin, "studentlogin");
		contentPane.add(studentSignup, "studentsignup");		
		cardLayout.show(contentPane, "home");
		adminMenuBar.setVisible(false);

	}
}
