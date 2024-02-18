package student;

import homepage.Index;
import java.sql.*;
import ems.ConnectionProvider;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPasswordField;

public class StudentSignup extends JPanel {

	// private JPanel contentPane;
	private JTextField studentIdTextField, studentNameTextField, phoneTextField, emailTextField, txtYourInstitute,
			txtYourDepartment, passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentSignup frame = new StudentSignup();
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
	public StudentSignup() {

		setBounds(0, 0, 1366, 768);
		// contentPane = new JPanel();
		// setBorder(new EmptyBorder(5, 5, 5, 5));

		// setContentPane(contentPane);
		setLayout(null);

		JLabel studentIconLabel = new JLabel("");
		studentIconLabel.setIcon(new ImageIcon(StudentSignup.class.getResource("../Images/index student.png")));
		studentIconLabel.setBounds(10, 10, 75, 65);
		add(studentIconLabel);

		JLabel formTitleLabel = new JLabel("Fill Up the Form");
		formTitleLabel.setFont(new Font("Agency FB", Font.BOLD, 40));
		formTitleLabel.setBounds(80, 10, 262, 54);
		add(formTitleLabel);

		JLabel dateLabel = new JLabel("Date");
		dateLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		dateLabel.setBounds(653, 31, 59, 25);
		add(dateLabel);

		JLabel dateDispaly = new JLabel("Time");
		dateDispaly.setFont(new Font("Tahoma", Font.BOLD, 20));
		dateDispaly.setBounds(721, 33, 246, 25);
		add(dateDispaly);

		// Event Handler for back BUTTOn

		JButton backButtonLabel = new JButton("Back");
		backButtonLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		backButtonLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// setVisible(false); // hiding the student detail page
				// new Index().setVisible(true); // going back to homepage
				Index.cardLayout.show(Index.contentPane, "home");
			}
		});
		backButtonLabel.setIcon(new ImageIcon(StudentSignup.class.getResource("../Images/Back.png")));
		backButtonLabel.setBounds(1107, 6, 109, 57);
		add(backButtonLabel);

		JButton closeButtonLabel = new JButton("");
		closeButtonLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int a = JOptionPane.showConfirmDialog(null, "Are sure you want to exit?", "Select",
						JOptionPane.YES_NO_OPTION);
				if (a == 0) {
					System.exit(0);
				}
			}
		});
		closeButtonLabel.setIcon(new ImageIcon(StudentSignup.class.getResource("../Images/Close.png")));
		closeButtonLabel.setBounds(1284, 0, 82, 64);
		add(closeButtonLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 74, 1346, 2);
		add(separator);

		JLabel studentIdLabel = new JLabel("Student ID");
		studentIdLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		studentIdLabel.setBounds(80, 103, 116, 47);
		add(studentIdLabel);

		JLabel studentNameLabel = new JLabel("Student Name :");
		studentNameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		studentNameLabel.setBounds(80, 160, 116, 47);
		add(studentNameLabel);

		JLabel lblGender = new JLabel("Gender :");
		lblGender.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblGender.setBounds(80, 217, 116, 47);
		add(lblGender);

		JLabel lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPhoneNumber.setBounds(80, 274, 116, 47);
		add(lblPhoneNumber);

		JLabel lblEmail = new JLabel("E-mail :");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmail.setBounds(80, 331, 116, 47);
		add(lblEmail);

		studentIdTextField = new JTextField();
		studentIdTextField.setFont(new Font("Dialog", Font.BOLD, 15));
		studentIdTextField.setBounds(214, 103, 371, 35);
		add(studentIdTextField);
		studentIdTextField.setColumns(10);

		studentNameTextField = new JTextField();
		studentNameTextField.setFont(new Font("Dialog", Font.BOLD, 15));
		studentNameTextField.setColumns(10);
		studentNameTextField.setBounds(214, 157, 371, 35);
		add(studentNameTextField);

		phoneTextField = new JTextField();
		phoneTextField.setFont(new Font("Dialog", Font.BOLD, 15));
		phoneTextField.setBounds(214, 274, 371, 35);
		add(phoneTextField);

		emailTextField = new JTextField();
		emailTextField.setFont(new Font("Dialog", Font.BOLD, 15));
		emailTextField.setColumns(10);
		emailTextField.setBounds(214, 347, 371, 35);
		add(emailTextField);

		JComboBox<String> genderComboBox = new JComboBox<>();
