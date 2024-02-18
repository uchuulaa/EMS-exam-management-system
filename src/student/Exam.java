package student;

import java.sql.*;
import ems.ConnectionProvider;
import homepage.Index;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.Timer;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JButton;

public class Exam extends JPanel {
	private JLabel studentIdDisplaylbl;
	private JLabel studentNameDisplaylbl;
	private JLabel questionNumberlbl;
	private JLabel questionNumberDisplaylbl;
	private JLabel datedisplaylbl;
	private JLabel questionLabel;
	private JRadioButton option1radiobtn;
	private JRadioButton option2radiobtn;
	private JRadioButton option3radiobtn;
	private JRadioButton option4radiobtn;
	private JButton nextButtonLabel, prevButton;
	private JButton submitButtonLabel;
	private JLabel secDisplay;
	private JLabel timetakendisplay;

	public String qid = "1";
	public String answer;
	public int min = 0;
	public int sec = 0;
	public int marks = 0;
	Timer timer;
	private ResultSet rs1;

	public void answerCheck() {
		String studentAnswer = "";
		if (option1radiobtn.isSelected()) {
			studentAnswer = option1radiobtn.getText();
		} else if (option2radiobtn.isSelected()) {
			studentAnswer = option2radiobtn.getText();
		} else if (option3radiobtn.isSelected()) {
			studentAnswer = option3radiobtn.getText();
		} else {
			studentAnswer = option4radiobtn.getText();
		}
		if (studentAnswer.equals(answer)) {
			marks = marks + 1;

		}
		int questionId = Integer.parseInt(qid);
		questionId = questionId + 1;
		qid = String.valueOf(questionId);
		option1radiobtn.setSelected(false);
		option2radiobtn.setSelected(false);
		option3radiobtn.setSelected(false);
		option4radiobtn.setSelected(false);
		// Last QUESTION HIDING NEXT BUTTON
		// if (qid.equals("")) {

		// } else {

		// }

	}

	public void prevCheck() {
		String studentAnswer = "";
		if (option1radiobtn.isSelected()) {
			studentAnswer = option1radiobtn.getText();
		} else if (option2radiobtn.isSelected()) {
			studentAnswer = option2radiobtn.getText();
		} else if (option3radiobtn.isSelected()) {
			studentAnswer = option3radiobtn.getText();
		} else {
			studentAnswer = option4radiobtn.getText();
		}
		if (studentAnswer.equals(answer)) {
			marks = marks - 1;
			// String marks1 = String.valueOf(marks);
			// yourMarkDisplaylbl.setText(marks1); // displaying mark value
		}

		// question Number change
		int questionId = Integer.parseInt(qid);
		questionId = questionId - 1;
		qid = String.valueOf(questionId);

		// CLEARING RADIO BUTTON
		option1radiobtn.setSelected(false);
		option2radiobtn.setSelected(false);
		option3radiobtn.setSelected(false);
		option4radiobtn.setSelected(false);

	}

