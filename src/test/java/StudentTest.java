import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    private Student student;
    private Teacher teacher;
    private Course course;

    @BeforeEach
    void setUp() {
        student = new Student("1", "Alicja", "alicja", "pass");
        teacher = new Teacher("2", "Marcin", "marcin", "secret");
        course = new Course("Matematyka dyskretna", "Matematyka dyskretna 2025", teacher);
    }

    @Test
    void testLoginSuccess() {
        assertTrue(student.login("pass"));
    }

    @Test
    void testLoginFailure() {
        assertFalse(student.login("wrong"));
    }

    @Test
    void testEnrollAndRemoveCourse() {
        assertTrue(course.getEnrolledStudents().isEmpty());

        course.addStudent(student);
        assertTrue(course.getEnrolledStudents().contains(student));

        course.addStudent(student);
        assertEquals(1, course.getEnrolledStudents().size());

        course.removeStudent(student);
        assertFalse(course.getEnrolledStudents().contains(student));
    }

}
