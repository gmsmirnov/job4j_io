package ru.gsmirnov;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Config load testing.
 */
public class ConfigTest {
    private Config config;

    @Before
    public void init() {
        this.config = new Config("./app.properties");
        this.config.load();
    }

    @Test
    public void testKeyValue() {
        assertEquals("password", this.config.value("hibernate.connection.password"));
        assertEquals("postgres", this.config.value("hibernate.connection.username"));
    }

    @Test
    public void printLoadedConfig() {
        System.out.println(this.config);
    }
}