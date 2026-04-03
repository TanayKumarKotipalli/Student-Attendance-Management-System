import javax.swing.*;

public class Dashboard extends JFrame {

    public Dashboard() {
        setTitle("Dashboard");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton qrBtn = new JButton("Mark Attendance (QR)");
        qrBtn.setBounds(100, 120, 200, 40);

        setLayout(null);
        add(qrBtn);

        qrBtn.addActionListener(e -> qrAttendance());

        setVisible(true);
    }

    void qrAttendance() {
        int studentId = Integer.parseInt(
                JOptionPane.showInputDialog("Enter Student ID"));

        String qrInput = JOptionPane.showInputDialog(
                "Enter QR Code");

        if (QRUtil.validateQR(qrInput, studentId)) {
            JOptionPane.showMessageDialog(this,
                    "Attendance Marked Present");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid QR Code");
        }
    }
}
