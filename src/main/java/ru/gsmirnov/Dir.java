package ru.gsmirnov;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Objects;

/**
 * @author Gregory Smirnov (gsmirnov <artress@ngs.ru>)
 * @version 0.1
 * @since 2020-04-29
 */
public class Dir {
    private static final Logger LOG = LogManager.getLogger(Dir.class);

    /**
     * Scans project directory for subdirectories and files.
     *
     * @param args input args.
     */
    public static void main(String[] args) {
        File file = new File("D:\\Work\\Java\\projects");
        if (!file.exists()) {
            throw new IllegalStateException(String.format("Not exists: %s", file.getAbsolutePath()));
        }
        if (!file.isDirectory()) {
            throw new IllegalStateException(String.format("Not directory: %s", file.getAbsolutePath()));
        }
        System.out.println(String.format("Size: %s", file.getTotalSpace()));
        for (File subfile : Objects.requireNonNull(file.listFiles())) {
            System.out.println(String.format("Name: %s, size: %fkb", subfile.getName(), (double) subfile.length() / 1024));
        }
    }
}