	public void question() {
		try {
			Connection con = ConnectionProvider.getCon();
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs1 = st.executeQuery("select * from question where qid='" + qid + "'");
			if (rs1.next()) { // loading question from database
				questionLabel.setText(rs1.getString(2)); // Update the label text instead of initializing it
				option1radiobtn.setText(rs1.getString(3));
				option2radiobtn.setText(rs1.getString(4));
				option3radiobtn.setText(rs1.getString(5));
				option4radiobtn.setText(rs1.getString(6));
				answer = rs1.getString(7);
				questionNumberDisplaylbl.setText(String.valueOf(qid));
				nextButtonLabel.setVisible(true); // Show the next button
			} else {
				nextButtonLabel.setVisible(false); // Hide the next button
				nextButtonLabel.setVisible(false);
				questionLabel.setVisible(false);
				option1radiobtn.setVisible(false);
				option2radiobtn.setVisible(false);
				option3radiobtn.setVisible(false);
				option4radiobtn.setVisible(false);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void submit() {
		String sid = studentIdDisplaylbl.getText();
		answerCheck();

		try {
			Connection con = ConnectionProvider.getCon();
			Statement st = con.createStatement();
			st.executeUpdate("update student set marks='" + marks + "' where sid='" + sid + "'");
			String mark1 = String.valueOf(marks);

			JOptionPane.showMessageDialog(null, "Mark Obtained :" + mark1);
			Index.cardLayout.show(Index.contentPane, "studenthome");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Something went wrong while submitting answer!");

		}

	}

	public static void main(String args[]) {
		// Create and display the form
		java.awt.EventQueue.invokeLater(() -> {
			new Exam().setVisible(true);
		});
	}

	///////////

	/**
	 * Create the frame.
	 */
	public Exam() {
		initComponents();

	}

	public Exam(String sid) {
		initComponents();
		studentIdDisplaylbl.setText(sid);

		SimpleDateFormat dformat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		datedisplaylbl.setText(dformat.format(date));

		try {
			Connection con = ConnectionProvider.getCon();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select name from student where sid='" + sid + "'");
			while (rs.next()) {
				studentNameDisplaylbl.setText(rs.getString("name"));

			}
			ResultSet rs1 = st.executeQuery("select * from question where qid='" + qid + "'");
			while (rs1.next()) {

				questionNumberDisplaylbl.setText(rs1.getString(1));
				questionLabel.setText(rs1.getString(2));
				option1radiobtn.setText(rs1.getString(3));
				option2radiobtn.setText(rs1.getString(4));
				option3radiobtn.setText(rs1.getString(5));
				option4radiobtn.setText(rs1.getString(6));
				answer = rs1.getString(7);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}

		// TIMER PROGRAM
		// Index.contentPane.setLocationRelativeTo(Index.contentPane);

		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				sec++;
				if (sec == 60) {
					sec = 0;
					min++;
				}
				secDisplay.setText(String.format("%02d", sec));
				timetakendisplay.setText(String.format("%02d", min));
				if (min == 10 && sec == 0) {
					timer.stop();
					answerCheck();
					submit();
				}
			}
		});

		timer.start();

	}

	public void initComponents() {

		setBounds(0, 0, 1256, 700);

		setFont(new Font("Tahoma", Font.BOLD, 20));
		setLayout(null);

		JPanel headerpanel = new JPanel();
		headerpanel.setBackground(new Color(128, 128, 192));
		headerpanel.setBounds(0, 0, 1366, 134);
		add(headerpanel);
		headerpanel.setLayout(null);

		JLabel examPageLabel = new JLabel("Exam Page");
		examPageLabel.setBounds(108, 25, 175, 75);
		headerpanel.add(examPageLabel);
		examPageLabel.setFont(new Font("Agency FB", Font.BOLD, 30));

		JLabel studentIconLabel = new JLabel("");
		studentIconLabel.setBounds(24, 25, 74, 75);
		headerpanel.add(studentIconLabel);
		studentIconLabel.setIcon(new ImageIcon(Exam.class.getResource("../Images/index student.png")));

		JLabel dateLabel = new JLabel("Date :");
		dateLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		dateLabel.setBounds(463, 23, 74, 47);
		headerpanel.add(dateLabel);

		JLabel totalTimeLabel = new JLabel("Total Time");
		totalTimeLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		totalTimeLabel.setBounds(794, 10, 124, 33);
		headerpanel.add(totalTimeLabel);

		JLabel totaltimedisplay = new JLabel("10 min");
		totaltimedisplay.setFont(new Font("Tahoma", Font.BOLD, 20));
		totaltimedisplay.setBounds(928, 10, 84, 38);
		headerpanel.add(totaltimedisplay);

		JLabel timeTakenLabel = new JLabel("Time Taken");
		timeTakenLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		timeTakenLabel.setBounds(794, 35, 132, 33);
		headerpanel.add(timeTakenLabel);

		timetakendisplay = new JLabel("00");
		timetakendisplay.setFont(new Font("Tahoma", Font.BOLD, 20));
		timetakendisplay.setBounds(928, 35, 29, 33);
		headerpanel.add(timetakendisplay);

		JLabel minLabel = new JLabel("min");
		minLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		minLabel.setBounds(967, 35, 46, 33);
		headerpanel.add(minLabel);

		datedisplaylbl = new JLabel("");
		datedisplaylbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		datedisplaylbl.setBounds(547, 23, 119, 47);
		headerpanel.add(datedisplaylbl);

		JPanel bodyPanel = new JPanel();
		bodyPanel.setBackground(new Color(255, 255, 255));
		bodyPanel.setBounds(0, 134, 1356, 505);
		add(bodyPanel);
		bodyPanel.setLayout(null);

		JLabel studIDLabel = new JLabel("Student ID :");
		studIDLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		studIDLabel.setBounds(10, 10, 132, 33);
		bodyPanel.add(studIDLabel);

		JLabel studNameLabel = new JLabel("Student Name :");
		studNameLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		studNameLabel.setBounds(10, 53, 170, 33);
		bodyPanel.add(studNameLabel);

		studentIdDisplaylbl = new JLabel("");
		studentIdDisplaylbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		studentIdDisplaylbl.setBounds(192, 10, 202, 33);
		bodyPanel.add(studentIdDisplaylbl);

		studentNameDisplaylbl = new JLabel("");
		studentNameDisplaylbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		studentNameDisplaylbl.setBounds(192, 53, 202, 33);
		bodyPanel.add(studentNameDisplaylbl);

		JPanel innerPanel = new JPanel();
		innerPanel.setBackground(new Color(255, 255, 255));
		innerPanel.setBounds(10, 91, 1240, 442);
		bodyPanel.add(innerPanel);
		innerPanel.setLayout(null);

		questionLabel = new JLabel("");
		questionLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		questionLabel.setBounds(10, 0, 1316, 47);
		innerPanel.add(questionLabel);

		option1radiobtn = new JRadioButton("");
		option1radiobtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		option1radiobtn.setBounds(26, 72, 1290, 33);
		innerPanel.add(option1radiobtn);

		option2radiobtn = new JRadioButton("");
		option2radiobtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		option2radiobtn.setBounds(26, 130, 1290, 33);
		innerPanel.add(option2radiobtn);

		option3radiobtn = new JRadioButton("");
		option3radiobtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		option3radiobtn.setBounds(26, 194, 1290, 33);
		innerPanel.add(option3radiobtn);

		option4radiobtn = new JRadioButton("");
		option4radiobtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		option4radiobtn.setBounds(26, 255, 1290, 33);
		innerPanel.add(option4radiobtn);

		nextButtonLabel = new JButton("Next");
		nextButtonLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		nextButtonLabel.setBounds(944, 336, 125, 33);
		innerPanel.add(nextButtonLabel);

		nextButtonLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				answerCheck();
				question();
				prevButton.setVisible(true);
			}
		});

		prevButton = new JButton("Previous");
		prevButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		prevButton.setBounds(786, 336, 132, 32);
		innerPanel.add(prevButton);
		prevButton.setVisible(false);

		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prevCheck();
				try {
					rs1.previous();
				} catch (Exception exp) {
					exp.printStackTrace();
				}
				question();
				nextButtonLabel.setVisible(true);
			}
		});

		submitButtonLabel = new JButton("Submit");
		submitButtonLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				answerCheck();
				submit();
			}
		});

		submitButtonLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		submitButtonLabel.setBounds(1104, 336, 118, 33);
		innerPanel.add(submitButtonLabel);

		questionNumberlbl = new JLabel("Question No :");
		questionNumberlbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		questionNumberlbl.setBounds(26, 325, 150, 33);
		innerPanel.add(questionNumberlbl);

		questionNumberDisplaylbl = new JLabel("1");
		questionNumberDisplaylbl.setFont(new Font("Tahoma", Font.BOLD, 20));
		questionNumberDisplaylbl.setBounds(179, 325, 46, 33);
		innerPanel.add(questionNumberDisplaylbl);

		secDisplay = new JLabel("00");
		secDisplay.setFont(new Font("Tahoma", Font.BOLD, 20));
		secDisplay.setBounds(1192, 30, 29, 33);
		headerpanel.add(secDisplay);

		JLabel counterLabel = new JLabel("counter");
		counterLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		counterLabel.setBounds(1098, 30, 84, 33);
		headerpanel.add(counterLabel);

		JLabel EMSLabel = new JLabel("EMS - Exam Management System");
		EMSLabel.setBackground(new Color(64, 0, 0));
		EMSLabel.setForeground(new Color(255, 255, 0));
		EMSLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		EMSLabel.setBounds(546, 74, 547, 50);
		headerpanel.add(EMSLabel);

		option1radiobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				option2radiobtn.setSelected(false);
				option3radiobtn.setSelected(false);
				option4radiobtn.setSelected(false);
			}
		});

		option2radiobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				option1radiobtn.setSelected(false);
				option3radiobtn.setSelected(false);
				option4radiobtn.setSelected(false);
			}
		});

		option3radiobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				option1radiobtn.setSelected(false);
				option2radiobtn.setSelected(false);
				option4radiobtn.setSelected(false);
			}
		});

		option4radiobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				option1radiobtn.setSelected(false);
				option2radiobtn.setSelected(false);
				option3radiobtn.setSelected(false);
			}
		});

	}
}
