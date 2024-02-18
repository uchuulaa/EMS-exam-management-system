package instructor;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class InstructorHome extends JFrame {
    public static int open = 0;// flag for actions when mouse click happen to open intended page

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InstructorHome frame = new InstructorHome();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public InstructorHome() {

        setTitle("Instructor-Home page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1271, 768);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblWelcome = new JLabel("Welcome Instructor, please choose what you want to do");
        lblWelcome.setForeground(new Color(0, 64, 128));
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblWelcome.setBounds(52, 69, 923, 30);
        panel.add(lblWelcome);

        JButton btnAddQuestion = new JButton("Add New Question");
        btnAddQuestion.setIcon(new ImageIcon(InstructorHome.class.getResource("../Images/add new question.png")));
        btnAddQuestion.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAddQuestion.setBounds(114, 142, 365, 50);
        btnAddQuestion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (open == 0) {
                    new addNewQuestion().setVisible(true);
                    open = 1;
                } else {
                    JFrame jf = new JFrame();
                    jf.setAlwaysOnTop(true);
                    JOptionPane.showMessageDialog(jf, "One form is already opened!");
                }
            }
        });
        panel.add(btnAddQuestion);

        JButton btnUpdateQuestion = new JButton("Update Question");
        btnUpdateQuestion.setIcon(new ImageIcon(InstructorHome.class.getResource("../Images/Update Question.png")));
        btnUpdateQuestion.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnUpdateQuestion.setBounds(114, 256, 365, 50);
        btnUpdateQuestion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (open == 0) {
                    new updateQuestion().setVisible(true);// linked
                    open = 1;
                } else {
                    JFrame jf = new JFrame();
                    jf.setAlwaysOnTop(true);
                    JOptionPane.showMessageDialog(jf, "One form is already opened!");
                }
            }
        });
        panel.add(btnUpdateQuestion);

        JButton btnAllQuestions = new JButton("All Questions");
        btnAllQuestions.setIcon(new ImageIcon(InstructorHome.class.getResource("../Images/all questions.png")));
        btnAllQuestions.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAllQuestions.setBounds(114, 380, 365, 50);
        btnAllQuestions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (open == 0) {
                    new AllQuestion().setVisible(true);// linked to all question
                    open = 1;
                } else {
                    JFrame jf = new JFrame();
                    jf.setAlwaysOnTop(true);
                    JOptionPane.showMessageDialog(jf, "One form is already opened!");
                }
            }
        });
        panel.add(btnAllQuestions);

        JButton btnDeleteQuestion = new JButton("Delete Question");
        btnDeleteQuestion.setIcon(new ImageIcon(InstructorHome.class.getResource("../Images/delete Question.png")));
        btnDeleteQuestion.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnDeleteQuestion.setBounds(683, 142, 515, 50);
        btnDeleteQuestion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (open == 0) {
                    new DeleteQuestion().setVisible(true);// linked to delete question
                    open = 1;
                } else {
                    JFrame jf = new JFrame();
                    jf.setAlwaysOnTop(true);
                    JOptionPane.showMessageDialog(jf, "One form is already opened!");
                }

            }
        });
        panel.add(btnDeleteQuestion);

        JButton btnAllStudents = new JButton("All Students Result");
        btnAllStudents.setIcon(new ImageIcon(InstructorHome.class.getResource("../Images/all student result.png")));
        btnAllStudents.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAllStudents.setBounds(684, 279, 514, 50);
        btnAllStudents.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (open == 0) {
                    new AllStudentsResult().setVisible(true);// linked to all students result
                    open = 1;
                } else {
                    JFrame jf = new JFrame();
                    jf.setAlwaysOnTop(true);
                    JOptionPane.showMessageDialog(jf, "One form is already opened!");
                }

            }
        });
        panel.add(btnAllStudents);

        JButton btnLogout = new JButton("Logout ");
        btnLogout.setBorderPainted(false);
        btnLogout.setBorder(UIManager.getBorder("RadioButton.border"));
        btnLogout.setBackground(new Color(255, 0, 128));
        btnLogout.setForeground(new Color(255, 255, 255));
        btnLogout.setIcon(null);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnLogout.setBounds(683, 380, 515, 50);
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame jf = new JFrame();
                jf.setAlwaysOnTop(true);// showing on the top of for hiding other opened form if any when other is to be
                                        // opened
                int a = JOptionPane.showConfirmDialog(jf, "Do you want to logout?", "select",
                        JOptionPane.YES_NO_OPTION);
                if (a == 0) {
                    setVisible(false);
                    new LoginInstructor().setVisible(true);
                }
            }
        });
        panel.add(btnLogout);
    }
}
