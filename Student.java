import java.util.ArrayList;

public class Student {
    int id;
    String name;
    ArrayList<Attendance> records = new ArrayList<>();

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void markAttendance(boolean present) {
        records.add(new Attendance(id,
                java.time.LocalDate.now().toString(),
                present));
    }

    public double getPercentage() {
        int presentDays = 0;
        for (Attendance a : records) {
            if (a.present) presentDays++;
        }
        return records.size() == 0 ? 0 :
                (presentDays * 100.0 / records.size());
    }
}
