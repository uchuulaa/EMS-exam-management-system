package instructor;

import java.sql.*;
import ems.ConnectionProvider;
import java.awt.EventQueue;

import javax.swing.JFrame;
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

public class updateQuestion extends JFrame {
	private JTextField questionIdField;
	private JTextField questionField;
	private JTextField option1Field;
	private JTextField option2Field;
	private JTextField option3Field;
	private JTextField option4Field;
	private JTextField answerField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					updateQuestion frame = new updateQuestion();
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
	public updateQuestion() {
		setAlwaysOnTop(true);
		setBounds(0,0, 1066, 535);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel iconlbl = new JLabel("");
		iconlbl.setIcon(new ImageIcon(updateQuestion.class.getResource("../Images/Update Question.png")));
		iconlbl.setBounds(10, 10, 60, 51);
		getContentPane().add(iconlbl);

		JLabel updatetitle = new JLabel("Update Question");
		updatetitle.setFont(new Font("Agency FB", Font.BOLD, 40));
		updatetitle.setBounds(80, 10, 267, 51);
		getContentPane().add(updatetitle);

		// for close button in update question page
		JButton closeButton = new JButton("Back");
		closeButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		closeButton.setForeground(new Color(255, 0, 0));
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InstructorHome.open = 0;
				setVisible(false);

			}
		});
		closeButton.setIcon(null);
		closeButton.setBounds(957, 0, 85, 61);
		getContentPane().add(closeButton);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 71, 1032, 2);
		getContentPane().add(separator);

		JLabel questionIdLabel = new JLabel("Question ID :");
		questionIdLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		questionIdLabel.setBounds(20, 83, 103, 13);
		getContentPane().add(questionIdLabel);

		JLabel questionLabel = new JLabel("Question  :");
		questionLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		questionLabel.setBounds(20, 121, 103, 13);
		getContentPane().add(questionLabel);

		JLabel option_1label = new JLabel("Option 1 :");
		option_1label.setFont(new Font("Tahoma", Font.BOLD, 14));
		option_1label.setBounds(20, 160, 103, 13);
		getContentPane().add(option_1label);

		JLabel option_2label = new JLabel("Option 2 :");
		option_2label.setFont(new Font("Tahoma", Font.BOLD, 14));
		option_2label.setBounds(20, 195, 103, 13);
		getContentPane().add(option_2label);

		JLabel option_3label = new JLabel("Option 3 :");
		option_3label.setFont(new Font("Tahoma", Font.BOLD, 14));
		option_3label.setBounds(20, 236, 103, 13);
		getContentPane().add(option_3label);

		JLabel option_4label = new JLabel("Option 4 :");
		option_4label.setFont(new Font("Tahoma", Font.BOLD, 14));
		option_4label.setBounds(20, 276, 103, 13);
		getContentPane().add(option_4label);

		JLabel answerLabel = new JLabel("Answer :");
		answerLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		answerLabel.setBounds(20, 316, 103, 13);
		getContentPane().add(answerLabel);

		questionIdField = new JTextField();
		questionIdField.setBounds(133, 77, 85, 30);
		getContentPane().add(questionIdField);
		questionIdField.setColumns(10);

		questionField = new JTextField();
		questionField.setColumns(10);
		questionField.setBounds(133, 120, 672, 19);
		getContentPane().add(questionField);

		option1Field = new JTextField();
		option1Field.setColumns(10);
		option1Field.setBounds(133, 159, 672, 19);
		getContentPane().add(option1Field);

		option2Field = new JTextField();
		option2Field.setColumns(10);
		option2Field.setBounds(133, 194, 672, 19);
		getContentPane().add(option2Field);

		option3Field = new JTextField();
		option3Field.setColumns(10);
		option3Field.setBounds(133, 235, 672, 19);
		getContentPane().add(option3Field);

		option4Field = new JTextField();
		option4Field.setColumns(10);
		option4Field.setBounds(133, 275, 672, 19);
		getContentPane().add(option4Field);

		answerField = new JTextField();
		answerField.setColumns(10);
		answerField.setBounds(133, 315, 672, 19);
		getContentPane().add(answerField);

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String qid = questionIdField.getText();
				try {
					Connection con = ConnectionProvider.getCon(); // from ems package

					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select *from question where qid='" + qid + "'"); // taking values
																										// equal to
																										// input value
																										// on search
																										// field from
																										// question
																										// table
					if (rs.next()) {// iterating and displaying question information
						questionField.setText(rs.getString(2));
						option1Field.setText(rs.getString(3));
						option2Field.setText(rs.getString(4));
						option3Field.setText(rs.getString(5));
						option4Field.setText(rs.getString(6));
						answerField.setText(rs.getString(7));
						questionIdField.setEditable(false);// DISABLING EDITING OF ID FIELD AFTER LOADING
					} else { // if question not exist
						JFrame jf = new JFrame();
						jf.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jf, "Question with specified ID does not exist!");
					}

				} catch (Exception e1) { // handling exception
					JFrame jf = new JFrame();
					jf.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jf, "Something went wrong while searching the question!");
				}
			}
		});
		searchButton.setIcon(new ImageIcon(updateQuestion.class.getResource("../Images/search.png")));
		searchButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		searchButton.setBounds(228, 77, 131, 30);
		getContentPane().add(searchButton);

		// EVENT HANDLER FOR UPDATE BUTTON
		JButton updatebtn = new JButton("Update");
		updatebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String qid = questionIdField.getText();
				String qquestion = questionField.getText();
				String qop1 = option1Field.getText();
				String qop2 = option2Field.getText();
				String qop3 = option3Field.getText();
				String qop4 = option4Field.getText();
				String qcorrect_answer = answerField.getText();

				try {
					Connection con = ConnectionProvider.getCon();
					PreparedStatement ps = con.prepareStatement(
							"update question set qquestion=?,qop1=?, qop2=?,qop3=?,qop4=?,qcorrect_answer=? where qid=?");
					ps.setString(1, qquestion);
					ps.setString(2, qop1);
					ps.setString(3, qop2);
					ps.setString(4, qop3);
					ps.setString(5, qop4);
					ps.setString(6, qcorrect_answer);
					ps.setString(7, qid);
					ps.executeUpdate();

					JFrame jf = new JFrame();
					jf.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jf, "Succesfully Updated the question!");
					setVisible(false);
					new updateQuestion().setVisible(true);
				} catch (Exception e1) {
					JFrame jf = new JFrame();
					jf.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jf, "Something went wrong while submiting update!");
				}
			}
		});
		updatebtn.setIcon(new ImageIcon(updateQuestion.class.getResource("../Images/save.png")));
		updatebtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		updatebtn.setBounds(165, 359, 115, 35);
		getContentPane().add(updatebtn);

		// EVENT HANDLER FOR CLEAR
		JButton clearbtn = new JButton("Clear");
		clearbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				questionIdField.setText("");
				questionField.setText("");
				option1Field.setText("");
				option2Field.setText("");
				option3Field.setText("");
				option4Field.setText("");
				answerField.setText("");
				questionIdField.setEditable(true);
			}
		});
		clearbtn.setIcon(new ImageIcon(updateQuestion.class.getResource("../Images/clear.png")));
		clearbtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		clearbtn.setBounds(450, 359, 108, 35);
		getContentPane().add(clearbtn);

	}
}
