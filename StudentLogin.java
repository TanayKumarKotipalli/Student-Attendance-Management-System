import javax.swing.*;
import java.sql.*;
import javax.swing.*;

public class StudentLogin extends JFrame {

    JTextField emailField;
    JPasswordField passwordField;

    public StudentLogin() {

        setTitle("Student Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel title = new JLabel("Student Login", SwingConstants.CENTER);
        title.setBounds(100, 30, 200, 25);
        add(title);

        JLabel emailLbl = new JLabel("Email:");
        emailLbl.setBounds(60, 80, 80, 25);
        add(emailLbl);

        emailField = new JTextField();
        emailField.setBounds(150, 80, 180, 25);
        add(emailField);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setBounds(60, 120, 80, 25);
        add(passLbl);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 120, 180, 25);
        add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(140, 170, 100, 30);
        add(loginBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(140, 210, 100, 25);
        add(backBtn);

        loginBtn.addActionListener(e -> loginStudent());

        backBtn.addActionListener(e -> {
            dispose();
            new Login();
        });

        setVisible(true);
    }

    void loginStudent() {

        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT full_name FROM students WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String name = rs.getString("full_name");

                dispose();
                new StudentDashboard(name, email);

            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Login Failed");
        }
    }
}
