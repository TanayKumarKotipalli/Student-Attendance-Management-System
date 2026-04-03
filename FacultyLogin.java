import java.sql.*;
import javax.swing.*;
public class FacultyLogin extends JFrame {

    JTextField empField;
    JPasswordField passField;

    public FacultyLogin() {

        setTitle("Faculty Login");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel empLbl = new JLabel("Employee ID:");
        empLbl.setBounds(40, 40, 100, 25);
        add(empLbl);

        empField = new JTextField();
        empField.setBounds(150, 40, 120, 25);
        add(empField);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setBounds(40, 80, 100, 25);
        add(passLbl);

        passField = new JPasswordField();
        passField.setBounds(150, 80, 120, 25);
        add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(110, 130, 100, 30);
        add(loginBtn);
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(110, 170, 100, 30);
        add(backBtn);

        loginBtn.addActionListener(e -> loginFaculty());
        backBtn.addActionListener(e -> {
            dispose();
            new Login();
        });
        setVisible(true);
    }

    void loginFaculty() {

        String empId = empField.getText().trim();
        String password = new String(passField.getPassword());

        // Validation rule
        if (!empId.matches("^1\\d{3}$")) {
            JOptionPane.showMessageDialog(this,
                    "Invalid Employee ID.\nMust start with 1 and be 4 digits.");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM faculty WHERE employee_id=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, empId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                new FacultyDashboard(empId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
