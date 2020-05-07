package ru.gsmirnov.archiver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Archives the specified directory
 * 1. You should specify the folder which need to archive, for example: c:\project\job4j\
 * 2. You should specify the extensions of excluded type of files a the key.
 * 3. Archive must repeat directory structure. It must contains all subdirectories.
 * 4. Usage example: java -jar zip.jar -d c:\project\job4j\ -e *.xml -o project.zip
 *
 * java -jar pack.jar - Compiled and packaged program jar.
 *
 * -d - directory - the directory we want to archive.
 * -e - exclude - excluded files extension *.xml
 * -o - output - target archive name.
 *
 * @author Gregory Smirnov (gsmirnov <artress@ngs.ru>)
 * @version 0.2
 * @since 2020-04-29
 */
public class Zip {
    /**
     * The logger.
     */
    private static final Logger LOG = LogManager.getLogger(Zip.class);

    /**
     * Entry point.
     *
     * @param args input args.
     * @throws IOException if IO problems were found.
     */
    public static void main(String[] args) throws IOException {
        LOG.info("Zip started.");
        ArgZip argZip = new ArgZip(args);
        if (!argZip.valid()) {
            throw new IllegalArgumentException("Root folder, file extension or destination archive is not specified. Usage: java -jar zip.jar -d . -e *.xml -o project.zip");
        }
        Zip zip = new Zip();
        LOG.info(String.format("Directory: %s", Paths.get(argZip.directory())));
        LOG.info(String.format("Exclude: %s", Paths.get(argZip.exclude())));
        LOG.info(String.format("Output: %s", Paths.get(argZip.output())));
        zip.packFiles(zip.search(argZip.directory(), argZip.exclude()), Paths.get(argZip.output()));
        LOG.info("Zip finished successful.");
    }

    /**
     * Adds the specified list of files into the specified archive.
     *
     * @param sources the specified list of files.
     * @param target the specified archive.
     */
    public void packFiles(List<Path> sources, Path target) {
        sources.forEach(s -> this.packSingleFile(s, target));
    }

    /**
     * Adds the specified file to the specified archive.
     *
     * @param source the specified source file.
     * @param target the specified target archive file.
     */
    public void packSingleFile(Path source, Path target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target.toFile())))) {
            zip.putNextEntry(new ZipEntry(source.toFile().getPath()));
            try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(source.toFile()))) {
                zip.write(out.readAllBytes());
                LOG.info(String.format("File added to archive: %s", source.toFile().getAbsolutePath()));
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Looks in current directory and in depth for files without the specified extension and puts result into the
     * output list.
     *
     * @param root the specified root directory.
     * @param ext the specified file extension for exclusion.
     * @return a list of files without the specified extension if there are files or empty list.
     * @throws IOException if IO problems were found.
     */
    public List<Path> search(String root, String ext) throws IOException {
        SearchFiles sf = new SearchFiles(p -> !p.toFile().toString().endsWith(ext));
        Files.walkFileTree(Paths.get(root), sf);
        return sf.getResult();
    }
}