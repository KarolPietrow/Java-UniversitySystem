import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class University {
    private List<Student> students = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    public void addStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    public void addTeacher(Teacher teacher) {
        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
        }
    }

    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public boolean removeCourse(Course course) {
        for (Student s : new ArrayList<>(course.getEnrolledStudents())) {
            course.removeStudent(s);
        }
        return courses.remove(course);
    }

    public List<Course> getAllCourses() {
        return courses;
    }

    public List<Course> getCoursesByTeacher(Teacher teacher) {
        return courses.stream()
                .filter(c -> c.getTeacher().equals(teacher))
                .toList();
    }

    public List<Course> getCoursesByStudent(Student student) {
        return courses.stream()
                .filter(c -> c.getEnrolledStudents().contains(student))
                .toList();
    }

    public Student getStudentById(String id) {
        for (Student student : students) {
            if (Objects.equals(student.getId(), id)) {
                return student;
            }
        }
        return null;
    }

    public Student getStudentByLogin(String login) {
        for (Student student : students) {
            if (Objects.equals(student.getLogin(), login)) {
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
