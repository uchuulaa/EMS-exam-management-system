package student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ems.ConnectionProvider;
import homepage.Index;

public class studentHome extends JPanel {
   public studentHome(){
					setLayout(null);

					        JButton checkExamsBtn = new JButton("Check Available Exams");
					        checkExamsBtn.addActionListener(new ActionListener() {
					            public void actionPerformed(ActionEvent e) {
					                // query the exam table to get all records
					                try {
					                    Connection con = ConnectionProvider.getCon();
					                    PreparedStatement stmt = con.prepareStatement("SELECT eid, ename, edate, eduration FROM exam");
					                    ResultSet rs = stmt.executeQuery();

					                    // create a table model with the relevant columns
					                    DefaultTableModel model = new DefaultTableModel();
					                    model.addColumn("Exam ID");
					                    model.addColumn("Exam Name");
					                    model.addColumn("Exam Date");
					                    model.addColumn("Exam Duration");

					                    // add each record to the table model
					                    while (rs.next()) {
					                        String eid = rs.getString("eid");
					                        String ename = rs.getString("ename");
					                        String edate = rs.getString("edate");
					                        int eduration = rs.getInt("eduration");

					                        model.addRow(new Object[] { eid, ename, edate, eduration });
					                    }

					                    // create a JTable with the table model
					                    JTable table = new JTable(model);

					                    // display the JTable in a JScrollPane
					                    JOptionPane.showMessageDialog(null, new JScrollPane(table), "Available Exams", JOptionPane.INFORMATION_MESSAGE);

					                    con.close();
					                } catch (Exception ex) {
					                    System.out.println(ex);
					                }
					            }
					        });

					        
					        checkExamsBtn.setBounds(497, 168, 219, 51);
					        add(checkExamsBtn);

					        JButton checkResultBtn = new JButton("Check Your Result");
							checkResultBtn.addActionListener(new ActionListener() {
					            public void actionPerformed(ActionEvent e) {
					                String email = StudentLogin.email;

					                // query the student table to get the student's record
					                try {
					                    Connection con = ConnectionProvider.getCon();
					                    PreparedStatement stmt = con.prepareStatement("SELECT * FROM student WHERE email = ?");
					                    stmt.setString(1, email);
					                    ResultSet rs = stmt.executeQuery();
					                    if (rs.next()) {
					                        //String sid = rs.getString("sid");
					                        String name = rs.getString("name");
					                        int marks = rs.getInt("marks");

					                        // Display the student's name and marks
					                        JOptionPane.showMessageDialog(null," Name: " + name + "\nMarks: " + marks, "Your Result", JOptionPane.INFORMATION_MESSAGE);
					                    }
					                    con.close();
					                } catch (Exception ex) {
					                    System.out.println(ex);
					                }
					            }
					        });
					        checkResultBtn.setBounds(497, 250, 219, 54);
					        add(checkResultBtn);

					        JButton takeExamBtn = new JButton("Take Exam");
					        takeExamBtn.addActionListener(new ActionListener() {
					        	public void actionPerformed(ActionEvent e) {
								Index.cardLayout.show(Index.contentPane,"instructionstudent");
					        	}
					        	});
					        takeExamBtn.setBounds(497, 327, 215, 51);
					        add(takeExamBtn);

					        JButton logoutBtn = new JButton("Logout");
					        logoutBtn.addActionListener(new ActionListener() {
					        	public void actionPerformed(ActionEvent e) {
									Index.cardLayout.show(Index.contentPane,"home");
					        	}
					        	});

					        logoutBtn.setBounds(497, 403, 219, 51);
					        add(logoutBtn);
					        setSize(1301, 701);
					    }
    }

