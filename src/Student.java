import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<Course> enrolledCourses = new ArrayList<>();

   public Student(String id, String name, String login, String password) {
       super(id, name, login, password);
   }

}
