package admin;

import homepage.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.util.Scanner;
//import java.util.TreeSet;

public class AdminPanel extends JPanel implements ActionListener, MenuListener {
        private JTextField groupnumField;
        private JTable studTable,instTable;
        private DefaultTableModel studModel, instModel;
        private JButton creategpButton, addStudButton, addInstButton;
        private String groupName;
        private JLabel studIDLabel, instIDLabel, succLabel1, succLabel2;
        private JTextArea instIDArea, studIDArea;
        public static CardLayout cardLayout;

        public AdminPanel() {
                String[][] studData = new String[25][2];
                String[] studCols = { "ID", "Name" };
                studModel = new DefaultTableModel(studData, studCols) {
                        @Override
                        public boolean isCellEditable(int row, int col) {
                                return false;
                        }
                };
                String[][] instData = new String[25][2];
                String[] instCols = { "ID", "Name" };
                instModel = new DefaultTableModel(instData, instCols) {
                        @Override
                        public boolean isCellEditable(int row, int col) {
                                return false;
                        }
                };
                updateTables();

                JLabel groupnumBtn = new JLabel("GROUP NAME ");
                groupnumBtn.setBounds(74, 22, 106, 32);
                groupnumBtn.setBackground(new Color(0, 255, 64));

                groupnumField = new JTextField();
                groupnumField.setBounds(199, 25, 187, 26);
                groupnumField.setColumns(10);

                studIDLabel = new JLabel("STUDENT IDS");
                studIDLabel.setBounds(74, 72, 95, 32);

                studIDArea = new JTextArea();
                studIDArea.setBounds(191, 82, 195, 198);

                instIDLabel = new JLabel("INSTRUCTOR IDS");
                instIDLabel.setBounds(61, 344, 95, 32);

                instIDArea = new JTextArea();
                instIDArea.setBounds(191, 354, 195, 198);

                creategpButton = new JButton("CREATE GROUP ");
                creategpButton.setBounds(437, 27, 164, 27);

                addStudButton = new JButton("ADD STUDENT ");
                addStudButton.setBounds(228, 298, 123, 23);

                addInstButton = new JButton("ADD INSTRUCTOR ");
                addInstButton.setBounds(204, 570, 147, 23);

                succLabel1 = new JLabel("STUDENT ADDED");
                succLabel1.setBounds(377, 299, 104, 21);
                succLabel1.setForeground(new Color(0, 128, 64));
                succLabel1.setHorizontalAlignment(SwingConstants.CENTER);
                succLabel1.setBackground(new Color(255, 255, 255));

                succLabel2 = new JLabel("INSTRUCTOR ADDED");
                succLabel2.setBounds(389, 570, 141, 23);
                succLabel2.setForeground(new Color(0, 128, 64));
                succLabel2.setHorizontalAlignment(SwingConstants.CENTER);
                studIDLabel.setVisible(false);
                studIDArea.setVisible(false);
                instIDLabel.setVisible(false);
                instIDArea.setVisible(false);
                addStudButton.setVisible(false);
                addInstButton.setVisible(false);
                succLabel1.setVisible(false);
                succLabel2.setVisible(false);

                groupnumField.addActionListener(this);
                creategpButton.addActionListener(this);
                addStudButton.addActionListener(this);
                addInstButton.addActionListener(this);

                Index.createGroup.addMenuListener(this);
                Index.groupDetails.addMenuListener(this);
                Index.memberDetails.addMenuListener(this);

                setLayout(null);
                setLayout(null);
                add(groupnumBtn);
                add(instIDLabel);
                add(studIDLabel);
                add(groupnumField);
                add(creategpButton);
                add(studIDArea);
                add(instIDArea);
                add(addInstButton);
                add(succLabel2);
                add(addStudButton);
                add(succLabel1);

                JLabel addedStudLabel = new JLabel(" UNASSIGNED STUDENTS");
                addedStudLabel.setBounds(655, 23, 154, 30);
                add(addedStudLabel);
                addedStudLabel.setHorizontalAlignment(SwingConstants.CENTER);

                JScrollPane studScrollpane = new JScrollPane();
                studScrollpane.setBounds(652, 58, 248, 239);
                add(studScrollpane);
                studTable = new JTable();
                studTable.setModel(studModel);

                studScrollpane.setViewportView(studTable);

                JScrollPane instScrollpane = new JScrollPane();
                instScrollpane.setBounds(654, 344, 257, 226);
                add(instScrollpane);
                instTable = new JTable();
                instTable.setModel(instModel);

                instScrollpane.setViewportView(instTable);

                JLabel addedInstLable = new JLabel("UNASSIGNED INSTRUCTORS");
                addedInstLable.setBounds(655, 308, 175, 31);
                add(addedInstLable);
                addedInstLable.setHorizontalAlignment(SwingConstants.CENTER);

        }

