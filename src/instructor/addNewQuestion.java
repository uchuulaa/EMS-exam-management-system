package instructor;

import ems.ConnectionProvider;
import java.sql.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class addNewQuestion extends JFrame {

	private JPanel contentPane;
	private JTextField questionIdField;
	private JTextField questionField;
	private JTextField option1Field;
	private JTextField option2Field;
	private JTextField option3Filed;
	private JTextField option4Field;
	private JTextField answerField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addNewQuestion frame = new addNewQuestion();
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
	public addNewQuestion() {
		setAlwaysOnTop(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0, 1060, 536);
		contentPane = new JPanel();
		contentPane.setLocation(new Point(150, 183));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel iconlbl = new JLabel("");
		iconlbl.setIcon(new ImageIcon(addNewQuestion.class.getResource("../Images/add new question.png")));
		iconlbl.setBounds(10, 10, 42, 32);
		contentPane.add(iconlbl);

		JLabel titlelbl = new JLabel("Add New Question");
		titlelbl.setFont(new Font("Times New Roman", Font.BOLD, 25));
		titlelbl.setBounds(62, 10, 224, 42);
		contentPane.add(titlelbl);

		JButton exitbtn = new JButton("Back");
		exitbtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		exitbtn.setForeground(new Color(255, 0, 0));
		exitbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InstructorHome.open = 0;// making the flag to open new page 0 again
				setVisible(false);
			}
		});
		exitbtn.setIcon(null);
		exitbtn.setBounds(961, 0, 85, 53);
		contentPane.add(exitbtn);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 50, 1046, 14);
		contentPane.add(separator);

		JLabel questionIdlbl = new JLabel("Question ID :");
		questionIdlbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		questionIdlbl.setBounds(10, 62, 97, 13);
		contentPane.add(questionIdlbl);

		questionIdField = new JTextField();
		questionIdField.setBounds(117, 62, 558, 19);
		contentPane.add(questionIdField);
		questionIdField.setColumns(10);
		questionIdField.setEditable(false);

		JLabel questionlbl = new JLabel("Question :");
		questionlbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		questionlbl.setBounds(10, 90, 85, 13);
		contentPane.add(questionlbl);

		questionField = new JTextField();
		questionField.setBounds(117, 89, 558, 19);
		contentPane.add(questionField);
		questionField.setColumns(10);

		JLabel option1lbl = new JLabel("Option 1 :");
		option1lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		option1lbl.setBounds(10, 128, 88, 13);
		contentPane.add(option1lbl);

		JLabel option2lbl = new JLabel("Option 2 :");
		option2lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		option2lbl.setBounds(10, 162, 85, 13);
		contentPane.add(option2lbl);

		JLabel option3lbl = new JLabel("Option 3 :");
		option3lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		option3lbl.setBounds(10, 201, 85, 13);
		contentPane.add(option3lbl);

		JLabel option4lbl = new JLabel("Option 4 :");
		option4lbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		option4lbl.setBounds(10, 242, 85, 13);
		contentPane.add(option4lbl);

		JLabel answerlbl = new JLabel("Answer :");
		answerlbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		answerlbl.setBounds(10, 272, 85, 13);
		contentPane.add(answerlbl);

		option1Field = new JTextField();
		option1Field.setColumns(10);
		option1Field.setBounds(117, 127, 558, 19);
		contentPane.add(option1Field);

		option2Field = new JTextField();
		option2Field.setColumns(10);
		option2Field.setBounds(117, 161, 558, 19);
		contentPane.add(option2Field);

		option3Filed = new JTextField();
		option3Filed.setColumns(10);
		option3Filed.setBounds(117, 200, 558, 19);
		contentPane.add(option3Filed);

		option4Field = new JTextField();
		option4Field.setColumns(10);
		option4Field.setBounds(117, 241, 558, 19);
		contentPane.add(option4Field);

		answerField = new JTextField();
		answerField.setColumns(10);
		answerField.setBounds(117, 271, 558, 19);
		contentPane.add(answerField);

		// Making connection with database for adding new question and making question
		// id AI
		try {
			Connection con = ConnectionProvider.getCon();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select count(qid) from question");
			if (rs.next()) {
				int id = rs.getInt(1);
				id = id + 1;
				String str = String.valueOf(id);
				questionIdField.setText(str);
			} else {
				questionIdField.setText("1");
			}
		} catch (Exception e) {
			JFrame jf = new JFrame();
			jf.setAlwaysOnTop(true);
			JOptionPane.showMessageDialog(jf, e);
		}

		JButton savebtn = new JButton("Save");
		savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String qid = questionIdField.getText();
				String qquestion = questionField.getText();
				String qop1 = option1Field.getText();
				String qop2 = option2Field.getText();
				String qop3 = option3Filed.getText();
				String qop4 = option4Field.getText();
				String qcorrect_answer = answerField.getText();
				try {
					Connection conn = ConnectionProvider.getCon();
					PreparedStatement ps = conn.prepareStatement("insert into question values(?,?,?,?,?,?,?)");
					ps.setString(1, qid);
					ps.setString(2, qquestion);
					ps.setString(3, qop1);
					ps.setString(4, qop2);
					ps.setString(5, qop3);
					ps.setString(6, qop4);
					ps.setString(7, qcorrect_answer);

					ps.executeUpdate();// to save

					// to display on top as pop up
					JFrame jf = new JFrame();
					jf.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jf, "Successfully saved");
					setVisible(false);// removing data from form after submitting
					new addNewQuestion().setVisible(true);// showing new form with updated id automatically

				} catch (Exception e1) {
					JFrame jf = new JFrame();
					jf.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jf, "Something went wrong while trying to save to database");
				}
			}
		});
		savebtn.setIcon(new ImageIcon(addNewQuestion.class.getResource("../Images/save.png")));
		savebtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		savebtn.setBounds(146, 327, 110, 32);
		contentPane.add(savebtn);

		JButton clearbtn = new JButton("Clear");
		clearbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				questionField.setText("");
				option1Field.setText("");
				option2Field.setText("");
				option3Filed.setText("");
				option4Field.setText("");
				answerField.setText("");

			}
		});
		clearbtn.setIcon(new ImageIcon(addNewQuestion.class.getResource("../Images/clear.png")));
		clearbtn.setFont(new Font("Tahoma", Font.BOLD, 18));
		clearbtn.setBounds(419, 327, 124, 32);
		contentPane.add(clearbtn);
	}
}
