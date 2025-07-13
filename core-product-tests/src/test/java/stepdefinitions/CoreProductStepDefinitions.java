package stepdefinitions;

import com.veeva.framework.driver.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pages.CoreHomePage;
import pages.ShopPage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CoreProductStepDefinitions {
    private static final Logger log = LogManager.getLogger(CoreProductStepDefinitions.class);

    private WebDriver driver = DriverManager.getDriver();
    private CoreHomePage homePage = new CoreHomePage();

    private ShopPage shopPage;

    @Given("I am on the Core Product Home Page")
    public void i_am_on_core_product_home_page() {
        Allure.step("Launching Core Product Home Page");
        homePage.homePage();
        log.info("Navigated to Warriors home page");
    }

    @When("I navigate to the Shop Menu and select Men's section")
    public void i_navigate_to_the_shop_menu_and_select_men_s_section() {
        homePage.goToMensShop();
        shopPage = new ShopPage();
    }

    @When("I scrape all Jacket items across all pages")
    public void i_scrape_all_jacket_items() {
        shopPage.selectJacketsFilter();
        shopPage.scrapeAllJackets();
    }

    @Then("I store Jacket price, title, and Top Seller message in a text file and attach it to the report")
    public void i_store_jacket_details() {
        shopPage.saveJacketDetailsToFile();
        // Attach file to Allure
        try {
            Allure.addAttachment("Jackets File", new FileInputStream("test-output/jackets.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @When("I hover on the menu and click on New & Features")
    public void i_hover_on_the_menu_and_click_on_new_features() {
        homePage.hoverAndClickOnFeatures();
    }

    @Then("I count the total number of Video Feeds")
    public void i_count_the_total_number_of_video_feeds() {
        int countOfVideos = homePage.countOfVideoFeed();
        log.info("total number of video feed in the page {}", countOfVideos);
    }

    @Then("I count how many Video Feeds are marked as 3D or above")
    public void i_count_how_many_video_feeds_are_marked_as_3d_or_above() {
        int threeDaysOrAboveVideos = homePage.countTilesOlderThan3Days();
        log.info("total number of video feed in the page which are 3 or more days older {}", threeDaysOrAboveVideos);
    }

}