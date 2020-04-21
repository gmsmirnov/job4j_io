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

    public static void main(String[] args) {
        int size = 10;
        try (FileOutputStream out = new FileOutputStream("result.txt")) {
            int[][] mult = matrix(size);
            for (int i = 0; i < size; i++) {
                out.write(Arrays.toString(mult[i]).getBytes());
                out.write("\n".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[][] matrix(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i <size ; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = (i + 1) * (j + 1);
            }
        }
        return matrix;
    }
}