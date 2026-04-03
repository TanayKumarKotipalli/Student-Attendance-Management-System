import java.awt.*;
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

        // Center Area
        JLabel centerText = new JLabel(
                "<html><h2>Select an option from above</h2></html>",
                SwingConstants.CENTER
        );
        add(centerText, BorderLayout.CENTER);

        // Button Actions

        attendanceBtn.addActionListener(e ->
                new AttendanceView(studentEmail)
        );

        profileBtn.addActionListener(e ->
                new StudentProfile(studentEmail)
        );

        logoutBtn.addActionListener(e -> {
            dispose();
            new Login();
        });

        setVisible(true);
    }
}
