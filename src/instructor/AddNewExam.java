package instructor;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import ems.ConnectionProvider;

public class AddNewExam extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable table;
	private DefaultTableModel model;
	private List<String> selectedQuestions;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddNewExam frame = new AddNewExam();

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

	public AddNewExam() {
		selectedQuestions = new ArrayList<String>();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Add New Exam");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(10, 10, 150, 30);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Exam ID:");
		lblNewLabel_1.setBounds(10, 50, 70, 20);
		contentPane.add(lblNewLabel_1);

		// textField = new JTextField();
		textField = new JTextField("1");
		textField.setEditable(false);
		textField.setBounds(90, 50, 100, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Exam Name:");
		lblNewLabel_2.setBounds(10, 80, 80, 20);
		contentPane.add(lblNewLabel_2);

		textField_1 = new JTextField();
		textField_1.setBounds(90, 80, 200, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Exam Duration (mins):");
		lblNewLabel_3.setBounds(10, 110, 130, 20);
		contentPane.add(lblNewLabel_3);

		textField_2 = new JTextField();
		textField_2.setBounds(150, 110, 50, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Exam Date:");
		lblNewLabel_4.setBounds(10, 140, 70, 20);
		contentPane.add(lblNewLabel_4);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(90, 140, 150, 20);
		contentPane.add(dateChooser);

		JLabel lblNewLabel_5 = new JLabel("Select Questions:");
		lblNewLabel_5.setBounds(10, 170, 110, 20);
		contentPane.add(lblNewLabel_5);

		model = new DefaultTableModel();
		model.addColumn("Select");
		model.addColumn("Question ID");
		model.addColumn("Question");

		table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(500);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 200, 760, 200);
		contentPane.add(scrollPane);

		// Populate table with questions from database
		try {
			Connection con = ConnectionProvider.getCon();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from question");

			while (rs.next()) {
				String qid = rs.getString("qid");
				String question = rs.getString("qquestion");

				model.addRow(new Object[] { false, qid, question });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

		JLabel lblNewLabel_6 = new JLabel("Group ID:");
		lblNewLabel_6.setBounds(300, 50, 80, 20);
		contentPane.add(lblNewLabel_6);

		JTextField textField_3 = new JTextField();
		textField_3.setBounds(390, 50, 100, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);

		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String examName = textField_1.getText();
				int examDuration = Integer.parseInt(textField_2.getText());
				Date examDate = dateChooser.getDate();
				String groupId = textField_3.getText();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String examDateString = sdf.format(examDate);

				if (groupId.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please enter a group ID.");
					return;
				}

				try {
					Connection con = ConnectionProvider.getCon();

					// Get the next exam ID
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select count(eid) from exam");
					if (rs.next()) {
						int id = rs.getInt(1) + 1;
						String examId = String.valueOf(id);
						textField.setText(examId);
					} else {
						textField.setText("1");
					}

					// Save exam details to database
					PreparedStatement ps = con.prepareStatement("insert into exam values(?,?,?,?,?)");
					ps.setString(1, textField.getText());
					ps.setString(2, examName);
					ps.setInt(3, examDuration);
					ps.setString(4, examDateString);
					ps.setString(5, groupId);
					ps.executeUpdate();

					// Save selected questions to database
					for (String qid : selectedQuestions) {
						PreparedStatement ps2 = con.prepareStatement("insert into exam_questions values(?,?)");
						ps2.setString(1, textField.getText());
						ps2.setString(2, qid);
						ps2.executeUpdate();
					}

					// Display success message
					JOptionPane.showMessageDialog(null, "Exam added successfully!");

					// Reset form
					textField_1.setText("");
					textField_2.setText("");
					dateChooser.setDate(null);
					textField_3.setText("");
					for (int i = 0; i < model.getRowCount(); i++) {
						model.setValueAt(false, i, 0);
					}
					selectedQuestions.clear();

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex);
				}
			}
		});
		btnNewButton.setBounds(10, 410, 85, 25);
		contentPane.add(btnNewButton);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(105, 410, 85, 25);
		contentPane.add(btnCancel);

		// Add listener for checkbox selection
		table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxEditor());
		table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());

		// Add listener for selected questions
		table.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int col = e.getColumn();
					if (col == 0) {
						selectedQuestions.clear();
						for (int i = 0; i < model.getRowCount(); i++) {
							Boolean selected = (Boolean) model.getValueAt(i, col);
							if (selected) {
								String qid = (String) model.getValueAt(i, 1);
								selectedQuestions.add(qid);
							}
						}
					}
				}
			}
		});
	}

	// Custom cell renderer for checkbox column
	class CheckBoxRenderer extends JCheckBox implements javax.swing.table.TableCellRenderer {
		public CheckBoxRenderer() {
			setHorizontalAlignment(JCheckBox.CENTER);
		}

		public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {
			setSelected((value != null && ((Boolean) value).booleanValue()));
			return this;
		}
	}

	// Custom cell editor for checkbox column
	class CheckBoxEditor extends JCheckBox implements javax.swing.table.TableCellEditor {
		public CheckBoxEditor() {
			setHorizontalAlignment(JCheckBox.CENTER);
		}

		public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			setSelected((value != null && ((Boolean) value).booleanValue()));
			return this;
		}

		public Object getCellEditorValue() {
			return Boolean.valueOf(isSelected());
		}

		@Override
		public boolean isCellEditable(EventObject anEvent) {
			
			return true;
		}

		@Override
		public boolean shouldSelectCell(EventObject anEvent) {
			
			return false;
		}

		@Override
		public boolean stopCellEditing() {
			
			return false;
		}

		@Override
		public void cancelCellEditing() {
			

		}

		@Override
		public void addCellEditorListener(CellEditorListener l) {
		

		}

		@Override
		public void removeCellEditorListener(CellEditorListener l) {
			

		}
	}
}