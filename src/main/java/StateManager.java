import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class StateManager {
    private static final String DATA_FILE = "data.json";
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static University load() {
        File f = new File(DATA_FILE);
        if (!f.exists()) {
            return new University();
        }
        try (Reader reader = new FileReader(f)) {
            return gson.fromJson(reader, University.class);
        } catch (IOException e) {
            System.err.println("Błąd podczas ładowania danych: " + e.getMessage());
            return new University();
        }
    }

    public static void save(University uni) {
        try (Writer writer = new FileWriter(DATA_FILE)) {
            gson.toJson(uni, writer);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu danych: " + e.getMessage());
        }
    }
}
