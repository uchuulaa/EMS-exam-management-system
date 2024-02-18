package instructor;

import java.sql.*;
import ems.ConnectionProvider;
import java.awt.EventQueue;

import javax.swing.JFrame;
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

public class DeleteQuestion extends JFrame {
	private JTextField questionIdTextField;
	private JTextField questionTextField;
	private JTextField option1TextField;
	private JTextField option2TextField;
	private JTextField option3TextField;
	private JTextField option4TextField;
	private JTextField answerTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteQuestion frame = new DeleteQuestion();
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
	public DeleteQuestion() {
		getContentPane().setFont(new Font("Tahoma", Font.BOLD, 14));

		setLocation(new Point(150, 183));
		setAlwaysOnTop(true);
		setBounds(0, 0, 1066, 535);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel iconLabel = new JLabel("");
		iconLabel.setIcon(new ImageIcon(DeleteQuestion.class.getResource("../Images/delete Question.png")));
		iconLabel.setBounds(0, 0, 60, 66);
		getContentPane().add(iconLabel);

		JLabel deleteQuestionTitlelbl = new JLabel("Delete Question");
		deleteQuestionTitlelbl.setFont(new Font("Agency FB", Font.BOLD, 40));
		deleteQuestionTitlelbl.setBounds(61, 10, 254, 51);
		getContentPane().add(deleteQuestionTitlelbl);

		// Action PERFORMED WHEN MOUSE IS CLICKED --CLOSE BUTTON
		JButton exitbtn = new JButton("Back");
		exitbtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		exitbtn.setForeground(new Color(255, 0, 0));
		exitbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InstructorHome.open = 0; // changing opening flag back to default in Instructor home
				setVisible(false);
			}
		});
		exitbtn.setIcon(null);
		exitbtn.setBounds(981, 0, 85, 61);
		getContentPane().add(exitbtn);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 71, 1046, 2);
		getContentPane().add(separator);

		JLabel questionIdlbl = new JLabel("Question ID :");
		questionIdlbl.setFont(new Font("Tahoma", Font.BOLD, 14));
		questionIdlbl.setBounds(32, 85, 104, 13);
		getContentPane().add(questionIdlbl);

		JLabel questionLabel = new JLabel("Question :");
		questionLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		questionLabel.setBounds(32, 133, 104, 13);
		getContentPane().add(questionLabel);

		JLabel option1label = new JLabel("Option 1 :");
		option1label.setFont(new Font("Tahoma", Font.BOLD, 14));
		option1label.setBounds(32, 181, 104, 25);
		getContentPane().add(option1label);

		JLabel option2label = new JLabel("Option 2 :");
		option2label.setFont(new Font("Tahoma", Font.BOLD, 14));
		option2label.setBounds(32, 239, 104, 23);
		getContentPane().add(option2label);

		JLabel option3label = new JLabel("Option 3 :");
		option3label.setFont(new Font("Tahoma", Font.BOLD, 14));
		option3label.setBounds(32, 287, 104, 25);
		getContentPane().add(option3label);

		JLabel option4label = new JLabel("Option 4 :");
		option4label.setFont(new Font("Tahoma", Font.BOLD, 14));
		option4label.setBounds(32, 337, 104, 29);
		getContentPane().add(option4label);

		JLabel answerLabel = new JLabel("Answer :");
		answerLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		answerLabel.setBounds(32, 381, 104, 29);
		getContentPane().add(answerLabel);

		questionIdTextField = new JTextField();
		questionIdTextField.setBounds(131, 84, 96, 33);
		getContentPane().add(questionIdTextField);
		questionIdTextField.setColumns(10);

		questionTextField = new JTextField();
		questionTextField.setColumns(10);
		questionTextField.setBounds(131, 127, 690, 30);
		getContentPane().add(questionTextField);

		option1TextField = new JTextField();
		option1TextField.setColumns(10);
		option1TextField.setBounds(131, 181, 690, 30);
		getContentPane().add(option1TextField);

		option2TextField = new JTextField();
		option2TextField.setColumns(10);
		option2TextField.setBounds(131, 239, 690, 28);
		getContentPane().add(option2TextField);

		option3TextField = new JTextField();
		option3TextField.setColumns(10);
		option3TextField.setBounds(131, 287, 690, 30);
		getContentPane().add(option3TextField);

		option4TextField = new JTextField();
		option4TextField.setColumns(10);
		option4TextField.setBounds(131, 336, 690, 30);
		getContentPane().add(option4TextField);

		answerTextField = new JTextField();
		answerTextField.setColumns(10);
		answerTextField.setBounds(131, 380, 690, 30);
		getContentPane().add(answerTextField);

		// Event Handler for search button in delete Question

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String qid = questionIdTextField.getText();
				try {
					Connection con = ConnectionProvider.getCon();
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select * from question where qid='" + qid + "'");// getting question
																										// with given id
																										// from question
																										// table

					if (rs.next()) {
						questionTextField.setText(rs.getString(2));
						option1TextField.setText(rs.getString(3));
						option2TextField.setText(rs.getString(4));
						option3TextField.setText(rs.getString(5));
						option4TextField.setText(rs.getString(6));
						answerTextField.setText(rs.getString(7));
						questionIdTextField.setEditable(false); // disabling editing question Id
					} else {
						JFrame jf = new JFrame();
						jf.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jf, "Question does not exist!");
					}
				} catch (Exception e1) {
					JFrame jf = new JFrame();
					jf.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jf, "Something went wrong trying to search question ID!");
				}
			}
		});
		searchButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		searchButton.setIcon(new ImageIcon(DeleteQuestion.class.getResource("../Images/search.png")));
		searchButton.setBounds(238, 83, 113, 33);
		getContentPane().add(searchButton);

		// EVENT HANDLER FOR DELETE BUTTON

		JButton deletebtn = new JButton("Delete");
		deletebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String qid = questionIdTextField.getText();
				try {
					Connection con = ConnectionProvider.getCon();
					PreparedStatement ps = con.prepareStatement("delete from question where qid=?");
					ps.setString(1, qid);
					ps.executeUpdate();

					// showing pop up message
					JFrame jf = new JFrame();
					jf.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jf, "Deleted Succesfully!");
					setVisible(false);
					new DeleteQuestion().setVisible(true);

				} catch (Exception e2) {
					JFrame jf = new JFrame();
					jf.setAlwaysOnTop(true);
					JOptionPane.showMessageDialog(jf, "Something went wrong trying to delete question ID!");
				}
			}
		});
		deletebtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		deletebtn.setIcon(new ImageIcon(DeleteQuestion.class.getResource("../Images/delete.png")));
		deletebtn.setBounds(200, 451, 115, 45);
		getContentPane().add(deletebtn);

		// Event Listener for Clear button

		JButton clearbtn = new JButton("Clear");
		clearbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				questionIdTextField.setText("");
				questionTextField.setText("");
				option1TextField.setText("");
				option2TextField.setText("");
				option3TextField.setText("");
				option4TextField.setText("");
				answerTextField.setText("");
				questionIdTextField.setEditable(true);
			}
		});
		clearbtn.setFont(new Font("Tahoma", Font.BOLD, 14));
		clearbtn.setIcon(new ImageIcon(DeleteQuestion.class.getResource("../Images/clear.png")));
		clearbtn.setBounds(486, 451, 104, 45);
		getContentPane().add(clearbtn);

	}
}
