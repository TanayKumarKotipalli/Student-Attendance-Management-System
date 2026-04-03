import javax.swing.*;
import java.sql.*;

public class Login extends JFrame {

    public Login() {

        setTitle("Attendance Management System");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel title = new JLabel("Attendance System", SwingConstants.CENTER);
        title.setBounds(80, 30, 240, 30);
        add(title);

        JButton studentLoginBtn = new JButton("Student Login");
        studentLoginBtn.setBounds(100, 80, 180, 30);
        add(studentLoginBtn);

        JButton facultyLoginBtn = new JButton("Faculty Login");
        facultyLoginBtn.setBounds(100, 120, 180, 30);
        add(facultyLoginBtn);

        JButton registerStudentBtn = new JButton("Register Student");
        registerStudentBtn.setBounds(100, 160, 180, 30);
        add(registerStudentBtn);

        JButton registerFacultyBtn = new JButton("Register Faculty");
        registerFacultyBtn.setBounds(100, 200, 180, 30);
        add(registerFacultyBtn);

        // Button Actions

        studentLoginBtn.addActionListener(e -> {
            dispose();
            new StudentLogin();
        });

        facultyLoginBtn.addActionListener(e -> {
            dispose();
            new FacultyLogin();
        });

        registerStudentBtn.addActionListener(e -> {
            dispose();
            new Register();  // Your existing student register class
        });

        registerFacultyBtn.addActionListener(e -> {
            dispose();
            new FacultyRegister();
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }
}
