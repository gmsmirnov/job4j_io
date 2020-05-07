package ru.gsmirnov.archiver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Simple file visitor which applies the specified predicate to each visited file.
 *
 * @author Gregory Smirnov (gsmirnov <artress@ngs.ru>)
 * @version 0.2
 * @since 2020-04-29
 */
public class SearchFiles extends SimpleFileVisitor<Path> {
    private static final Logger LOG = LogManager.getLogger(SearchFiles.class);

    /**
     * The predicate which need to apply to every file.
     */
    private final Predicate<Path> predicate;

    /**
     * The result files list.
     */
    private final List<Path> result = new ArrayList<Path>();

    /**
     * The constructor. Creates a simple file visitor with the specified predicate.
     *
     * @param predicate the specified predicate.
     */
    public SearchFiles(Predicate<Path> predicate) {
        this.predicate = predicate;
    }

    /**
     * Applies when visits file.
     *
     * @param file next file to visit.
     * @param attrs specific attributes (not used).
     * @return continues files visiting.
     * @throws IOException when IO problems were found.
     */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (this.predicate.test(file)) {
            this.result.add(file);
        }
        return FileVisitResult.CONTINUE;
    }

    /**
     * Gets result files list.
     *
     * @return the result files list.
     */
    public List<Path> getResult() {
        return this.result;
    }
}