package com.veeva.framework.hooks;

import com.veeva.framework.config.BrowserType;
import com.veeva.framework.driver.DriverManager;
import com.veeva.framework.utils.FrameworkConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class Hooks {

    @Before
    public void setUp() {
        BrowserType browserType = FrameworkConfig.getBrowser();
        Map<String, String> options = new HashMap<>();
        options.put("headless", "false");
        options.put("incognito", "false");

        DriverManager.initDriver(browserType, options);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            takeScreenshot(scenario);
        }
        attachLogsToAllure(scenario);
        DriverManager.quitDriver();
    }

    private void takeScreenshot(Scenario scenario) {
        try {
            byte[] screenshotBytes = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            // Attach to Cucumber report
            scenario.attach(screenshotBytes, "image/png", screenshotName);
            // Attach to Allure report
            Allure.addAttachment(screenshotName, "image/png", new ByteArrayInputStream(screenshotBytes), ".png");
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

    private void attachLogsToAllure(Scenario scenario) {
        File logFile = new File("logs/execution.log");
        if (logFile.exists()) {
            try {
                String attachmentName = "Execution_Logs_" + scenario.getName().replaceAll(" ", "_");
                Allure.addAttachment(attachmentName, "text/plain", new FileInputStream(logFile), ".log");
            } catch (Exception e) {
                System.out.println("Log file not found: " + e.getMessage());
            }
        } else {
            System.err.println("Log file does not exist at: " + logFile.getAbsolutePath());
        }
    }
}

