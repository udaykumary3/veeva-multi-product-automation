package pages;

import com.veeva.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CoreHomePage extends BasePage {
    @FindBy(xpath = "(//span[contains(text(),'Shop')])[1]")
    private WebElement shopMenu;

    @FindBy(xpath = "(//a[@title=\"Men's\"])[1]")
    private WebElement mensSection;

    @FindBy(xpath = "//a[contains(text(), 'New & Features')]")
    private WebElement newAndFeaturesMenu;

    @FindBy(xpath = "//div[contains(@class, 'hover:cursor-pointer') and normalize-space()='x']")
    private WebElement popupCloseButton;

    @FindBy(xpath = "(//span[contains(text(),'...')])[1]")
    private WebElement moreOption;

    @FindBy(xpath = "(//a[contains(text(),'News & Features')])[1]")
    private WebElement clickNewFeature;

    @FindBy(css = "li.accent-primary-border")
    private List<WebElement> videoFeed;

    @FindBy(xpath = "//a[@data-testid='tile-featured-article-link']")
    private WebElement featuredVideo;

    /**
     * Constructor — uses BasePage constructor
     * No need to call PageFactory.initElements — already done in BasePage!
     */
    public CoreHomePage() {
        super();
    }

    public void dismissCoreProductPopupIfPresent() {
        try {
            waitForElementToBeClickable(popupCloseButton);
            popupCloseButton.click();
            System.out.println("Popup dismissed.");
        } catch (Exception e) {
            System.out.println("Popup not present. Continuing...");
        }
    }

    public void homePage() {
        String url = "https://www.nba.com/warriors";
        driver.get(url);
        dismissCoreProductPopupIfPresent();
    }

    /**
     * Navigate to Men's Shop section
     */
    public void goToMensShop() {
        waitForElementToBeClickable(shopMenu);
        hoverOverElement(shopMenu);
        hoverAndClick(mensSection);
        switchToNewTab("Golden State Warriors");
    }

    public void hoverAndClickOnFeatures() {
        waitForVisibility(moreOption);
        hoverOverElement(moreOption);
        hoverAndClick(clickNewFeature);
        waitForUrlContains("news");
    }

    public int countOfVideoFeed() {
        waitForPageLoad();
        waitForVisibility(featuredVideo);
        scrollIntoView(featuredVideo);
        return videoFeed.size();
    }

    public int countTilesOlderThan3Days() {
        waitForPageLoad();
        int count = 0;

        List<WebElement> freshVideoFeeds = driver.findElements(By.cssSelector("li.accent-primary-border"));

        for (int i = 0; i < freshVideoFeeds.size(); i++) {
            try {
                freshVideoFeeds = driver.findElements(By.cssSelector("li.accent-primary-border"));
                WebElement tile = freshVideoFeeds.get(i);

                WebElement timeElement = tile.findElement(By.tagName("time"));
                String timeLabel = timeElement.getAttribute("aria-label");

                String numberPart = timeLabel.split(" ")[0];
                int daysAgo = Integer.parseInt(numberPart);

                if (daysAgo >= 3) {
                    count++;
                }
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                System.out.println("Time element missing or stale for tile #" + i + ", skipping.");
            } catch (Exception e) {
                System.out.println("Unexpected error for tile #" + i + ": " + e.getMessage());
            }
        }
        return count;
    }

}