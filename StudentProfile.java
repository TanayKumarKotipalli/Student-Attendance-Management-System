import java.sql.*;
import javax.swing.*;

public class StudentProfile extends JFrame {

    JTextField nameField, rollField, branchField, sectionField, emailField;
    JPasswordField passwordField;

    String studentEmail;

    public StudentProfile(String email) {
        this.studentEmail = email;

        setTitle("My Profile");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(null);

        addLabel("Full Name", 30);      nameField = addField(30);
        addLabel("Roll No", 70);        rollField = addField(70);
        addLabel("Branch", 110);        branchField = addField(110);
        addLabel("Section", 150);       sectionField = addField(150);
        addLabel("Email", 190);         emailField = addField(190);
        addLabel("Password", 230);      passwordField = addPassword(230);

        rollField.setEditable(false);
        emailField.setEditable(false);

        JButton updateBtn = new JButton("Update");
        updateBtn.setBounds(140, 270, 100, 30);
        add(updateBtn);

        updateBtn.addActionListener(e -> updateProfile());

        loadProfile();
        setVisible(true);
    }

    // 🔹 READ (load data)
    void loadProfile() {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM students WHERE email = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, studentEmail);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nameField.setText(rs.getString("full_name"));
                rollField.setText(rs.getString("roll_no"));
                branchField.setText(rs.getString("branch"));
                sectionField.setText(rs.getString("section"));
                emailField.setText(rs.getString("email"));
                passwordField.setText(rs.getString("password"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load profile");
        }
    }

    // 🔹 UPDATE
    void updateProfile() {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE students SET full_name=?, branch=?, section=?, password=? WHERE email=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nameField.getText().trim());
            ps.setString(2, branchField.getText().trim());
            ps.setString(3, sectionField.getText().trim());
            ps.setString(4, new String(passwordField.getPassword()));
            ps.setString(5, studentEmail);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Profile Updated Successfully");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Update failed");
        }
    }

    // 🔹 UI helpers
    void addLabel(String text, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(40, y, 100, 25);
        add(lbl);
    }

    JTextField addField(int y) {
        JTextField tf = new JTextField();
        tf.setBounds(150, y, 180, 25);
        add(tf);
        return tf;
    }

    JPasswordField addPassword(int y) {
        JPasswordField pf = new JPasswordField();
        pf.setBounds(150, y, 180, 25);
        add(pf);
        return pf;
    }
}
