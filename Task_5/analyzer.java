package Task_5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class analyzer {

    public static void analyzeFile(String filePath) throws IOException {
        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                charCount += line.length();
                String[] words = line.trim().split("\\s+");
                if (!line.trim().isEmpty()) {
                    wordCount += words.length;
                }
            }
        }

        System.out.println("\n===== File Analysis Results =====");
        System.out.println("File: " + filePath);
        System.out.println("Total lines  : " + lineCount);
        System.out.println("Total words  : " + wordCount);
        System.out.println("Total characters (excluding line separators): " + charCount);
        System.out.println("================================\n");
    }

    public static void searchWord(String filePath, String searchWord, boolean caseSensitive) throws IOException {
        int occurrences = 0;
        String target = caseSensitive ? searchWord : searchWord.toLowerCase();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNum = 0;
            System.out.println("\n===== Search Results =====");
            while ((line = reader.readLine()) != null) {
                lineNum++;
                String compareLine = caseSensitive ? line : line.toLowerCase();
                String[] words = compareLine.split("\\W+");
                for (String w : words) {
                    if (w.equals(target)) {
                        occurrences++;
                    }
                }
                if (caseSensitive && line.contains(searchWord)) {
                    System.out.println("Line " + lineNum + ": " + line);
                } else if (!caseSensitive && line.toLowerCase().contains(searchWord.toLowerCase())) {
                    System.out.println("Line " + lineNum + ": " + line);
                }
            }
        }

        if (occurrences == 0) {
            System.out.println("The word \"" + searchWord + "\" was not found in the file.");
        } else {
            System.out.println("\nTotal occurrences of \"" + searchWord + "\": " + occurrences);
        }
        System.out.println("=========================\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Text File Analyzer ===");
        System.out.print("Enter the path of the text file: ");
        String filePath = scanner.nextLine().trim();

        if (filePath.isEmpty()) {
            System.out.println("No file path provided. Exiting.");
            scanner.close();
            return;
        }

        try {
            analyzeFile(filePath);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            scanner.close();
            return;
        }

        System.out.print("Do you want to search for a specific word? (yes/no): ");
        String searchChoice = scanner.nextLine().trim().toLowerCase();
        if (searchChoice.equals("yes") || searchChoice.equals("y")) {
            System.out.print("Enter the word to search: ");
            String word = scanner.nextLine().trim();
            if (word.isEmpty()) {
                System.out.println("No word entered. Skipping search.");
            } else {
                System.out.print("Case sensitive? (yes/no): ");
                String caseChoice = scanner.nextLine().trim().toLowerCase();
                boolean caseSensitive = caseChoice.equals("yes") || caseChoice.equals("y");
                try {
                    searchWord(filePath, word, caseSensitive);
                } catch (IOException e) {
                    System.out.println("Error searching file: " + e.getMessage());
                }
            }
        }

        scanner.close();
    }
}