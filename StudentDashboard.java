import java.awt.*;
import java.sql.*;
import javax.swing.*;
public class StudentDashboard extends JFrame {

    String studentName;
    String studentEmail;

    public StudentDashboard(String name, String email) {

        this.studentName = name;
        this.studentEmail = email;

        setTitle("Student Dashboard");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(30, 144, 255));
        topPanel.setPreferredSize(new Dimension(700, 60));

        // Button Panel (left side)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);

        JButton attendanceBtn = new JButton("Check Attendance");
        JButton profileBtn = new JButton("My Profile");
        JButton logoutBtn = new JButton("Logout");

        buttonPanel.add(attendanceBtn);
        buttonPanel.add(profileBtn);
        buttonPanel.add(logoutBtn);

        // Welcome Label (right side)
        JLabel welcomeLabel = new JLabel("Welcome back, " + studentName + "!");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        topPanel.add(buttonPanel, BorderLayout.WEST);
        topPanel.add(welcomeLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        JLabel attendanceLabel = new JLabel("Attendance: Loading...", SwingConstants.CENTER);
        attendanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(attendanceLabel, BorderLayout.CENTER);
        // Button Actions

        attendanceBtn.addActionListener(e ->
                new AttendanceView(studentEmail)
        );

        profileBtn.addActionListener(e ->
                new StudentProfile(studentEmail)
        );

       logoutBtn.addActionListener(e -> {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (confirm == JOptionPane.YES_OPTION) {
        dispose();
        new Login();
    }
});
        loadAttendancePercentage(attendanceLabel);
        setVisible(true);
    }
    void loadAttendancePercentage(JLabel label) {
    try (Connection con = DBConnection.getConnection()) {

        // Step 1: get student ID
        String getIdSql = "SELECT id FROM students WHERE email = ?";
        PreparedStatement ps1 = con.prepareStatement(getIdSql);
        ps1.setString(1, studentEmail);

        ResultSet rs1 = ps1.executeQuery();

        if (!rs1.next()) {
            label.setText("Attendance: Data not found");
            return;
        }

        int userId = rs1.getInt("id");

        // Step 2: total classes
        String totalSql = "SELECT COUNT(*) FROM attendance WHERE user_id = ?";
        PreparedStatement ps2 = con.prepareStatement(totalSql);
        ps2.setInt(1, userId);

        ResultSet rs2 = ps2.executeQuery();
        rs2.next();
        int total = rs2.getInt(1);

        // Step 3: present count
        String presentSql = "SELECT COUNT(*) FROM attendance WHERE user_id = ? AND status = 'Present'";
        PreparedStatement ps3 = con.prepareStatement(presentSql);
        ps3.setInt(1, userId);

        ResultSet rs3 = ps3.executeQuery();
        rs3.next();
        int present = rs3.getInt(1);

        if (total == 0) {
            label.setText("Attendance: No records yet");
            return;
        }

        int percentage = (present * 100) / total;

        label.setText("Attendance: " + percentage + "% (" + present + " / " + total + ")");
        if (percentage >= 75) {
    label.setForeground(new Color(0, 128, 0)); // green
} else {
    label.setForeground(Color.RED);
}
    } catch (Exception e) {
        e.printStackTrace();
        label.setText("Attendance: Error");
    }
}
}
