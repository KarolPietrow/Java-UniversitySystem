import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    private Teacher teacher;
    private Student s1;
    private Course course;

    @BeforeEach
    void init() {
        teacher = new Teacher("5", "Marcin Kwiatkowski", "marcin", "x");
        course = new Course("Matematyka", "Matematyka dyskertna 2025", teacher);
        s1 = new Student("11", "Ewa Mazurek", "ewa", "e");
    }

    @Test
    void testAddAndRemoveStudent() {
        assertTrue(course.getEnrolledStudents().isEmpty());

        course.addStudent(s1);
        assertTrue(course.getEnrolledStudents().contains(s1));

        course.addStudent(s1);
        assertEquals(1, course.getEnrolledStudents().size());

        course.removeStudent(s1);
        assertFalse(course.getEnrolledStudents().contains(s1));
    }
}
