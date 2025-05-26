import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UniversityTest {
    private University uni;
    private Student st;
    private Teacher th;
    private Course c1, c2;

    @BeforeEach
    void setup() {
        uni = new University();
        st = new Student("100", "Damian Kowalski", "damian", "d");
        th = new Teacher("200", "Jan Nowak", "jan", "j");
        uni.addStudent(st);
        uni.addTeacher(th);
        c1 = new Course("Chemia", "Chemia 2025", th);
        c2 = new Course("Biologia", "Biologia 2025", th);
        uni.addCourse(c1);
        uni.addCourse(c2);
    }

    @Test
    void addStudent() {
        Student student = new Student("1", "Test", "test", "test");
        uni.addStudent(student);
        assertNotNull((uni.getStudentById(student.getId())));
    }

    @Test
    void addTeacher() {
        Teacher teacher = new Teacher("1", "Test", "test", "test");
        uni.addTeacher(teacher);
        assertNotNull((uni.getTeacherByLogin(teacher.getLogin())));
    }

    @Test
    void addCourse() {
        Course course = new Course("course", "desc", new Teacher("test","test","test","test"));
        uni.addCourse(course);
        assertTrue(uni.getAllCourses().contains(course));
    }

    @Test
    void testGetStudentByLogin() {
        assertEquals(st, uni.getStudentByLogin("damian"));
        assertNull(uni.getStudentByLogin("nope"));
    }

    @Test
    void testGetTeacherByLogin() {
        assertEquals(th, uni.getTeacherByLogin("jan"));
        assertNull(uni.getTeacherByLogin("x"));
    }

    @Test
    void testGetCoursesByTeacher() {
        List<Course> list = uni.getCoursesByTeacher(th);
        assertEquals(2, list.size());
        assertTrue(list.containsAll(List.of(c1, c2)));
    }

    @Test
    void testGetCoursesByStudent() {
        assertTrue(uni.getCoursesByStudent(st).isEmpty());
        c1.addStudent(st);
        List<Course> list = uni.getCoursesByStudent(st);
        assertEquals(1, list.size());
        assertTrue(list.contains(c1));
    }

    @Test
    void testRemoveCourseCleansStudents() {
        c1.addStudent(st);
        assertTrue(c1.getEnrolledStudents().contains(st));

        boolean removed = uni.removeCourse(c1);
        assertTrue(removed);
        assertFalse(uni.getAllCourses().contains(c1));
        assertFalse(c1.getEnrolledStudents().contains(st));
    }
}
