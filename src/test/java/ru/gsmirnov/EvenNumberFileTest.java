package ru.gsmirnov;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Read even numbers from file test.
 */
public class EvenNumberFileTest {
    @Test
    public void whenReadEvenNumbersFromFile() {
        List<Integer> expected = List.of(4, 16, 100, 74, 24, 18);
        List<Integer> result = EvenNumberFile.filterEvenNumbers(
                                EvenNumberFile.splitByLines(
                                EvenNumberFile.readFromFile("even.txt")));

        Assert.assertArrayEquals(expected.toArray(), result.toArray());
    }
}
