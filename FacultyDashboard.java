import java.awt.*;
import javax.swing.*;

public class FacultyDashboard extends JFrame {

    public FacultyDashboard(String empId) {

        setTitle("Faculty Dashboard");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Label
        JLabel welcome = new JLabel("Welcome Faculty ID: " + empId,
                SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 16));
        add(welcome, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());

        JButton markBtn = new JButton("Mark Attendance");
        JButton logoutBtn = new JButton("Logout");

        centerPanel.add(markBtn);
        centerPanel.add(logoutBtn);

        add(centerPanel, BorderLayout.CENTER);

        // Button Actions

        markBtn.addActionListener(e ->
                new MarkAttendance()
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

        setVisible(true);
    }
}
