import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniversityTest {

    @Test
    void addStudent() {
        University university = new University();
        Student student = new Student("1", "Test", "test", "test");
        university.addStudent(student);
        assertNotNull((university.getStudentById(student.getId())));
    }

    @Test
    void addTeacher() {
        University university = new University();
        Teacher teacher = new Teacher("1", "Test", "test", "test");
        university.addTeacher(teacher);
        assertNotNull((university.getTeacherByLogin(teacher.getLogin())));
    }

    @Test
    void addCourse() {
        University university = new University();
        Course course = new Course("course", "desc", new Teacher("test","test","test","test"));
        university.addCourse(course);
        assertTrue(university.getAllCourses().contains(course));
    }

    @Test
    void removeCourse() {
        University university = new University();
        Course course = new Course("course", "desc", new Teacher("test","test","test","test"));
        university.addCourse(course);
        assertTrue(university.getAllCourses().contains(course));
        university.removeCourse(course);
        assertFalse(university.getAllCourses().contains(course));
    }
}