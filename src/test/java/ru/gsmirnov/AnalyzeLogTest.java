package ru.gsmirnov;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class AnalyzeLogTest {
    @Test
    public void readSourceFileTest() {
        AnalyzeLog analyzeLog = new AnalyzeLog();
        List<String> result = analyzeLog.readLog("./source.txt");
        assertArrayEquals(new String[]{
                        "200 10:56:01",
                        "500 10:57:01",
                        "400 10:58:01",
                        "200 10:59:01",
                        "500 11:01:02",
                        "200 11:02:02"},
                result.toArray());
    }

    @Test
    public void emptyLineFilterTest() {
        List<String> input = List.of("200 10:56:01", "", "500 10:57:01", System.lineSeparator(), "400 10:58:01");
        Object[] result = input.stream().filter(new AnalyzeLog().filerEmptyLines()).toArray();
        assertArrayEquals(new String[]{"200 10:56:01", "500 10:57:01", "400 10:58:01"}, result);
    }

    @Test
    public void whenServerWasAvailableThenUnavailable() {
        AnalyzeLog analyzeLog = new AnalyzeLog();
        List<String> strings = List.of(
                "200 10:56:01",
                "500 10:57:01",
                "400 10:58:01",
                "200 10:59:01",
                "500 11:01:02",
                "200 11:02:02");
        List<String> result = analyzeLog.analyze(strings);
        assertArrayEquals(new String[]{"10:57:01;10:59:01", "11:01:02;11:02:02"}, result.toArray());
    }

    @Test
    public void whenServerWasUnavailableThenAvailable() {
        AnalyzeLog analyzeLog = new AnalyzeLog();
        List<String> strings = List.of(
                "500 10:57:01",
                "400 10:58:01",
                "200 10:59:01",
                "500 11:01:02",
                "200 11:02:02");
        List<String> result = analyzeLog.analyze(strings);
        result.forEach(System.out::println);
        assertArrayEquals(new String[]{"10:57:01;10:59:01", "11:01:02;11:02:02"}, result.toArray());
    }

    @Test
    public void fullProcessTest() {
        AnalyzeLog analyzeLog = new AnalyzeLog();
        analyzeLog.unavailable("./source.txt", "./target.csv");
        try (BufferedReader in = new BufferedReader(new FileReader("./target.csv"))) {
            assertArrayEquals(new String[]{"10:57:01;10:59:01", "11:01:02;11:02:02"}, in.lines().toArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}