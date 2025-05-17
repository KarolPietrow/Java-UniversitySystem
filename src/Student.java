import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<Course> enrolledCourses = new ArrayList<>();

   public Student(String id, String name, String login, String password) {
       super(id, name, login, password);
   }

   public void enroll(Course course) {
       if (!this.enrolledCourses.contains(course)) {
           course.addStudent(this);
           this.enrolledCourses.add(course);
           System.out.println("Pomyślnie zapisano na przedmiot!");
       }
   }

   public List<Course> getEnrolledCourses() {
       return enrolledCourses;
   }
}