        public void menuSelected(MenuEvent e) {

                if (e.getSource() == Index.groupDetails) {
                        Index.cardLayout.show(Index.contentPane, "gpdetail");
                } else if (e.getSource() == Index.memberDetails) {
                        Index.cardLayout.show(Index.contentPane, "memdetail");
                }
                updateTables();

        }

        public void menuDeselected(MenuEvent e) {

        }

        public void menuCanceled(MenuEvent e) {

        }

        public void updateTables() {
                try {
                        Connection connection = DriverManager.getConnection(
                                        "jdbc:mysql://localhost:3306/examsystem",
                                        "root", "");
                        PreparedStatement stmt = connection.prepareStatement(
                                        "SELECT sid,name FROM student WHERE sid NOT IN(SELECT sid FROM student_group) ");
                        ResultSet rsData = stmt.executeQuery();
                        int index = 0;
                        while (rsData.next()) {
                                studModel.setValueAt(rsData.getString("sid"), index, 0);
                                studModel.setValueAt(rsData.getString("name"), index, 1);

                                index++;
                        }
                        for (int j = index; j < studModel.getRowCount(); j++) {
                                studModel.setValueAt(null, j, 0);
                                studModel.setValueAt(null, j, 1);
                        }
                        rsData.close();
                        PreparedStatement stmt2 = connection.prepareStatement(
                                        "SELECT iid,name FROM instructor WHERE iid NOT IN(SELECT iid FROM instructor_group) ");
                        ResultSet rsData2 = stmt2.executeQuery();

                        int index2 = 0;
                        while (rsData2.next()) {
                                instModel.setValueAt(rsData2.getString("iid"), index2, 0);
                                instModel.setValueAt(rsData2.getString("name"), index2, 1);
                                index2++;
                        }
                        for (int j = index2; j < instModel.getRowCount(); j++) {
                                instModel.setValueAt(null, j, 0);
                                instModel.setValueAt(null, j, 1);
                        }
                        rsData2.close();

                } catch (Exception e) {
                        e.printStackTrace();
                }

        }

