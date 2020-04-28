package ru.gsmirnov;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
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

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void withTemporaryFilesTest() throws IOException {
        File src = this.folder.newFile("source.txt");
        File dst = this.folder.newFile("destination.txt");
        try (PrintWriter out = new PrintWriter(src)) {
            List.of("200 10:56:01",
                    "500 10:57:01",
                    "400 10:58:01",
                    "200 10:59:01",
                    "500 11:01:02",
                    "200 11:02:02").forEach(out::println);
        }
        AnalyzeLog analyzeLog = new AnalyzeLog();
        analyzeLog.unavailable(src.getAbsolutePath(), dst.getAbsolutePath());
        try (BufferedReader in = new BufferedReader(new FileReader(dst))) {
            assertArrayEquals(new String[]{"10:57:01;10:59:01", "11:01:02;11:02:02"}, in.lines().toArray());
        }
    }
}