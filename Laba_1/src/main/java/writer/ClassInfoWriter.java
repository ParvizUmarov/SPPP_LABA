package writer;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class ClassInfoWriter {

    public static void main(String[] args) {
        String sourceDirPath = "src";
        String outputFilePath = "class_info.txt";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));

            Files.walk(Paths.get(sourceDirPath))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        try {
                            List<String> fileContent = Files.readAllLines(path);
                            writer.write("=== File: " + path.getFileName() + " ===\n");
                            for (String line : fileContent) {
                                writer.write(line + "\n");
                            }
                            writer.write("\n\n");
                        } catch (IOException e) {
                            System.err.println("Error reading file: " + path);
                        }
                    });

            writer.close();
            System.out.println("All Java class files have been aggregated into " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
        }
    }
}