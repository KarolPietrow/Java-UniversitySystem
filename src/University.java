import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class University {
    private List<Student> students = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    public List<Course> getAllCourses() {
        return courses;
    }

    public List<Course> getCoursesByTeacher(Teacher teacher) {
        return courses.stream()
                .filter(c -> c.getTeacher().equals(teacher))
                .toList();
    }

    public Student getStudentById(String id) {
        for (Student student : students) {
            if (Objects.equals(student.id, id)) {
                return student;
            }
        }
        return null;
    }

    public Teacher getTeacherById(String id) {
        for (Teacher teacher : teachers) {
            if (Objects.equals(teacher.id, id)) {
                return teacher;
            }
        }
        return null;
    }
}
