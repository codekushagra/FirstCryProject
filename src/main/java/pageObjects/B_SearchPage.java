package pageObjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class B_SearchPage {
    public WebDriver driver;

    public B_SearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getBrandByName(String brandName) {
        return driver.findElement(By.xpath("//a[contains(@aria-label,'" + brandName + "')]"));
    }

    public List<WebElement> getBrandFilters() {
        return driver.findElements(By.xpath("//input[contains(@placeholder, 'Brand') or contains(@placeholder, 'brand')]/following-sibling::div[contains(@class, 'scrollable')][1]/a[@role='checkbox']"));
    }
}
