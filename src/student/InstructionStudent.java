package student;

import java.awt.EventQueue;


import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import ems.ConnectionProvider;
import homepage.Index;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class InstructionStudent extends JPanel {

	public String sid;// to store data from student detail page and pass it to instruction page

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InstructionStudent frame = new InstructionStudent();
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
	public InstructionStudent() {
		initComponents();
	}

	public InstructionStudent(String sid1) { // to get data from previous instruction
		initComponents();
		sid = sid1; //
	}

	public void initComponents() {

		setBounds(0, 0, 1302, 634);
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1366, 768);
		add(panel);
		panel.setLayout(null);

		JLabel studentIconLabel = new JLabel("");
		studentIconLabel.setIcon(new ImageIcon(InstructionStudent.class.getResource("../Images/index student.png")));
		studentIconLabel.setBounds(10, 10, 64, 53);
		panel.add(studentIconLabel);

		JLabel instructionLabel = new JLabel("Instruction");
		instructionLabel.setFont(new Font("Agency FB", Font.BOLD, 40));
		instructionLabel.setBounds(84, 10, 183, 47);
		panel.add(instructionLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 61, 1346, 2);
		panel.add(separator);

		JTextArea instructionTextArea = new JTextArea();
		instructionTextArea.setEditable(false);
		instructionTextArea.setLineWrap(true);
		instructionTextArea.setWrapStyleWord(true);
		instructionTextArea.setToolTipText("");
		instructionTextArea.setFont(new Font("Monospaced", Font.PLAIN, 17));
		instructionTextArea.setText(
				"Welcome to the exam Instruction page!\r\n Before you begin your exam, please take a moment to read these instructions carefully.\r\n1. Get ready: Ensure that you have a reliable internet connection and a device that meets the   system requirements. Make sure you find a quiet and comfortable place to take the exam.\r\n\r\n2. Review the instructions: Read the exam instructions and guidelines carefully before starting the exam.   Familiarize yourself with the exam format.\r\n\r\n3. Manage your time: Check the exam duration and make sure you manage your time effectively during the exam. If   the exam has multiple sections, make sure you navigate to each section and complete all required questions.\r\n\r\n4. Check your work: Double-check your answers before submitting the exam. Make sure you have   answered all  required questions and provided complete and accurate answers.\r\n\r\n5. Technical difficulties: If you encounter any technical difficulties during the exam, notify the  system   administrator or technical support immediately.\r\n\r\n6. Submit your exam: After submitting the exam, make sure you receive a confirmation message that your exam has  been successfully submitted.\r\n\t                              Good luck with your exam!\r\n\t\t\t\t The Exam Management System Team\r\n\r\n");
		instructionTextArea.setBounds(47, 74, 1193, 404);
		panel.add(instructionTextArea);

		// EVENT HANDLER FOR EXAM START BUTTON
		JButton startButtonLabel = new JButton("START");
		startButtonLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// setVisible(false);
				// new Exam(sid).setVisible(true); // linking exam page with student details
				// from instruction page with
				// constructor to exam page
				// setVisible(false);

				String sid = null;
				try {
					Connection connection = ConnectionProvider.getCon();
					Statement statement = connection.createStatement();
					ResultSet rsData = statement
							.executeQuery("select sid from student where email='" + StudentLogin.email + "'");
					if (rsData.next()) {
						sid = rsData.getString("sid");
					}

				} catch (Exception exp) {
					exp.printStackTrace();
				}
				// panel.setVisible(false);
				Index.contentPane.add(new Exam(sid), "exam");
				Index.cardLayout.show(Index.contentPane, "exam");

			}
		});
		startButtonLabel.setFont(new Font("Algerian", Font.BOLD, 36));
		startButtonLabel.setBounds(487, 511, 265, 53);
		panel.add(startButtonLabel);
	}
}
