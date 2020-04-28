package ru.gsmirnov;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Gregory Smirnov (gsmirnov <artress@ngs.ru>)
 * @version 0.1
 * @since 2020-04-27
 */
public class AnalyzeLog {
    /**
     * If true - server is available, false either.
     */
    private boolean state = true;

    /**
     * Wraps all logic. Gets source server log file and writes servers' unavailable time intervals.
     *
     * @param source source log file.
     * @param target destination output file for servers' unavailable time intervals.
     */
    public void unavailable(String source, String target) {
        this.savePeriod(this.analyze(this.readLog(source)), target);
    }

    /**
     * Reads the specified log-file. Returns the list of not empty log lines.
     *
     * @param source the specified log file.
     * @return the list of not empty log lines.
     */
    public List<String> readLog(String source) {
        List<String> lines = new ArrayList<String>();
        try (BufferedReader in = new BufferedReader(new FileReader(source))) {
            lines = in.lines().filter(this.filerEmptyLines()).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * The predicate, used to filter empty lines.
     *
     * @return the predicate, which filters empty lines.
     */
    public Predicate<String> filerEmptyLines() {
        return l -> !l.isEmpty() && !l.equals(System.lineSeparator());
    }

    /**
     * Analyser, finds server unavailable time intervals.
     *
     * @param lines log file lines.
     * @return the list of strings with server unavailable time intervals.
     */
    public List<String> analyze(List<String> lines) {
        List<String> result = new ArrayList<String>();
        String available = "^[2-3]\\d{2}$"; // 200, 201, 202, 300...
        String unavailable = "^[4-5]\\d{2}$"; // 403, 404, 500...
        String start = "";
        String end = "";
        for (String line : lines) {
            String[] split = line.split(" ");
            if (Pattern.matches(unavailable, split[0]) && this.state) {
                this.state = false;
                start = split[1];
            } else if (Pattern.matches(available, split[0]) && !this.state) {
                this.state = true;
                end = split[1];
                result.add(String.format("%s;%s", start, end));
            }
        }
        return result;
    }

    /**
     * Saves server unavailable time intervals into the specified file.
     *
     * @param lines lines with server unavailable time intervals.
     * @param destination destination file.
     */
    public void savePeriod(List<String> lines, String destination) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(destination))) {
            lines.forEach(out::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}