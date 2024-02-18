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
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;

public class AllStudentsResult extends JFrame {
	private JTextField filterTextField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AllStudentsResult frame = new AllStudentsResult();
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
	public AllStudentsResult() {
		getContentPane().setBackground(new Color(232, 208, 208));
		setAlwaysOnTop(true);
		setLocation(new Point(150, 183));

		setBounds(0, 0, 1066, 535);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel allstudentResulticon = new JLabel("");
		allstudentResulticon
				.setIcon(new ImageIcon(AllStudentsResult.class.getResource("../Images/all student result.png")));
		allstudentResulticon.setBounds(0, 0, 68, 68);
		getContentPane().add(allstudentResulticon);

		JLabel allStudentRTitle = new JLabel("All Student Result");
		allStudentRTitle.setFont(new Font("Agency FB", Font.BOLD, 40));
		allStudentRTitle.setBounds(72, 10, 277, 45);
		getContentPane().add(allStudentRTitle);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 65, 1066, 13);
		getContentPane().add(separator);

		JButton exitBtn = new JButton("Back");
		exitBtn.setFont(new Font("Tahoma", Font.BOLD, 16));
		exitBtn.setForeground(new Color(255, 0, 0));
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InstructorHome.open = 0;
				setVisible(false);
			}
		});
		exitBtn.setIcon(null);
		exitBtn.setBounds(981, 0, 85, 58);
		getContentPane().add(exitBtn);

		JLabel filterLabel = new JLabel("Filter Students by mark");
		filterLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		filterLabel.setBounds(10, 78, 233, 34);
		getContentPane().add(filterLabel);

		filterTextField = new JTextField();
		filterTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) { // to filter values from database and show it on table
				int marks;
				if (filterTextField.getText().equals("")) {
					marks = 0;
				} else {
					marks = Integer.parseInt(filterTextField.getText());

					try {
						Connection con = ConnectionProvider.getCon();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery(
								"select sid, name, gender, email, marks from student where marks>=" + marks + "");
						// table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
						table.setModel(DbUtils.resultSetToTableModel(rs)); // imported from rs2xml library and used to
																			// display data.
						table.repaint(); // force the table to repaint

					} catch (Exception e1) {
						JFrame jf = new JFrame();
						jf.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jf, e1);
					}
				}
			}
		});
		filterTextField.setBounds(236, 78, 96, 34);
		getContentPane().add(filterTextField);
		filterTextField.setColumns(10);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Student ID");
		model.addColumn("Name");
		model.addColumn("Gender");
		model.addColumn("E-mail");
		model.addColumn("Marks");

		try {
			Connection con = ConnectionProvider.getCon();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select sid, name, gender, email, marks from student");
			model.setRowCount(0); // clear the existing rows from the model
			while (rs.next()) {
				String sid = rs.getString("sid");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				String email = rs.getString("email");
				int marks = rs.getInt("marks");

				// Add a new row to the model with the retrieved data
				model.addRow(new Object[] { sid, name, gender, email, marks });
			}
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

		table = new JTable(model); // create the table with the model
		table.setBounds(20, 122, 1026, 380);
		getContentPane().add(table);

	}

}
