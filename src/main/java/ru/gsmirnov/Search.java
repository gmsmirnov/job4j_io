package ru.gsmirnov;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Demonstrates simple search of files with the specified extension in the specified directory.
 *
 * @author Gregory Smirnov (gsmirnov <artress@ngs.ru>)
 * @version 0.1
 * @since 2020-04-29
 */
public class Search {
    private static final Logger LOG = LogManager.getLogger(Search.class);

    /**
     * Entry point.
     *
     * @param args input args.
     * @throws IOException if IO problems were found.
     */
    public static void main(String[] args) throws IOException {
        Path start = Paths.get(".");
//        Files.walkFileTree(start, new PrintFiles());
        Search.search(start, ".java").forEach(System.out::println);
    }

    /**
     * Search logic. Looks in current directory and in depth for files with the specified extension and puts result into the
     * output list.
     *
     * @param root the specified root directory.
     * @param ext the specified file extension.
     * @return a list of files with the specified extension if there are files or empty list.
     * @throws IOException if IO problems were found.
     */
    public static List<String> search(Path root, String ext) throws IOException {
        SearchFiles sf = new SearchFiles(p -> p.toFile().toString().endsWith(ext));
        Files.walkFileTree(root, sf);
        return sf.getResult().stream().map(path -> path.getFileName().toString()).collect(Collectors.toList());
    }
}