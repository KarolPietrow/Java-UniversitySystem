import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Material {
    private String title;
    private String filePath;

    public Material(String title, String source) throws IOException {
        this.title = title;
        if (source.startsWith("http://") || source.startsWith("https://")) { // Link
            this.filePath = source;
        } else { // Plik
            File materialsDir = new File("materials");
            if (!materialsDir.exists()) {
                materialsDir.mkdirs();
            }
            File src = new File(source);
            if (!src.exists()) {
                throw new IOException("Plik źródłowy nie istnieje: " + source);
            }
            File dest = new File(materialsDir, src.getName());
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            this.filePath = dest.getPath();
        }
    }

    public String getTitle() { return title; }
    public String getFilePath() { return filePath; }

    @Override
    public String toString() {
        return title + " (" + filePath + ")";
    }

    public void displayContent() {
        if (filePath.startsWith("http://") || filePath.startsWith("https://")) {
            openLink();
        } else if (filePath.toLowerCase().endsWith(".txt")) {
            openTxt();
        } else {
            System.out.println("Podgląd dostępny tylko dla plików .txt oraz linków");
        }
    }

    private void openLink() {
        try {
            Desktop.getDesktop().browse(new URI(filePath));
            System.out.println("Otwieranie linku w przeglądarce: " + filePath);
        } catch (IOException | URISyntaxException e) {
            System.out.println("Błąd otwierania linku: " + e.getMessage());
        }
    }

    public void openTxt() {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Plik nie istnieje: " + filePath);
            return;
        }
        System.out.println("----- ZAWARTOŚĆ " + title + " -----");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Błąd odczytu pliku: " + e.getMessage());
        }
        System.out.println("----- KONIEC PODGLĄDU -----");
    }

    public void download(String targetDir) {
        if (filePath.startsWith("http://") || filePath.startsWith("https://")) {
            System.out.println("Linków nie można pobrać");
            return;
        } else {
            File src = new File(filePath);
            if (!src.exists()) {
                System.out.println("Plik źródłowy nie istnieje: " + filePath);
                return;
            }
            File dir = new File(targetDir);
            if (!dir.exists() && !dir.mkdirs()) {
                System.out.println("Nie udało się utworzyć katalogu docelowego: " + targetDir);
                return;
            }
            File dest = new File(dir, src.getName());
            try {
                Files.copy(Path.of(src.getPath()), Path.of(dest.getPath()), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Pobrano plik do: " + dest.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Błąd pobierania pliku: " + e.getMessage());
            }
        }
    }

    public boolean delete() {
        if (filePath.startsWith("http://") || filePath.startsWith("https://")) {
            return true;
        } else {
            File f = new File(filePath);
            boolean fileDeleted = true;
            if (f.exists()) {
                fileDeleted = f.delete();
            }
            return fileDeleted;
        }
    }
}
