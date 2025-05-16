import java.util.ArrayList;
import java.util.List;

public class Course {
    private String name;
    private String description;
    private Teacher teacher;
    private List<Student> enrolledStudents = new ArrayList<>();

    List<File> listOfFiles = new ArrayList<>();

    public Course(String name, String description, Teacher teacher) {
        this.name = name;
        this.description = description;
        this.teacher = teacher;
    }

    public void addStudent(Student student) {
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    public String getName() {
        return name;
    }
    public Teacher getTeacher() {
        return teacher;
    }
}
