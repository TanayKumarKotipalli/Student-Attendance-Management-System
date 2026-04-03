import java.sql.*;
import javax.swing.*;

public class FacultyRegister extends JFrame {

    JTextField empField, nameField;
    JPasswordField passField, confirmPassField;

    public FacultyRegister() {

        setTitle("Faculty Registration");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(null);

        addLabel("Employee ID:", 30);
        empField = addField(30);

        addLabel("Full Name:", 70);
        nameField = addField(70);

        addLabel("Password:", 110);
        passField = addPassword(110);

        addLabel("Confirm Password:", 150);
        confirmPassField = addPassword(150);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(130, 200, 120, 30);
        add(registerBtn);
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(130, 240, 120, 20); 
        add(backBtn);
        registerBtn.addActionListener(e -> registerFaculty());
        backBtn.addActionListener(e -> {
            dispose();      
            new Login();    
        });
        setVisible(true);
    }

    void registerFaculty() {

        String empId = empField.getText().trim();
        String name = nameField.getText().trim();
        String pass = new String(passField.getPassword());
        String confirm = new String(confirmPassField.getPassword());

        if (!empId.matches("^1\\d{3}$")) {
            JOptionPane.showMessageDialog(this,
                    "Employee ID must start with 1 and be 4 digits.");
            return;
        }

        if (!pass.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            String checkSql = "SELECT * FROM faculty WHERE employee_id=?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setString(1, empId);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Employee ID already exists.");
                return;
            }

            String insertSql =
                    "INSERT INTO faculty(employee_id, full_name, password) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insertSql);

            ps.setString(1, empId);
            ps.setString(2, name);
            ps.setString(3, pass);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Faculty Registered Successfully");
            dispose();
            new FacultyLogin();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Registration failed.");
        }
    }

    void addLabel(String text, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(40, y, 130, 25);
        add(lbl);
    }

    JTextField addField(int y) {
        JTextField tf = new JTextField();
        tf.setBounds(180, y, 150, 25);
        add(tf);
        return tf;
    }

    JPasswordField addPassword(int y) {
        JPasswordField pf = new JPasswordField();
        pf.setBounds(180, y, 150, 25);
        add(pf);
        return pf;
    }
}
