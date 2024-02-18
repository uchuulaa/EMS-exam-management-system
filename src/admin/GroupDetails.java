package admin;

import homepage.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.TreeSet;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;

public class GroupDetails extends JPanel implements ActionListener, MenuListener {
        private JTable studTable, instTable;
        private DefaultTableModel studModel, instModel, groupModel;
        private JTextField groupnumField, addStudField, removeStudField, addInstField, removeInstField;
        private JButton findgpButton,
                        addStudButton, removeStudButton, addInstButton, removeInstButton;

        private String groupName;
        private JTable groupInfoTable;
        private TreeSet<String> gpNames;

        public GroupDetails() {

                JLabel groupnumLabel = new JLabel("GROUP NAME");
                groupnumLabel.setBounds(50, 31, 126, 35);
                groupnumLabel.setHorizontalAlignment(SwingConstants.CENTER);

                groupnumField = new JTextField();
                groupnumField.setBounds(198, 38, 96, 20);
                groupnumField.setColumns(10);

                findgpButton = new JButton("View Details");
                findgpButton.setBounds(338, 37, 117, 23);

                String[][] studData = new String[25][5];
                String[] studCols = { "ID", "Name" };
                JScrollPane studscroll = new JScrollPane();
                studscroll.setBounds(60, 121, 294, 328);
                studModel = new DefaultTableModel(studData, studCols) {
                        @Override
                        public boolean isCellEditable(int row, int col) {
                                return false;
                        }
                };
                studTable = new JTable();
                studTable.setModel(studModel);
                studscroll.setViewportView(studTable);

                JLabel studLabel = new JLabel("ADDED STUDENTS ");
                studLabel.setBounds(50, 77, 162, 33);
                studLabel.setHorizontalAlignment(SwingConstants.CENTER);

                JLabel instLabel = new JLabel("ADDED INSTRUCTORS ");
                instLabel.setBounds(413, 76, 170, 34);
                instLabel.setHorizontalAlignment(SwingConstants.CENTER);

                String[][] instData = new String[25][5];
                String[] instCols = { "ID", "Name" };
                JScrollPane instScroll = new JScrollPane();
                instScroll.setBounds(413, 121, 344, 328);
                instModel = new DefaultTableModel(instData, instCols) {
                        @Override
                        public boolean isCellEditable(int row, int col) {
                                return false;
                        }
                };
                instTable = new JTable();
                instTable.setModel(instModel);
                instScroll.setViewportView(instTable);

                JLabel addStudLabel = new JLabel("ADD STUDENT ");
                addStudLabel.setBounds(35, 483, 96, 28);
                addStudLabel.setHorizontalAlignment(SwingConstants.CENTER);

                JLabel removeStudLabel = new JLabel("REMOVE STUDENT  ");
                removeStudLabel.setHorizontalAlignment(SwingConstants.CENTER);
                removeStudLabel.setBounds(10, 522, 138, 28);

                JLabel addInstLabel = new JLabel("ADD INSTRUCTOR  ");
                addInstLabel.setBounds(437, 483, 117, 28);
                addInstLabel.setHorizontalAlignment(SwingConstants.CENTER);

                JLabel removeInstLabel = new JLabel("REMOVE INSTRUCTOR   ");
                removeInstLabel.setBounds(413, 522, 182, 28);
                removeInstLabel.setHorizontalAlignment(SwingConstants.CENTER);

                addStudField = new JTextField();
                addStudField.setBounds(159, 487, 96, 20);
                addStudField.setColumns(10);

                removeStudField = new JTextField();
                removeStudField.setBounds(158, 526, 96, 20);
                removeStudField.setColumns(10);

                addInstField = new JTextField();
                addInstField.setBounds(595, 487, 96, 20);
                addInstField.setColumns(10);

                removeInstField = new JTextField();
                removeInstField.setBounds(595, 526, 96, 20);
                removeInstField.setColumns(10);

                addStudButton = new JButton("ADD");
                addStudButton.setBounds(287, 485, 76, 25);

                removeStudButton = new JButton("REMOVE");
                removeStudButton.setBounds(287, 524, 90, 25);

                addInstButton = new JButton("ADD");
                addInstButton.setBounds(723, 485, 76, 25);

                removeInstButton = new JButton("REMOVE");
                removeInstButton.setBounds(723, 524, 90, 25);
                setLayout(null);
                add(groupnumLabel);
                add(groupnumField);
                add(findgpButton);
                add(studLabel);
                add(instLabel);
                add(studscroll);
                add(addStudLabel);
                add(removeStudLabel);
                add(addStudField);
                add(removeStudField);
                add(removeStudButton);
                add(addStudButton);
                add(removeInstLabel);
                add(addInstLabel);
                add(addInstField);
                add(removeInstField);
                add(addInstButton);
                add(removeInstButton);
                add(instScroll);

                String[][] groupData = new String[20][3];
                String[] groupInfoCols = { "GROUP ID", "No of STUDENTS", "No of INSTRUCTORS" };
                groupModel = new DefaultTableModel(groupData, groupInfoCols) {
                        @Override
                        public boolean isCellEditable(int row, int col) {
                                return false;
                        }
                };
                groupInfoTable = new JTable();
                groupInfoTable.setModel(groupModel);

                JScrollPane groupInfoScroll = new JScrollPane();
                groupInfoScroll.setBounds(833, 121, 379, 328);
                groupInfoScroll.setViewportView(groupInfoTable);
                add(groupInfoScroll);
                updateTables();

                JLabel groupInfoLabel = new JLabel("AVAILABLE GROUPS");
                groupInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                groupInfoLabel.setBounds(847, 71, 162, 45);
                add(groupInfoLabel);

                findgpButton.addActionListener(this);
                addStudButton.addActionListener(this);
                removeStudButton.addActionListener(this);
                addInstButton.addActionListener(this);
                removeInstButton.addActionListener(this);

                Index.createGroup.addMenuListener(this);
                Index.groupDetails.addMenuListener(this);
                Index.memberDetails.addMenuListener(this);

        }

