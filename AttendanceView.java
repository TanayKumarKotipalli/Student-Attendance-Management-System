import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AttendanceView extends JFrame {

    public AttendanceView(String studentEmail) {

        setTitle("My Attendance");
        setSize(500, 300);
        setLocationRelativeTo(null);

        String[] columns = {"Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        try (Connection con = DBConnection.getConnection()) {

            // Step 1: get user_id from students table
            String userSql = "SELECT id FROM students WHERE email = ?";
            PreparedStatement ps1 = con.prepareStatement(userSql);
            ps1.setString(1, studentEmail);
            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) {
                JOptionPane.showMessageDialog(this, "Student not found");
                return;
            }

            int userId = rs1.getInt("id");

            // Step 2: fetch attendance using user_id
            String attSql = "SELECT date, status FROM attendance WHERE user_id = ?";
            PreparedStatement ps2 = con.prepareStatement(attSql);
            ps2.setInt(1, userId);
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                model.addRow(new Object[]{
                        rs2.getDate("date"),
                        rs2.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load attendance");
        }

        add(new JScrollPane(table));
        setVisible(true);
    }
}
