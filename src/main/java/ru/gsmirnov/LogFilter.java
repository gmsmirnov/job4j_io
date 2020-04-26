package ru.gsmirnov;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Log file filter utility.
 */
public class LogFilter {
    /**
     * Filters file lines by the specified condition.
     *
     * @param filename  the specified file name.
     * @param predicate the specified condition.
     * @return the list of stings corresponding to the specified filter.
     */
    public static List<String> fileFilter(String filename, Predicate<String> predicate) {
        List<String> result = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            result = br.lines()
                    .filter(predicate)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Creates a filter condition.
     *
     * @param condition filter param.
     * @return created filter condition.
     */
    public static Predicate<String> filter(String condition) {
        return l -> {
            boolean result = false;
            String[] words = l.split(" ");
            if (words[words.length - 2].equals(condition)) { // -2 == last - 1
                result = true;
            }
            return result;
        };
    }

    /**
     * Saves the list of strings into the specified file.
     *
     * @param logName the specified output file name.
     * @param lines the specified list of strings.
     */
    public static void save(String logName, List<String> lines) {
        try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(logName)))) {
            lines.forEach(out::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Entry point. Demonstrates the work of log file filter. Saves the result into the file.
     *
     * @param args input args.
     */
    public static void main(String[] args) {
        List<String> filtered = LogFilter.fileFilter("log.txt", LogFilter.filter("404"));
        LogFilter.save("filtered_log.txt", filtered);
    }
}
