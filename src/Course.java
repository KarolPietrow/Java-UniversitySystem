import java.util.ArrayList;
import java.util.List;

public class Course {
    private String name;
    private String description;
    private Teacher teacher;
    private List<Student> enrolledStudents = new ArrayList<>();
    private List<File> listOfFiles = new ArrayList<>();

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

    public void removeStudent(Student student) {
        enrolledStudents.remove(student);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public List<File> getListOfFiles() {
        return listOfFiles;
    }
}
