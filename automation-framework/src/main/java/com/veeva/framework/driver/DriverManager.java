package com.veeva.framework.driver;

import com.veeva.framework.config.BrowserType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Map;

/**
 * DriverManager handles creation and management of WebDriver instances
 * using ThreadLocal to support parallel execution.
 */
public final class DriverManager {
    private static final Logger log = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    // Private constructor to prevent instantiation
    private DriverManager() {
    }

    /**
     * Initializes the ThreadLocal WebDriver for the given browserType.
     *
     * @param browserType Browser name ("chrome", "firefox").
     * @param optionsMap  Map of option flags (key-value pairs)
     */
    public static void initDriver(BrowserType browserType, Map<String, String> optionsMap) {
        if (threadLocalDriver.get() != null) {
            log.warn("Driver is already initialized for this thread");
            return;
        }

        log.info("initializing WebDriver for browserType: {}", browserType);

        switch (browserType) {
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                // headless
                if (Boolean.parseBoolean(optionsMap.getOrDefault("headless", "false"))) {
                    chromeOptions.addArguments("--headless=new");
                }
                // incognito
                if (Boolean.parseBoolean(optionsMap.getOrDefault("incognito", "false"))) {
                    chromeOptions.addArguments("--incognito");
                }
                threadLocalDriver.set(new ChromeDriver(chromeOptions));
                break;

            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (Boolean.parseBoolean(optionsMap.getOrDefault("headless", "false"))) {
                    firefoxOptions.addArguments("--headless");
                }
                threadLocalDriver.set(new FirefoxDriver(firefoxOptions));
                break;
            default:
                log.error("Unsupported browserType : {}", browserType);
                throw new IllegalArgumentException("Unsupported browserType : " + browserType);
        }

        WebDriver driver = getDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();

        log.info("{} browserType launched successfully.", browserType);
    }

    /**
     * Returns the WebDriver instance for the current thread.
     *
     * @return WebDriver instance.
     */
    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    /**
     * Quits the WebDriver and removes it from ThreadLocal.
     */
    public static void quitDriver() {
        WebDriver driver = threadLocalDriver.get();
        if (driver != null) {
            driver.quit();
            threadLocalDriver.remove();
            log.info("WebDriver session closed and removed from ThreadLocal.");
        } else {
            log.warn("No WebDriver instance found for this thread to quit.");
        }
    }
}
