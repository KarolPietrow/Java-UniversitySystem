import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MaterialTest {

    @TempDir Path tempDir;

    @Test
    void testLinkMaterial() throws IOException {
        Material m = new Material("Zoom", "https://zoom.us/j/123");
        assertTrue(m.getFilePath().startsWith("https://"));
        assertDoesNotThrow(m::delete);
    }

    @Test
    void testTxtMaterialCopyAndDisplay(@TempDir Path temp) throws Exception {
        Path src = temp.resolve("test.txt");
        Files.writeString(src, "Hello\nWorld");
        Material m = new Material("Test", src.toString());
        Path dest = Path.of(System.getProperty("user.dir"), "materials", "test.txt");
        assertTrue(Files.exists(dest));
        assertDoesNotThrow(m::displayContent);
        assertTrue(m.delete());
        assertFalse(Files.exists(dest));
    }

    @Test
    void testDownload(@TempDir Path temp) throws IOException {
        Path matDir = Path.of(System.getProperty("user.dir"), "materials");
        Files.createDirectories(matDir);
        Path src = matDir.resolve("dl.txt");
        Files.writeString(src, "Data");
        Material m = new Material("DL", src.toString());
        m.download(temp.toString());
        Path downloaded = temp.resolve("dl.txt");
        assertTrue(Files.exists(downloaded));
        assertEquals("Data", Files.readString(downloaded));
    }
}
