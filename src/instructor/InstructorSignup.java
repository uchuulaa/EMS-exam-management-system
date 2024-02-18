package instructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import ems.ConnectionProvider;
public class InstructorSignup extends JFrame implements ActionListener {

   
	// GUI components
    private JLabel nameLabel, genderLabel, phoneLabel, emailLabel, passwordLabel,idLabel,titleLabel;
    private JTextField nameField, phoneField, emailField;
    private JPasswordField passwordField;
    private JButton signupButton;
    private JTextField idField;
    private JComboBox<String> genderCombo; // Define the genderCombo variable

    
   
	public InstructorSignup() {
    	setLocation(new Point(150, 183));
        // Set up the GUI components
    	idLabel = new JLabel("Instructor ID");
    	idLabel.setForeground(new Color(0, 64, 64));
    	idLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
    	idLabel.setBounds(208, 125, 104, 86);
        nameLabel = new JLabel("Name:");
        nameLabel.setForeground(new Color(0, 64, 64));
        nameLabel.setBounds(208, 196, 104, 81);
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        genderLabel = new JLabel("Gender:");
        genderLabel.setForeground(new Color(0, 64, 64));
        genderLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        genderLabel.setBounds(208, 391, 104, 64);
        phoneLabel = new JLabel("Phone No:");
        phoneLabel.setForeground(new Color(0, 64, 64));
        phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        phoneLabel.setBounds(208, 257, 110, 86);
        emailLabel = new JLabel("Email:");
        emailLabel.setForeground(new Color(0, 64, 64));
        emailLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        emailLabel.setBounds(208, 325, 104, 86);
        passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(new Color(0, 64, 64));
        passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        passwordLabel.setBounds(208, 467, 104, 86);

        nameField = new JTextField(20);
        nameField.setFont(new Font("Tahoma", Font.BOLD, 14));
        nameField.setBounds(334, 218, 321, 41);
        phoneField = new JTextField(20);
        phoneField.setFont(new Font("Tahoma", Font.BOLD, 14));
        phoneField.setBounds(334, 277, 321, 48);
        emailField = new JTextField(20);
        emailField.setFont(new Font("Tahoma", Font.BOLD, 14));
        emailField.setBounds(334, 345, 321, 48);
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Tahoma", Font.BOLD, 14));
        passwordField.setBounds(334, 487, 321, 48);

        //genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        
        signupButton = new JButton("Signup");
        signupButton.setBorderPainted(false);
        signupButton.setForeground(new Color(255, 255, 255));
        signupButton.setBackground(new Color(64, 128, 128));
        signupButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        signupButton.setBounds(483, 605, 126, 48);
        signupButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(idLabel);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(genderLabel);
        //panel.add(genderComboBox);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(signupButton);

        getContentPane().add(panel);
        
        idField = new JTextField(20);
        idField.setFont(new Font("Tahoma", Font.BOLD, 14));
        idField.setBounds(334, 153, 321, 31);
        panel.add(idField);
        
        genderCombo = new JComboBox<>(new String[]{"-----choose your gender-----", "Male", "Female", "Other"}); // Initialize the genderCombo variable
        genderCombo.setFont(new Font("Tahoma", Font.BOLD, 14));
        genderCombo.setBounds(334, 413, 321, 42);
        panel.add(genderCombo);
        
        titleLabel = new JLabel("Instructor SignUp");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
        titleLabel.setForeground(new Color(0, 64, 128));
        titleLabel.setBounds(246, 38, 409, 55);
        panel.add(titleLabel);
        setTitle("Instructor Signup");
        setSize(911, 794);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // Get the data from the text fields and combo box
        String iid= idField.getText();
        String name = nameField.getText();
        String gender = (String) genderCombo.getSelectedItem(); // Use the genderCombo variable
        String phone = phoneField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        // Insert the data into the database
        try {
            // Open a connection
            Connection conn = ConnectionProvider.getCon();

            // Prepare and execute a query to insert instructor data into the table
            String sql = "INSERT INTO instructor (iid,name, gender, phone_no, email, password) VALUES (?, ?, ?, ?, ? ,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, iid);
            pstmt.setString(2, name);
            pstmt.setString(3, gender); // Set the gender value in the query
            pstmt.setString(4, phone);
            pstmt.setString(5, email);
            pstmt.setString(6, password);
            pstmt.executeUpdate();

            // Close the connection and show a success message
            conn.close();
            JOptionPane.showMessageDialog(this, "Signup successful!");
            setVisible(false);
            new LoginInstructor().setVisible(true); //sending to login page after sign up 

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new InstructorSignup();
    }
}
