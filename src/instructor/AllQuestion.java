package instructor;

import java.sql.*;
import ems.ConnectionProvider;
import net.proteanit.sql.DbUtils;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class AllQuestion extends JFrame {
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AllQuestion frame = new AllQuestion();
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
	public AllQuestion() {
		setLocation(new Point(150, 183));

		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("All Questions");
		setBounds(0, 0, 1066, 535);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel iconLabel = new JLabel("");
		iconLabel.setIcon(new ImageIcon(AllQuestion.class.getResource("../Images/all questions.png")));
		iconLabel.setBounds(10, 10, 69, 40);
		getContentPane().add(iconLabel);

		JLabel allQuestionLabel = new JLabel("All Question");
		allQuestionLabel.setFont(new Font("Agency FB", Font.BOLD, 40));
		allQuestionLabel.setBounds(89, 10, 196, 40);
		getContentPane().add(allQuestionLabel);

		// EVENT HANDLER FOR CLOSE BUTTON IN ALL QUESTION

		JButton exitbtn = new JButton("Back");
		exitbtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		exitbtn.setForeground(new Color(255, 0, 0));
		exitbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InstructorHome.open = 0;
				setVisible(false);
			}
		});
		exitbtn.setIcon(null);
		exitbtn.setBounds(971, 0, 85, 60);
		getContentPane().add(exitbtn);

		JSeparator separator = new JSeparator();
		separator.setBounds(20, 58, 1023, 2);
		getContentPane().add(separator);

		table = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(
				new String[] { "Question ID", "Question", "Option 1", "Option 2", "Option 3", "Option 4", "Answer" });
		table.setModel(model);

		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setBounds(20, 88, 998, 352);
		getContentPane().add(table);

		try {
			Connection con = ConnectionProvider.getCon();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from question");
			table.setModel(DbUtils.resultSetToTableModel(rs));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Something went wrong while displaying all questions from database");
		}
	}
}
