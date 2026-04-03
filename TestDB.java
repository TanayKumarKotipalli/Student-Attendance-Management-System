public class TestDB {
    public static void main(String[] args) {
        if (DBConnection.getConnection() != null) {
            System.out.println("DB CONNECTED");
        } else {
            System.out.println("DB FAILED");
        }
    }
}
