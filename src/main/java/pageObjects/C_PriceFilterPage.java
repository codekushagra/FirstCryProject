package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class C_PriceFilterPage {

    public WebDriver driver;

    public C_PriceFilterPage(WebDriver driver) {
        this.driver = driver;
    }

    By searchBox = By.id("search_box");
    By searchButton = By.className("search-button");

    public WebElement getSearchBox() {
        return driver.findElement(searchBox);
    }

    public WebElement getSearchButton() {
        return driver.findElement(searchButton);
    }

    public WebElement getPriceFilterByText(String range) {
        return driver.findElement(By.xpath("//a[contains(@aria-label,'" + range + "')]"));
    }
}
