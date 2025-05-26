import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacher = new Teacher("10", "Marcin Kwiatkowski", "marcin", "pwd");
    }

    @Test
    void testLoginSuccess() {
        assertTrue(teacher.login("pwd"));
    }

    @Test
    void testLoginFailure() {
        assertFalse(teacher.login("bad"));
    }
}
