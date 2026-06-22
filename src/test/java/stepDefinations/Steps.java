package stepDefinations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import base.Base;
import pageObjects.A_HomePage;
import pageObjects.B_SearchPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Steps extends Base {
	private static final Logger log = LogManager.getLogger(Steps.class);
	A_HomePage homePage;
	B_SearchPage searchPage;

    @Given("User is on the FirstCry home page")
    public void user_is_on_the_firstcry_home_page() throws Exception {
        log.info("Navigating to URL: " + getUrl());
        getDriver().get(getUrl());
        homePage = new A_HomePage(getDriver());
        searchPage = new B_SearchPage(getDriver());
        Thread.sleep(2000);
        log.info("Page Title: " + getDriver().getTitle());
    }

    @When("User searches for {string}")
    public void user_searches_for(String product) throws Exception {
        log.info("Searching for product: " + product);
        homePage.getSearchBox().clear();
        homePage.getSearchBox().sendKeys(product);
        Thread.sleep(2000);
    }

    @When("User clicks on the search button")
    public void user_clicks_on_the_search_button() {
        log.info("Clicking the search button.");
        homePage.getSearchButton().click();
    }

    @Then("User should be redirected to the search results page")
    public void user_should_be_redirected_to_the_search_results_page() throws Exception {
        Thread.sleep(3000); // wait for results to load
        log.info("Redirected Page Title: " + getDriver().getTitle());
    }

    @Then("User applies brand filter and prints them")
    public void user_applies_brand_filter_and_prints_them() throws Exception {
        int total = searchPage.getBrandFilters().size();
        log.info("Total Brand Filters found: " + total);

        for (int i = 0; i < total; i++) {
            log.info("Brand filter " + (i + 1) + ": " + searchPage.getBrandFilters().get(i).getText());
        }

        int clickCount = Math.min(3, total);
        for (int i = 0; i < clickCount; i++) {
            WebElement brand = searchPage.getBrandFilters().get(i);
            log.info("Clicking brand " + (i + 1) + ": " + brand.getText());
            brand.click();
            Thread.sleep(2000);
        }
    }
}
