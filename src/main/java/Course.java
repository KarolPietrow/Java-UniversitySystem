import java.util.ArrayList;
import java.util.List;

public class Course {
    private String name;
    private String description;
    private Teacher teacher;
    private List<Student> enrolledStudents = new ArrayList<>();
    private List<Material> materials = new ArrayList<>();

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

    public void addMaterial(Material material) {
        materials.add(material);
    }

    public List<Material> getMaterials() {
        return materials;
    }
}
