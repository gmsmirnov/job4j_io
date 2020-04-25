package ru.gsmirnov;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utilities for reading even numbers from text file.
 */
public class EvenNumberFile {
    /**
     * Reads lines from the specified file. The file must contains only lines with integer numbers. One number per line.
     *
     * @param fileName the specified file.
     * @return the specified file content as a string.
     */
    public static String readFromFile(String fileName) {
        StringBuilder text = new StringBuilder();
        try (FileInputStream in = new FileInputStream(fileName)) {
            int read;
            while ((read = in.read()) != -1) {
                text.append((char) read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    /**
     * Splits the specified text by lines.
     *
     * @param text the specified text.
     * @return A list of lines, received after text splitting.
     */
    public static List<String> splitByLines(String text) {
        return Stream.of(text.split(System.lineSeparator()))
                .collect(Collectors.toList());
    }

    /**
     * Transforms the specified number-lines into numbers and filters only even numbers.
     *
     * @param lines text lines containing numbers.
     * @return a list of filtered even numbers.
     */
    public static List<Integer> filterEvenNumbers(List<String> lines) {
        return lines.stream()
                .map(Integer::parseInt)
                .filter(x -> x % 2 == 0)
                .collect(Collectors.toList());
    }
}