genderComboBox.setFont(new Font("Tahoma", Font.BOLD, 14));
genderComboBox.setModel(new DefaultComboBoxModel<>(new String[] {
  "---Select Your gender---", "Male", "Female", "Other"
}));
genderComboBox.setBounds(214, 217, 371, 36);
add(genderComboBox);


		// Event Handler for save and next

		JButton saveButtonLabel = new JButton("Save");
		saveButtonLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sid = studentIdTextField.getText();
				String name = studentNameTextField.getText();
				String gender = (String) genderComboBox.getSelectedItem(); // to get selected item from option
				String phone_no = phoneTextField.getText();
				String email = emailTextField.getText();
				String institution = txtYourInstitute.getText();
				String department = txtYourDepartment.getText();
				String password = passwordField.getText();
				String marks = "0";

				try {
					// INSERTING VALUES STUDENT FILL IN FORM INTO student TABLE
					Connection con = ConnectionProvider.getCon();
					PreparedStatement ps = con.prepareStatement("insert into student values(?,?,?,?,?,?,?,?,?)");
					ps.setString(1, sid);
					ps.setString(2, name);
					ps.setString(3, gender);
					ps.setString(4, phone_no);

					ps.setString(5, email);
					ps.setString(6, institution);
					ps.setString(7, department);
					ps.setString(8, marks);

					ps.setString(9, password);

					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "Succesfully Registered!");
					// setVisible(false);

					// new StudentLogin().setVisible(true);
					Index.cardLayout.show(Index.contentPane, "studentlogin");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Something went wrong while inserting data into student table");
				}
			}
		});
		saveButtonLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		saveButtonLabel.setIcon(new ImageIcon(StudentSignup.class.getResource("../Images/save.png")));
		saveButtonLabel.setBounds(333, 608, 234, 47);
		add(saveButtonLabel);

		JLabel lblInstitution = new JLabel("Institution");
		lblInstitution.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblInstitution.setBounds(80, 403, 116, 47);
		add(lblInstitution);

		txtYourInstitute = new JTextField();

		txtYourInstitute.setFont(new Font("Dialog", Font.BOLD, 15));
		txtYourInstitute.setColumns(10);
		txtYourInstitute.setBounds(214, 403, 371, 35);
		add(txtYourInstitute);

		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDepartment.setBounds(80, 460, 116, 47);
		add(lblDepartment);

		txtYourDepartment = new JTextField();

		txtYourDepartment.setFont(new Font("Dialog", Font.BOLD, 15));
		txtYourDepartment.setBounds(214, 460, 371, 35);
		add(txtYourDepartment);

		JTextArea infoTextArea = new JTextArea();
		infoTextArea.setBackground(new Color(192, 192, 192));
		infoTextArea.setWrapStyleWord(true);
		infoTextArea.setLineWrap(true);
		infoTextArea.setFont(new Font("Cambria", Font.BOLD, 16));
		infoTextArea.setText(
				"Dear Student,\r\n\r\nThank you for choosing our exam management system to register for your exam. "
						+ "Please read and understand the exam requirements and guidelines before registering."
						+ " Ensure that you have all the necessary information and documents ready before starting the registration process. "
						+ "Upon completion, you can proceed to your exam.\r\n\r\nPlease contact us on: info@ems.edu.et if you have any questions or concerns. "
						+ "We wish you all the best on your exam!\r\n\r\nSincerely,\r\nExam Management System Team\r\n");
		infoTextArea.setRows(10);
		infoTextArea.setBounds(641, 113, 702, 378);
		add(infoTextArea);
		infoTextArea.setEditable(false);

		// to display date
		SimpleDateFormat dformat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		dateDispaly.setText(dformat.format(date));

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(80, 524, 116, 47);
		add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setBounds(214, 524, 371, 35);
		add(passwordField);
	}
}
