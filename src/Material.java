import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Material {
    private String title;
    private String filePath;

    public Material(String title, String source) throws IOException {
        this.title = title;
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

    public String getTitle() { return title; }
    public String getFilePath() { return filePath; }

    @Override
    public String toString() {
        return title + " (" + filePath + ")";
    }

    public void displayContent() {
        if (!filePath.toLowerCase().endsWith(".txt")) {
            System.out.println("Podgląd dostępny tylko dla plików .txt");
            return;
        }
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
