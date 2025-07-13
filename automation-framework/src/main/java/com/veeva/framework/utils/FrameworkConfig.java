package com.veeva.framework.utils;

import com.veeva.framework.config.BrowserType;

public final class FrameworkConfig {
    private FrameworkConfig() {
    }

    public static BrowserType getBrowser() {
        String browser = ConfigReader.get("browser");
        if (browser == null || browser.isBlank()) {
            throw new RuntimeException("Browser type is not set in config.properties!");
        }
        return BrowserType.valueOf(browser.toUpperCase());
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(ConfigReader.get("headless"));
    }

    public static boolean isIncognito() {
        return Boolean.parseBoolean(ConfigReader.get("incognito"));
    }

    public static String getHomePageUrl() {
        String url = ConfigReader.get("homepageurl");
        if (url == null || url.isBlank()) {
            throw new RuntimeException("Homepage URL is not set in config.properties!");
        }
        return url;
    }
}
