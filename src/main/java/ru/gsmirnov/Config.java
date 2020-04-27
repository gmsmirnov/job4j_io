package ru.gsmirnov;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Gregory Smirnov (gsmirnov <artress@ngs.ru>)
 * @version 0.1
 * @since 2020-04-27
 */
public class Config {
    private static final Logger LOG = LogManager.getLogger(Config.class);

    /**
     * The path to the config file.
     */
    private final String path;

    /**
     * Loaded configuration stored in this map.
     */
    private final Map<String, String> values = new HashMap<String, String>();

    /**
     * The constructor. Initializes config-file path.
     *
     * @param path the specified config-file path.
     */
    public Config(String path) {
        this.path = path;
    }

    /**
     * Loads the configuration into hash-map from the config-file.
     */
    public void load() {
        List<String> lines = new ArrayList<String>();
        try (BufferedReader in = new BufferedReader(new FileReader(this.path))) {
            lines = in.lines()
                    .filter(this.filterComment())
                    .filter(this.filterEmptyLine())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.splitKeyValue(lines);
    }

    /**
     * Splits filtered lines into key string and value string. And puts it into config hash map.
     *
     * @param lines filtered config lines.
     */
    private void splitKeyValue(List<String> lines) {
        for (String line : lines) {
            String[] split = line.split("=");
            if (!split[0].isEmpty() && !split[1].isEmpty()) {
                this.values.put(split[0], split[1]);
            }
        }
    }

    /**
     * Gets the config value mapped to the specified key.
     *
     * @param key the specified key.
     * @return the value mapped to the specified key.
     */
    public String value(String key) {
        return this.values.get(key);
    }

    /**
     * Filter to skip comment lines in the configuration file.
     *
     * @return predicate which checks comment lines.
     */
    private Predicate<String> filterComment() {
        return l -> !l.startsWith("#");
    }

    /**
     * Filter to skip empty lines in the configuration file.
     *
     * @return predicate which checks empty lines.
     */
    private Predicate<String> filterEmptyLine() {
        return l -> !l.isEmpty();
    }

    /**
     * String presentation of loaded configuration.
     *
     * @return string presentation of loaded configuration.
     */
    @Override
    public String toString() {
        StringBuilder str =  new StringBuilder("Config:").append(System.lineSeparator());
        for (Map.Entry<String, String> configEntry : this.values.entrySet()) {
            str.append(configEntry.getKey()).append("=").append(configEntry.getValue()).append(System.lineSeparator());
        }
        str.deleteCharAt(str.length() - 1);
        return str.toString();
    }
}