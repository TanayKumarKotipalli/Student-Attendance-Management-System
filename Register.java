import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.swing.*;

public class Register extends JFrame {

    JTextField nameField, rollField, branchField, sectionField, emailField;
    JPasswordField passField, rePassField;

    public Register() {
        setTitle("Student Registration");
        setSize(420, 430);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addLabel("Full Name", 30);
        nameField = addField(30);

        addLabel("Roll No", 70);
        rollField = addField(70);

        addLabel("Branch", 110);
        branchField = addField(110);

        addLabel("Section", 150);
        sectionField = addField(150);

        addLabel("College Email", 190);
        emailField = addField(190);

        addLabel("Password", 230);
        passField = addPassword(230);

        addLabel("Re-enter Password", 270);
        rePassField = addPassword(270);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(150, 320, 120, 30);
        add(registerBtn);
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(150, 360, 120, 30); // below Register button
        add(backBtn);
        registerBtn.addActionListener(e -> registerStudent());
        backBtn.addActionListener(e -> {
            dispose();      // close register window
            new Login();    // go back to main screen
        });
        setVisible(true);
    }

    void registerStudent() {

        String fullName = nameField.getText().trim();
        String rollNo = rollField.getText().trim();
        String branch = branchField.getText().trim();
        String section = sectionField.getText().trim();
        String email = emailField.getText().trim();
        String pass = new String(passField.getPassword());
        String rePass = new String(rePassField.getPassword());

        if (fullName.isEmpty() || rollNo.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Required fields are missing");
            return;
        }

        if (!email.endsWith("@adityauniversity.in")) {
            JOptionPane.showMessageDialog(this, "Use college email only");
            return;
        }

        if (!pass.equals(rePass)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO students " +
                         "(full_name, roll_no, branch, section, email, password) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fullName);
            ps.setString(2, rollNo);
            ps.setString(3, branch);
            ps.setString(4, section);
            ps.setString(5, email);
            ps.setString(6, pass);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registration Successful");
            dispose();

        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "Email or Roll Number already exists");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "ERROR: " + ex.getMessage());
        }
    }

    void addLabel(String text, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setBounds(40, y, 140, 25);
        add(lbl);
    }

    JTextField addField(int y) {
        JTextField tf = new JTextField();
        tf.setBounds(190, y, 160, 25);
        add(tf);
        return tf;
    }

    JPasswordField addPassword(int y) {
        JPasswordField pf = new JPasswordField();
        pf.setBounds(190, y, 160, 25);
        add(pf);
        return pf;
    }
}
