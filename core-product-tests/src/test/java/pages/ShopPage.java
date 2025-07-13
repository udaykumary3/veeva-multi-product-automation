package pages;

import com.veeva.framework.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShopPage extends BasePage {


    List<String> jacketDetails = new ArrayList<>();
    @FindBy(xpath = "//span[@class = 'allDepartmentsBoxes-link-text' and contains(text(),'Jackets')]")
    private WebElement jacketsFilter;
    @FindBy(xpath = "//span[@class ='no-transform' and contains(text(),'Jackets')]")
    private WebElement jacketsSelection;
    @FindBy(css = ".product-card.row")
    private List<WebElement> productTiles;
    @FindBy(css = "li.next-page")
    private List<WebElement> nextButtonContainers;

    public ShopPage() {
        super();
    }

    public void selectJacketsFilter() {
        scrollIntoView(jacketsFilter);
        waitForElementToBeClickable(jacketsFilter);
        jacketsFilter.click();
        waitForVisibility(jacketsSelection);
    }

    public void scrapeAllJackets() {
        boolean hasNextPage = true;

        while (hasNextPage) {
            waitForVisibility(productTiles.get(0));

            for (WebElement product : productTiles) {
                String title = product.findElement(By.cssSelector(".product-card-title a")).getText().trim();
                String price = product.findElement(By.cssSelector(".price-card .money-value")).getText().trim();
                String topSeller = "";
                List<WebElement> topSellerElements = product.findElements(By.cssSelector(".top-seller-vibrancy-message"));
                if (!topSellerElements.isEmpty()) {
                    topSeller = topSellerElements.get(0).getText().trim();
                }
                String details = String.format("Title: %s | Price: %s | Top Seller: %s", title, price, topSeller);
                jacketDetails.add(details);
            }
            WebElement nextContainer = nextButtonContainers.get(0);
            String classes = nextContainer.getAttribute("class");

            if (classes.contains("disabled")) {
                hasNextPage = false;
            } else {
                WebElement nextLink = nextContainer.findElement(By.cssSelector("a[aria-label='next page']"));
                scrollIntoView(nextLink);
                waitForElementToBeClickable(nextLink);
                nextLink.click();
                waitForVisibility(productTiles.get(0));
            }
        }
    }

    public void saveJacketDetailsToFile() {
        try {
            File outputDir = new File("test-output");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            File file = new File(outputDir, "jackets.txt");
            try (FileWriter writer = new FileWriter(file)) {
                for (String line : jacketDetails) {
                    writer.write(line + System.lineSeparator());
                }
            }

            System.out.println("Jacket details saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
