package stepDefinations;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import base.Base;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.C_PriceFilterPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class PriceFilterSteps extends Base {
    private static final Logger log = LogManager.getLogger(PriceFilterSteps.class);
    C_PriceFilterPage pricePage;

    @Given("User is on the FirstCry home page for price filter")
    public void user_is_on_firstcry_home_page_for_price_filter() throws Exception {
        log.info("Navigating to home page for price filter...");
        getDriver().get(getUrl());
        pricePage = new C_PriceFilterPage(getDriver());
        Thread.sleep(2000);
        log.info("Home Page: " + getDriver().getTitle());
    }

    @And("User searches for {string} and waits")
    public void user_searches_for_and_waits(String product) throws Exception {
        log.info("Searching for product: " + product);
        pricePage.getSearchBox().clear();
        pricePage.getSearchBox().sendKeys(product);
        Thread.sleep(2000);
    }

    @And("User clicks the search button")
    public void user_clicks_the_search_button() throws Exception {
        log.info("Clicking the search button.");
        pricePage.getSearchButton().click();
        Thread.sleep(4000);
        log.info("Search Results Page: " + getDriver().getTitle());
    }

    private void scrollToCenterAndClick(WebElement el) throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        Thread.sleep(500);
        el.click();
    }

    @And("User applies 3 brand filters")
    public void user_applies_3_brand_filters() throws Exception {
        String[] brands = {"Babyhug", "Babyoye", "US Polo Assn"};
        for (String name : brands) {
            log.info("Attempting to apply brand filter: " + name);
            for (int i = 0; i < 3; i++) {
                try {
                    WebElement el = getDriver().findElement(
                        By.xpath("//a[@role='checkbox' and contains(@aria-label,'" + name + "')]")
                    );
                    scrollToCenterAndClick(el);
                    log.info("Successfully clicked brand: " + name);
                    Thread.sleep(3000);
                    break;
                } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                    log.warn("Stale " + name + " (i " + (i + 1) + "). Retrying...");
                    Thread.sleep(1000);
                }
            }
        }
    }

    @When("User selects price range {string}")
    public void user_selects_price_range(String range) throws Exception {
        String xpath = "//a[contains(@aria-label,'" + range + "')]";
        log.info("Attempting to select price range: " + range);
        for (int i = 0; i < 3; i++) {
            try {
                WebElement el = getDriver().findElement(By.xpath(xpath));
                scrollToCenterAndClick(el);
                log.info("Successfully clicked price range: " + range);
                Thread.sleep(2000);
                break;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                log.warn("Stale or click-intercepted exception on price range " + range + " (attempt " + (i + 1) + "). Retrying...");
                Thread.sleep(1000);
            }
        }
    }

    @And("User selects price range {string} after 3 seconds")
    public void user_selects_price_range_after_3_seconds(String range) throws Exception {
        Thread.sleep(3000);
        user_selects_price_range(range);
    }

    @Then("Price filter results are displayed for {string}")
    public void price_filter_results_are_displayed(String range) throws Exception {
        Thread.sleep(5000);
        String[] limits = range.split(" to ");
        int min = Integer.parseInt(limits[0].trim()), max = Integer.parseInt(limits[1].trim());

        for (WebElement el : getDriver().findElements(By.className("li_prc"))) {
            int price = Integer.parseInt(el.getText().replaceAll("^\\D+", "").split("\\D+")[0]);
            Assert.assertTrue(price >= min && price <= max, "Price " + price + " out of range: " + range);
        }
        Thread.sleep(5000);
    }
}
