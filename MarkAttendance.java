import java.sql.*;
import javax.swing.*;

public class MarkAttendance extends JFrame {

    JTextField rollField;
    JLabel nameLabel;
    JComboBox<String> statusBox;
    JButton markBtn;

    int foundUserId = -1;

    public MarkAttendance() {

        setTitle("Faculty - Mark Attendance");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel rollLbl = new JLabel("Roll Number:");
        rollLbl.setBounds(40, 30, 100, 25);
        add(rollLbl);

        rollField = new JTextField();
        rollField.setBounds(150, 30, 180, 25);
        add(rollField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(150, 70, 100, 25);
        add(searchBtn);

        nameLabel = new JLabel("Student: ");
        nameLabel.setBounds(40, 110, 300, 25);
        add(nameLabel);

        statusBox = new JComboBox<>(new String[]{"Present", "Absent"});
        statusBox.setBounds(150, 150, 120, 25);
        add(statusBox);

        markBtn = new JButton("Mark Attendance");
        markBtn.setBounds(120, 200, 150, 30);
        markBtn.setEnabled(false); // disabled initially
        add(markBtn);

        searchBtn.addActionListener(e -> searchStudent());
        markBtn.addActionListener(e -> markAttendance());

        setVisible(true);
    }

    void searchStudent() {

        // ✅ Validate FIRST
        if (rollField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Roll Number!");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT id, full_name FROM students WHERE roll_no = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, rollField.getText().trim());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                foundUserId = rs.getInt("id");
                nameLabel.setText("Student: " + rs.getString("full_name"));
                markBtn.setEnabled(true); // enable button
            } else {
                foundUserId = -1;
                nameLabel.setText("Student not found");
                markBtn.setEnabled(false); // disable button
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: Unable To Fetch The Student!");
        }
    }

    void markAttendance() {

        if (foundUserId == -1) {
            JOptionPane.showMessageDialog(this, "Search student first!");
            return;
        }

        // ✅ Validate status BEFORE insert
        if (statusBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Select status!");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            // 🔥 Check duplicate
            String checkSql = "SELECT * FROM attendance WHERE user_id = ? AND date = CURDATE()";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, foundUserId);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Attendance already marked for today!");
                return;
            }

            // 🔥 Insert
            String sql = "INSERT INTO attendance(user_id, date, status) VALUES (?, CURDATE(), ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, foundUserId);
            ps.setString(2, statusBox.getSelectedItem().toString());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Attendance Marked Successfully");

            // ✅ RESET FORM
            rollField.setText("");
            nameLabel.setText("Student: ");
            foundUserId = -1;
            markBtn.setEnabled(false);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: Please Try Again Later!");
        }
    }
}