        public void menuSelected(MenuEvent e) {
                if (e.getSource() == Index.createGroup) {
                        Index.cardLayout.show(Index.contentPane, "adminhome");

                } else if (e.getSource() == Index.memberDetails) {
                        Index.cardLayout.show(Index.contentPane, "memdetail");

                }
                updateTables();
        }

        public void menuDeselected(MenuEvent e) {

        }

        public void menuCanceled(MenuEvent e) {

        }

        public void display(String gpName) {
                try {
                        Connection connection = DriverManager.getConnection(
                                        "jdbc:mysql://localhost:3306/examsystem",
                                        "root", "");
                        PreparedStatement stmt = connection.prepareStatement(
                                        "SELECT sid,name FROM student WHERE sid  IN(SELECT sid FROM student_group WHERE group_id =(?)) ");
                        stmt.setString(1, gpName);
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
                                        "SELECT iid,name FROM instructor WHERE iid  IN(SELECT iid FROM instructor_group WHERE group_id =(?)) ");
                        stmt2.setString(1, gpName);
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

        public void updateTables() {
                int tableIndex = 0;
                try {
                        Connection connection = DriverManager.getConnection(
                                        "jdbc:mysql://localhost:3306/examsystem",
                                        "root", "");
                        gpNames = new TreeSet<>();
                        PreparedStatement stmt = connection.prepareStatement(
                                        "SELECT group_id FROM student_group");
                        ResultSet rsData = stmt.executeQuery();
                        while (rsData.next()) {
                                gpNames.add(rsData.getString("group_id"));
                        }
                        rsData.close();
                        PreparedStatement stmt2 = connection.prepareStatement("SELECT group_id FROM instructor_group");
                        ResultSet rsData2 = stmt2.executeQuery();
                        while (rsData2.next()) {
                                gpNames.add(rsData2.getString("group_id"));
                        }
                        rsData2.close();
                        for (String name : gpNames) {
                                int studcount, instcount;

                                PreparedStatement stmt3 = connection.prepareStatement(
                                                "SELECT count(sid) FROM student_group WHERE group_id=(?) ");
                                stmt3.setString(1, name);
                                ResultSet rsData3 = stmt3.executeQuery();
                                rsData3.next();
                                studcount = rsData3.getInt(1);
                                PreparedStatement stmt4 = connection.prepareStatement(
                                                "SELECT count(iid) FROM instructor_group WHERE group_id=(?)");
                                stmt4.setString(1, name);
                                ResultSet rsData4 = stmt4.executeQuery();
                                rsData4.next();
                                instcount = rsData4.getInt(1);
                                groupModel.setValueAt(name, tableIndex, 0);
                                groupModel.setValueAt(studcount, tableIndex, 1);
                                groupModel.setValueAt(instcount, tableIndex, 2);
                                tableIndex++;
                                rsData3.close();
                                rsData4.close();
                        }
                        for (int j = tableIndex; j < groupModel.getRowCount(); j++) {
                                groupModel.setValueAt(null, j, 0);
                                groupModel.setValueAt(null, j, 1);
                                groupModel.setValueAt(null, j, 2);
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                if (groupName != null) {
                        display(groupName);
                }

        }

        public void actionPerformed(ActionEvent e) {
                int studExist = 0, instExist = 0;
                String studID, instID;
                Connection connection = null;
                groupName = groupnumField.getText();

                if (e.getSource() == findgpButton) {

                        if (groupName.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Enter the group name to view the members");
                        } else {
                                try {
                                        connection = DriverManager.getConnection(
                                                        "jdbc:mysql://localhost:3306/examsystem",
                                                        "root", "");
                                        PreparedStatement statement1 = connection.prepareStatement(
                                                        "SELECT COUNT(group_id) FROM student_group WHERE group_id =(?); ");
                                        statement1.setString(1, groupName);
                                        ResultSet rs1 = statement1.executeQuery();
                                        rs1.next();
                                        int num1 = rs1.getInt(1);
                                        PreparedStatement statement2 = connection.prepareStatement(
                                                        "SELECT COUNT(group_id) FROM instructor_group WHERE group_id = (?); ");
                                        statement2.setString(1, groupName);
                                        ResultSet rs2 = statement2.executeQuery();
                                        rs2.next();
                                        int num2 = rs2.getInt(1);

                                        if (num1 != 0 || num2 != 0) {
                                                display(groupName);
                                        } else {
                                                JOptionPane.showMessageDialog(this, "Group doens't exist");
                                        }
                                } catch (Exception exp) {
                                        JOptionPane.showMessageDialog(this, "Something went wrong");
                                        exp.printStackTrace();
                                }
                        }

                } else if (e.getSource() == addStudButton) {
                        studID = addStudField.getText();
                        if (studID.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Enter ID first");
                        } else if (groupName.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Enter group name first");

                        } else {
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
                                                PreparedStatement statement3 = connection.prepareStatement(
                                                                "SELECT COUNT(sid) FROM student_group WHERE sid =(?); ");
                                                statement3.setString(1, studID);
                                                ResultSet rs3 = statement3.executeQuery();
                                                rs3.next();
                                                int num = rs3.getInt(1);
                                                if (num == 0) {
                                                        PreparedStatement statement4 = connection.prepareStatement(
                                                                        "INSERT INTO student_group VALUES (?,?) ;");
                                                        statement4.setString(1, groupName);
                                                        statement4.setString(2, studID);
                                                        statement4.executeUpdate();
                                                        JOptionPane.showMessageDialog(this,
                                                                        "Student is added succesfully.");

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
                } else if (e.getSource() == addInstButton) {

                        instID = addInstField.getText();
                        if (instID.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Enter ID first");
                        } else if (groupName.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Enter group name first");

                        } else {
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
                                                JOptionPane.showMessageDialog(this, "Instructor not registered.");

                                        } else {

                                                PreparedStatement statement3 = connection.prepareStatement(
                                                                "SELECT COUNT(iid) FROM instructor_group WHERE iid=(?);");
                                                statement3.setString(1, instID);
                                                ResultSet rs3 = statement3.executeQuery();
                                                rs3.next();
                                                int num = rs3.getInt(1);
                                                if (num == 0) {
                                                        PreparedStatement statement4 = connection
                                                                        .prepareStatement(
                                                                                        "INSERT INTO instructor_group VALUES(?,?);");
                                                        statement4.setString(1, groupName);
                                                        statement4.setString(2, instID);
                                                        statement4.executeUpdate();
                                                        JOptionPane.showMessageDialog(this,
                                                                        "Instructor is added succesfully.");

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

                } else if (e.getSource() == removeStudButton) {

                        studID = removeStudField.getText();
                        if (studID.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Enter ID first");
                        } else if (groupName.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Enter group name first");

                        } else {
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
                                                PreparedStatement statement3 = connection.prepareStatement(
                                                                "SELECT COUNT(sid) FROM student_group WHERE sid =(?) AND group_id=(?); ");
                                                statement3.setString(1, studID);
                                                statement3.setString(2, groupName);
                                                ResultSet rs3 = statement3.executeQuery();
                                                rs3.next();
                                                int num = rs3.getInt(1);
                                                if (num != 0) {
                                                        PreparedStatement statement4 = connection.prepareStatement(
                                                                        "DELETE FROM student_group WHERE sid =(?) ;");
                                                        statement4.setString(1, studID);
                                                        statement4.executeUpdate();
                                                        JOptionPane.showMessageDialog(this,
                                                                        "Student is removed succesfully.");

                                                } else {
                                                        JOptionPane.showMessageDialog(this,
                                                                        "This student is not in this group.");

                                                }
                                        }
                                        connection.close();

                                } catch (Exception exp) {
                                        exp.printStackTrace();
                                        JOptionPane.showMessageDialog(this, "Something went wrong ");

                                }

                        }
                } else if (e.getSource() == removeInstButton) {
                        instID = removeInstField.getText();
                        if (instID.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Enter id first");
                        } else if (groupName.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Enter group name first");

                        } else {
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
                                                JOptionPane.showMessageDialog(this, "Instructor not registered.");

                                        } else {

                                                PreparedStatement statement3 = connection.prepareStatement(
                                                                "SELECT COUNT(iid) FROM instructor_group WHERE iid=(?) AND group_id =(?);");
                                                statement3.setString(1, instID);
                                                statement3.setString(2, groupName);
                                                ResultSet rs3 = statement3.executeQuery();
                                                rs3.next();
                                                int num = rs3.getInt(1);
                                                if (num != 0) {
                                                        PreparedStatement statement4 = connection
                                                                        .prepareStatement(
                                                                                        "DELETE FROM instructor_group WHERE iid =(?);");
                                                        statement4.setString(1, instID);
                                                        statement4.executeUpdate();
                                                        JOptionPane.showMessageDialog(this,
                                                                        "Instructor is removed succesfully.");

                                                } else {
                                                        JOptionPane.showMessageDialog(this,
                                                                        "This instructor is not in this group.");

                                                }
                                        }
                                        connection.close();

                                } catch (Exception exp) {
                                        exp.printStackTrace();
                                        JOptionPane.showMessageDialog(this, "Something went wrong ");

                                }
                        }
                }
                updateTables();
        }
}
