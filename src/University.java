import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class University {
    private List<Student> students = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public List<Course> getAllCourses() {
        return courses;
    }

    public List<Course> getCoursesByTeacher(Teacher teacher) {
        return courses.stream()
                .filter(c -> c.getTeacher().equals(teacher))
                .toList();
    }

    public Student getStudentByLogin(String login) {
        for (Student student : students) {
            if (Objects.equals(student.login, login)) {
                return student;
            }
        }
        return null;
    }

    public Teacher getTeacherByLogin(String login) {
        for (Teacher teacher : teachers) {
            if (Objects.equals(teacher.login, login)) {
                return teacher;
            }
        }
        return null;
    }
}
