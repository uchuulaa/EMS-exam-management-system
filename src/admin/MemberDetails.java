package admin;

import homepage.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDetails extends JPanel implements ActionListener, MenuListener {
    private JTextField IDField, nameField, genderField, phoneField, emailField, depField, instField, groupField;
    private JButton searchButton, updateButton;
    private String ID, name, gender, email, institute, department, group;
    private Connection connection;
    private JTable studTable, instTable;
    private DefaultTableModel studModel, instModel;

    public MemberDetails() {
        setLayout(null);

        JLabel IDLabel = new JLabel("Enter ID");
        IDLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        IDLabel.setBounds(88, 44, 114, 20);
        add(IDLabel);

        IDField = new JTextField();
        IDField.setBounds(191, 46, 105, 20);
        add(IDField);
        IDField.setColumns(10);

        searchButton = new JButton("Search");
        searchButton.setBounds(338, 41, 105, 30);
        add(searchButton);

        JLabel nameLabel = new JLabel("Name ");
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        nameLabel.setBounds(88, 120, 100, 35);
        add(nameLabel);

        JLabel gender = new JLabel("Gender");
        gender.setFont(new Font("Tahoma", Font.PLAIN, 14));
        gender.setBounds(88, 178, 100, 35);
        add(gender);

        JLabel phone = new JLabel("Phone number");
        phone.setFont(new Font("Tahoma", Font.PLAIN, 14));
        phone.setBounds(88, 224, 142, 35);
        add(phone);

        JLabel email = new JLabel("E-mail");
        email.setFont(new Font("Tahoma", Font.PLAIN, 14));
        email.setBounds(90, 270, 100, 35);
        add(email);

        JLabel department = new JLabel("Department");
        department.setFont(new Font("Tahoma", Font.PLAIN, 14));
        department.setBounds(90, 320, 100, 35);
        add(department);

        JLabel institute = new JLabel("Institute");
        institute.setFont(new Font("Tahoma", Font.PLAIN, 14));
        institute.setBounds(90, 370, 100, 35);
        add(institute);

        nameField = new JTextField();
        nameField.setBounds(245, 129, 360, 20);
        add(nameField);
        nameField.setColumns(10);

        genderField = new JTextField();
        genderField.setColumns(10);
        genderField.setBounds(245, 178, 360, 20);
        add(genderField);

        phoneField = new JTextField();
        phoneField.setColumns(10);
        phoneField.setBounds(245, 224, 360, 20);
        add(phoneField);

        emailField = new JTextField();
        emailField.setColumns(10);
        emailField.setBounds(245, 270, 360, 20);
        add(emailField);

        depField = new JTextField();
        depField.setColumns(10);
        depField.setBounds(245, 320, 360, 20);
        add(depField);

        instField = new JTextField();
        instField.setColumns(10);
        instField.setBounds(245, 370, 360, 20);
        add(instField);

        updateButton = new JButton("UPDATE");
        updateButton.setBounds(547, 474, 105, 30);
        add(updateButton);

        searchButton.addActionListener(this);
        updateButton.addActionListener(this);
        Index.createGroup.addMenuListener(this);
        Index.groupDetails.addMenuListener(this);
        Index.memberDetails.addMenuListener(this);

        JLabel group = new JLabel("Group");
        group.setFont(new Font("Tahoma", Font.PLAIN, 14));
        group.setBounds(88, 420, 94, 20);
        add(group);

        groupField = new JTextField();
        groupField.setColumns(10);
        groupField.setBounds(245, 420, 360, 20);
        add(groupField);
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

        JScrollPane studScrollpane = new JScrollPane();
        studScrollpane.setBounds(749, 82, 248, 239);
        add(studScrollpane);
        studTable = new JTable();
        studTable.setModel(studModel);

        studScrollpane.setViewportView(studTable);

        JScrollPane instScrollpane = new JScrollPane();
        instScrollpane.setBounds(749, 381, 257, 226);
        add(instScrollpane);
        instTable = new JTable();
        instTable.setModel(instModel);

        instScrollpane.setViewportView(instTable);

        JLabel allStudLabel = new JLabel("REGISTRED STUDENTS");
        allStudLabel.setBounds(775, 40, 197, 30);
        add(allStudLabel);

        JLabel allInstTable = new JLabel("REGISTRED INSTRUCTORS");
        allInstTable.setBounds(775, 340, 197, 30);
        add(allInstTable);
        updateTables();

    }

    public void updateTables() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/examsystem",
                    "root", "");
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT sid,name FROM student");
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
                    "SELECT iid,name FROM instructor ");
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

    public void menuSelected(MenuEvent e) {
        if (e.getSource() == Index.createGroup) {
            Index.cardLayout.show(Index.contentPane, "adminhome");

        } else if (e.getSource() == Index.groupDetails) {
            Index.cardLayout.show(Index.contentPane, "gpdetail");
        }
        updateTables();
    }

    public void menuDeselected(MenuEvent e) {

    }

    public void menuCanceled(MenuEvent e) {

    }

    public void actionPerformed(ActionEvent e) {
        int studcount, instcount;
        boolean isStudent = false, isInstuctor = false;
        ID = IDField.getText();
        name = nameField.getText();
        gender = genderField.getText();
        phoneField.getText();
        email = emailField.getText();
        department = depField.getText();
        institute = instField.getText();
        group = groupField.getText();
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/examsystem",
                    "root", "");
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT COUNT(sid) FROM student WHERE sid = (?);");
            stmt.setString(1, ID);
            ResultSet rsData = stmt.executeQuery();
            rsData.next();
            studcount = rsData.getInt(1);
            PreparedStatement stmt2 = connection.prepareStatement(
                    "SELECT COUNT(iid) FROM instructor WHERE iid = (?);");
            stmt2.setString(1, ID);
            ResultSet rsData2 = stmt2.executeQuery();
            rsData2.next();
            instcount = rsData2.getInt(1);
            if (studcount == 1 && instcount == 0) {
                isStudent = true;
            } else if (studcount == 0 && instcount == 1) {
                isInstuctor = true;
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        if (e.getSource() == searchButton) {
            if (ID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter ID to view details");
            } else {

                try {

                    if (isStudent) {
                        PreparedStatement stmt3 = connection.prepareStatement(
                                "SELECT name,gender,phone_no,email,department,institution FROM student WHERE sid=(?)");
                        stmt3.setString(1, ID);
                        ResultSet rs3 = stmt3.executeQuery();
                        rs3.next();
                        nameField.setText(rs3.getString("name"));
                        genderField.setText(rs3.getString("gender"));
                        phoneField.setText(rs3.getString("phone_no"));
                        emailField.setText(rs3.getString("email"));
                        depField.setText(rs3.getString("department"));
                        instField.setText(rs3.getString("institution"));
                        PreparedStatement stmt4 = connection.prepareStatement(
                                "SELECT group_id FROM student_group WHERE sid=(?)");
                        stmt4.setString(1, ID);
                        ResultSet rs4 = stmt4.executeQuery();
                        if (rs4.next()) {
                            groupField.setText(rs4.getString("group_id"));
                        } else {
                            groupField.setText("Not assigned");
                        }

                    } else if (isInstuctor) {
                        PreparedStatement stmt4 = connection.prepareStatement(
                                "SELECT name,gender,phone_no,email,department,institute FROM instructor WHERE iid=(?)");
                        stmt4.setString(1, ID);
                        ResultSet rs4 = stmt4.executeQuery();
                        rs4.next();
                        nameField.setText(rs4.getString("name"));
                        genderField.setText(rs4.getString("gender"));
                        phoneField.setText(Integer.toString(rs4.getInt("phone_no")));
                        emailField.setText(rs4.getString("email"));
                        depField.setText(rs4.getString("department"));
                        instField.setText(rs4.getString("institution"));
                        PreparedStatement stmt5 = connection.prepareStatement(
                                "SELECT group_id FROM instructor_group WHERE iid=(?)");
                        stmt5.setString(1, ID);
                        ResultSet rs5 = stmt5.executeQuery();
                        if (rs5.next()) {
                            groupField.setText(rs5.getString("group_id"));
                        } else {
                            groupField.setText("Not assigned");
                        }
                    } else if (!isStudent && !isInstuctor) {
                        JOptionPane.showMessageDialog(this, "Unknown ID");
                    } else {
                        JOptionPane.showMessageDialog(this, "Something went wrong.");

                    }
                    connection.close();
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }

        } else if (e.getSource() == updateButton) {
            ID = IDField.getText();
            name = nameField.getText();
            gender = genderField.getText();
            String phone = phoneField.getText();
            email = emailField.getText();
            department = depField.getText();
            institute = instField.getText();
            if (ID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter ID to update information");
            } else if (name.isEmpty() || gender.isEmpty() || phone.isEmpty() || email.isEmpty() || department.isEmpty()
                    || institute.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Some fields are empty");

            } else {
                // int phone_no = Integer.parseInt(phone);
                boolean groupExist = false;
                try {
                    connection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/examsystem",
                            "root", "");
                    PreparedStatement stmt2 = connection.prepareStatement(
                            "SELECT group_id FROM student_group WHERE group_id=(?)");
                    stmt2.setString(1, group);
                    ResultSet rs2 = stmt2.executeQuery();

                    PreparedStatement stmt3 = connection.prepareStatement(
                            "SELECT group_id FROM instructor_group WHERE group_id=(?)");
                    stmt3.setString(1, group);
                    ResultSet rs3 = stmt3.executeQuery();
                    if (rs2.next() || rs3.next()) {
                        groupExist = true;
                    }
                    if (groupExist) {
                        if (isStudent) {
                            PreparedStatement stmt4 = connection.prepareStatement(
                                    "SELECT group_id FROM student_group WHERE sid=(?)");
                            stmt4.setString(1, ID);
                            ResultSet rs4 = stmt4.executeQuery();
                            if (rs4.next()) {
                                PreparedStatement statement = connection.prepareStatement(
                                        "DELETE FROM student_group WHERE sid =(?) ;");
                                statement.setString(1, ID);
                                statement.executeUpdate();
                            }
                            PreparedStatement statement2 = connection.prepareStatement(
                                    "INSERT INTO student_group VALUES (?,?) ;");
                            statement2.setString(1, group);
                            statement2.setString(2, ID);
                            statement2.executeUpdate();
                            PreparedStatement stmt = connection.prepareStatement(
                                    "UPDATE student SET name=(?),gender=(?),phone_no=(?),email=(?),department=(?),institution=(?) WHERE sid=(?)");
                            stmt.setString(1, name);
                            stmt.setString(2, gender);
                            stmt.setString(3, phone);
                            stmt.setString(4, email);
                            stmt.setString(5, department);
                            stmt.setString(6, institute);
                            stmt.setString(7, ID);
                            stmt.executeUpdate();
                            JOptionPane.showMessageDialog(this, "Updated successfully.");

                        } else if (isInstuctor) {
                            PreparedStatement stmt4 = connection.prepareStatement(
                                    "SELECT iid FROM instructor_group WHERE iid=(?)");
                            stmt4.setString(1, ID);
                            ResultSet rs4 = stmt4.executeQuery();
                            if (rs4.next()) {
                                PreparedStatement statement = connection.prepareStatement(
                                        "DELETE FROM instructor_group WHERE iid =(?) ;");
                                statement.setString(1, ID);
                                statement.executeUpdate();
                            }

                            PreparedStatement statement2 = connection.prepareStatement(
                                    "INSERT INTO instructor_group VALUES (?,?) ;");
                            statement2.setString(1, group);
                            statement2.setString(2, ID);
                            statement2.executeUpdate();
                            PreparedStatement stmt = connection.prepareStatement(
                                    "UPDATE instructor SET name=(?),gender=(?),phone_no=(?),email=(?),department=(?),institutiion=(?) WHERE iid=(?)");
                            stmt.setString(1, name);
                            stmt.setString(2, gender);
                            stmt.setString(3, phone);
                            stmt.setString(4, email);
                            stmt.setString(5, department);
                            stmt.setString(6, institute);
                            stmt.setString(7, ID);
                            stmt.executeUpdate();
                            JOptionPane.showMessageDialog(this, "Updated successfully.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Group doesn't exist");
                    }

                    connection.close();
                } catch (Exception exp) {
                    exp.printStackTrace();
                    JOptionPane.showMessageDialog(this, "something went wrong.");

                }
            }
        }
        updateTables();
    }
}
