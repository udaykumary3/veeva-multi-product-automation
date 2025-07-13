package com.veeva.framework.pages;

import com.veeva.framework.driver.DriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;

    // Constructor initializes PageFactory and Wait
    protected BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Wait for an element to be visible.
     *
     * @param element WebElement to wait for.
     */
    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Click an element when it is visible.
     *
     * @param element WebElement to click.
     */
    protected void click(WebElement element) {
        waitForVisibility(element);
        element.click();
    }

    /**
     * Type text into a visible input field.
     *
     * @param element WebElement input field.
     * @param text    Text to enter.
     */
    protected void sendKeys(WebElement element, String text) {
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get the page title.
     *
     * @return Current page title.
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Hover over an element.
     */
    protected void hoverOverElement(WebElement element) {
        waitForVisibility(element);
        actions.moveToElement(element).perform();
    }

    /**
     * Hover and click an element.
     */
    protected void hoverAndClick(WebElement element) {
        waitForVisibility(element);
        actions.moveToElement(element).click().perform();
    }

    /**
     * Right-click (context click) an element.
     */
    protected void rightClick(WebElement element) {
        waitForVisibility(element);
        actions.contextClick(element).perform();
    }

    /**
     * Drag one element and drop onto another.
     */
    protected void dragAndDrop(WebElement source, WebElement target) {
        waitForVisibility(source);
        waitForVisibility(target);
        actions.dragAndDrop(source, target).perform();
    }

    /**
     * Switches WebDriver focus to the newest opened tab or window.
     */
    protected void switchToNewTab(String expectedTitle) {
        String originalWindow = driver.getWindowHandle();

        // Wait for a new window/tab to appear
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        // Wait for the new tab's title
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.titleContains(expectedTitle));
    }

    /**
     * Click an element using JavaScriptExecutor.
     */
    protected void clickUsingJS(WebElement element) {
        waitForVisibility(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Scroll an element into view using JavaScriptExecutor.
     */
    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Send keys or set value using JavaScriptExecutor.
     */
    protected void setValueUsingJS(WebElement element, String value) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, value);
    }

    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until((ExpectedCondition<Boolean>) webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Wait until the page title contains a specific text.
     */
    public void waitForTitleContains(String partialTitle) {
        wait.until(ExpectedConditions.titleContains(partialTitle));
    }

    /**
     * Wait until a specific URL pattern is present.
     */
    public void waitForUrlContains(String partialUrl) {
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }

}