        public void actionPerformed(ActionEvent e) {
                int num1 = 0, num2 = 0, num3 = 0, studExist = 0, instExist = 0;
                String studID, instID;
                Connection connection = null;
                PreparedStatement statement1, statement2, statement3, statement4;
                ResultSet rs1, rs2, rs3;
                groupName = groupnumField.getText();
                succLabel1.setVisible(false);
                succLabel2.setVisible(false);
                if (e.getSource() == creategpButton) {
                        if (groupName.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Enter the group name.");
                        } else {
                                try {
                                        connection = DriverManager.getConnection(
                                                        "jdbc:mysql://localhost:3306/examsystem",
                                                        "root", "");
                                        statement1 = connection.prepareStatement(
                                                        "SELECT COUNT(group_id) FROM student_group WHERE group_id =(?); ");
                                        statement1.setString(1, groupName);
                                        rs1 = statement1.executeQuery();
                                        rs1.next();
                                        num1 = rs1.getInt(1);
                                        statement2 = connection.prepareStatement(
                                                        "SELECT COUNT(group_id) FROM instructor_group WHERE group_id = (?); ");
                                        statement2.setString(1, groupName);
                                        rs2 = statement2.executeQuery();
                                        rs2.next();
                                        num2 = rs2.getInt(1);

                                        if (num1 == 0 && num2 == 0) {
                                                studIDLabel.setVisible(true);
                                                studIDArea.setVisible(true);
                                                instIDLabel.setVisible(true);
                                                instIDArea.setVisible(true);
                                                addStudButton.setVisible(true);
                                                addInstButton.setVisible(true);

                                        } else {
                                                JOptionPane.showMessageDialog(this,
                                                                "Group already exists.Create another group");

                                        }
                                        connection.close();
                                } catch (Exception exp) {
                                        exp.printStackTrace();
                                        JOptionPane.showMessageDialog(this, "Something went wrong ");

                                }
                        }

                } else if (e.getSource() == addStudButton) {
                        if (groupName.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Enter group name first.");
                        } else {
                                Scanner reader = new Scanner(studIDArea.getText());
                                while (reader.hasNext()) {
                                        studID = reader.next();
                                        try {
                                                connection = DriverManager.getConnection(
                                                                "jdbc:mysql://localhost:3306/examsystem",
                                                                "root", "");
                                                PreparedStatement stmt = connection.prepareStatement(
                                                                "SELECT COUNT(sid) FROM student WHERE sid = (?);");
                                                stmt.setString(1, studID);
                                                ResultSet rsData = stmt.executeQuery();
                                                rsData.next();
                                                studExist = rsData.getInt(1);
                                                if (studExist == 0) {
                                                        JOptionPane.showMessageDialog(this, "Student not registered.");

                                                } else {
                                                        statement3 = connection.prepareStatement(
                                                                        "SELECT COUNT(sid) FROM student_group WHERE sid =(?); ");
                                                        statement3.setString(1, studID);
                                                        rs3 = statement3.executeQuery();
                                                        rs3.next();
                                                        num3 = rs3.getInt(1);
                                                        if (num3 == 0) {

                                                                statement4 = connection.prepareStatement(
                                                                                "INSERT INTO student_group VALUES (?,?) ;");
                                                                statement4.setString(1, groupName);
                                                                statement4.setString(2, studID);
                                                                statement4.executeUpdate();
                                                                succLabel1.setVisible(true);
                                                        } else {
                                                                JOptionPane.showMessageDialog(this,
                                                                                "This student is already assigned to a group.");

                                                        }
                                                }
                                                connection.close();

                                        } catch (Exception exp) {
                                                exp.printStackTrace();
                                                JOptionPane.showMessageDialog(this, "Something went wrong ");

                                        }
                                }
                        }
                } else if (e.getSource() == addInstButton) {
                        if (groupName.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Enter group name first.");
                        } else {
                                Scanner reader = new Scanner(instIDArea.getText());
                                while (reader.hasNext()) {
                                        instID = reader.next();

                                        try {
                                                connection = DriverManager.getConnection(
                                                                "jdbc:mysql://localhost:3306/examsystem",
                                                                "root", "");

                                                PreparedStatement stmt = connection.prepareStatement(
                                                                "SELECT COUNT(iid) FROM instructor WHERE iid = (?);");
                                                stmt.setString(1, instID);
                                                ResultSet rsData = stmt.executeQuery();
                                                rsData.next();
                                                instExist = rsData.getInt(1);
                                                if (instExist == 0) {
                                                        JOptionPane.showMessageDialog(this,
                                                                        "Instructor not registered.");

                                                } else {

                                                        statement3 = connection.prepareStatement(
                                                                        "SELECT COUNT(iid) FROM instructor_group WHERE iid=(?);");
                                                        statement3.setString(1, instID);
                                                        rs3 = statement3.executeQuery();
                                                        rs3.next();
                                                        num3 = rs3.getInt(1);
                                                        if (num3 == 0) {
                                                                statement4 = connection
                                                                                .prepareStatement(
                                                                                                "INSERT INTO instructor_group VALUES(?,?);");
                                                                statement4.setString(1, groupName);
                                                                statement4.setString(2, instID);
                                                                statement4.executeUpdate();
                                                                succLabel2.setVisible(true);
                                                        } else {
                                                                JOptionPane.showMessageDialog(this,
                                                                                "This instructor is already assigned to a group.");

                                                        }
                                                }
                                                connection.close();

                                        } catch (Exception exp) {
                                                exp.printStackTrace();
                                                JOptionPane.showMessageDialog(this, "Something went wrong ");

                                        }
                                }
                        }

                }
                updateTables();

        }

}
