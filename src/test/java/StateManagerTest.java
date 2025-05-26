import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class StateManagerTest {
    private static final String TEST_FILE = "data.json";

    @BeforeEach
    void cleanUp() throws Exception {
        Files.deleteIfExists(Path.of(TEST_FILE));
    }

    @Test
    void testSaveAndLoad() {
        University uni = new University();
        Student s = new Student("9", "Ivy", "ivy", "i");
        uni.addStudent(s);
        StateManager.save(uni);

        assertTrue(new File(TEST_FILE).exists());
        University loaded = StateManager.load();
        assertNotNull(loaded.getStudentByLogin("ivy"));
        assertEquals("Ivy", loaded.getStudentByLogin("ivy").getName());
    }
}
