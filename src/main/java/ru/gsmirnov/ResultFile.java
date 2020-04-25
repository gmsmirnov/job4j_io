package ru.gsmirnov;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Gregory Smirnov (gsmirnov <artress@ngs.ru>)
 * @version 0.1
 * @since 2020-04-22
 */
public class ResultFile {
    private static final Logger LOG = LogManager.getLogger(ResultFile.class);

    /**
     * Entry point. Creates a matrix and puts it into the file.
     *
     * @param args the specified args.
     */
    public static void main(String[] args) {
        int size = 10;
        try (FileOutputStream out = new FileOutputStream("result.txt")) {
            int[][] mult = matrix(size);
            for (int i = 0; i < size; i++) {
                out.write(Arrays.toString(mult[i]).getBytes());
                out.write(System.lineSeparator().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates multiplying matrix of the specified size.
     *
     * @param size the specified matrix's size.
     * @return generated matrix of the specified size.
     */
    public static int[][] matrix(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = (i + 1) * (j + 1);
            }
        }
        return matrix;
    }
}
