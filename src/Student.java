import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<Course> enrolledCourses = new ArrayList<>();

   public Student(String id, String name, String login, String password) {
       super(id, name, login, password);
   }

   public void enroll(Course course) {

   }

   public void viewEnrolledCourses() {
       if (enrolledCourses.isEmpty()) {
           System.out.println("Nie jesteś zapisany/a na żaden przedmiot.");
       }
   }

}
