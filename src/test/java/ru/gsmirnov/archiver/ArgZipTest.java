package ru.gsmirnov.archiver;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Arguments class test.
 */
public class ArgZipTest {
    private String[] args = {"-d", "c:\\project\\job4j", "-e", ".xml", "-o", "project.zip"};

    private ArgZip argZip;

    @Before
    public void init() {
        this.argZip = new ArgZip(this.args);
    }

    @Test
    public void getDirectoryParamTest() {
        assertEquals("c:\\project\\job4j", this.argZip.directory());
    }

    @Test
    public void getExcludeParamTest() {
        assertEquals(".xml", this.argZip.exclude());
    }

    @Test
    public void getOutputParamTest() {
        assertEquals("c:\\project\\job4j\\project.zip", this.argZip.output());
    }

    @Test
    public void whenParamsValidThenTrue() {
        assertTrue(this.argZip.valid());
    }

    @Test
    public void whenParamsNotEnoughThenFalse() {
        ArgZip zip = new ArgZip(new String[] {"-d", "c:\\project\\job4j", "-e", ".xml"});
        assertFalse(zip.valid());
    }

    @Test
    public void whenParamsNotValidThenFalse() {
        ArgZip zip = new ArgZip(new String[] {"-d", "c:\\project\\job4j", "-e", ".xml", "-k", "khl"});
        assertFalse(zip.valid());
    }
}