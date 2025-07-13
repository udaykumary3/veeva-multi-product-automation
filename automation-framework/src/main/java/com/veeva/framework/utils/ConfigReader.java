package com.veeva.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {
    private static final Properties prop = new Properties();

    // Load properties when class loads
    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath!");
            }
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file.", e);
        }
    }

    // Private constructor to prevent instantiation
    private ConfigReader() {
    }

    // Public getter method
    public static String get(String key) {
        return prop.getProperty(key);
    }
}
