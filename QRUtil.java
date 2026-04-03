import java.time.LocalDate;

public class QRUtil {

    private static final String SECRET_KEY = "ATTEND";

    public static String generateQR(int studentId) {
        String date = LocalDate.now().toString();
        return studentId + "_" + date + "_" + SECRET_KEY;
    }

    public static boolean validateQR(String inputQR, int studentId) {
        String expectedQR = generateQR(studentId);
        return inputQR.equals(expectedQR);
    }
}
