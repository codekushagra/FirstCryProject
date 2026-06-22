package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class A_HomePage {
    public WebDriver driver;

    public A_HomePage(WebDriver driver) {
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
}
