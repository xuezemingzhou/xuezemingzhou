package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtils {
    public static void saveToFile(String filename, List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < data.size(); i++) {
                writer.write((i+1) + ". " + data.get(i));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
