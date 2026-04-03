import java.sql.*;
import javax.swing.*;

public class MarkAttendance extends JFrame {

    JTextField rollField;
    JLabel nameLabel;
    JComboBox<String> statusBox;

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

        JButton markBtn = new JButton("Mark Attendance");
        markBtn.setBounds(120, 200, 150, 30);
        add(markBtn);

        searchBtn.addActionListener(e -> searchStudent());
        markBtn.addActionListener(e -> markAttendance());

        setVisible(true);
    }

    int foundUserId = -1;

    void searchStudent() {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT id, full_name FROM students WHERE roll_no = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, rollField.getText().trim());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                foundUserId = rs.getInt("id");
                nameLabel.setText("Student: " + rs.getString("full_name"));
            } else {
                foundUserId = -1;
                nameLabel.setText("Student not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void markAttendance() {
        if (foundUserId == -1) {
            JOptionPane.showMessageDialog(this, "Search student first!");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO attendance(user_id, date, status) VALUES (?, CURDATE(), ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, foundUserId);
            ps.setString(2, statusBox.getSelectedItem().toString());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Attendance Marked Successfully");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to mark attendance");
        }
    }
}
