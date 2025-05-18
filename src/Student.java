import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<Course> enrolledCourses = new ArrayList<>();

    public Student(String id, String name, String login, String password) {
        super(id, name, login, password);
    }

    public void addToCourse(Course course) {
        if (!this.enrolledCourses.contains(course)) {
            course.addStudent(this);
            this.enrolledCourses.add(course);
            System.out.println("Pomyślnie zapisano na przedmiot!");
        }
    }

    public void removeFromCourse(Course course) {
        if (this.enrolledCourses.contains(course)) {
            course.removeStudent(this);
            this.enrolledCourses.remove(course);
            System.out.println("Pomyślnie wypisano się z przedmiotu.");
        }
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }
}
