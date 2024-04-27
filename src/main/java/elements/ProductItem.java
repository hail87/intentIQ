package elements;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductItem extends Element {

    private static final Logger logger = LoggerFactory.getLogger(ProductItem.class);

    By btnAddToCart = By.xpath(".//button[@title='Add to Cart']");

    @Setter
    @Getter
    WebElement webElement;
    @Setter
    @Getter
    By locator;
    @Setter
    @Getter
    WebDriver webDriver;

    public ProductItem(WebDriver webDriver, By locator) {
        super(webDriver, locator);
        setLocator(locator);
        setWebDriver(webDriver);
        setWebElement(webDriver.findElement(locator));
        waitForJStoLoad(webDriver);
        waitForElementToLoad(locator, webDriver);
    }

    public ProductItem(WebDriver webDriver, WebElement webElement) {
        super(webElement, webDriver);
        setWebDriver(webDriver);
        waitForJStoLoad(webDriver);
        waitForElementToLoad(webElement, webDriver);
        setWebElement(webElement);
    }

    public void hover() {
        super.hover(webDriver, webElement);
    }

    public void addToCart() {
        new Button(webDriver, webElement.findElement(btnAddToCart)).click();
    }

    public void waitForElementToBeClickable() {
        WebDriverWait wait = new WebDriverWait(this.webDriver, waitForElementDelay);
        wait.until(ExpectedConditions.elementToBeClickable(this.locator));
    }

    public void waitForElementToDissapear() {
        WebDriverWait wait = new WebDriverWait(this.webDriver, waitForElementDelay);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(this.locator));
    }

    public boolean isEnabled() {
        return webElement.isEnabled();
    }

    public boolean isDisabled() {
        return super.isDisabled(webDriver, locator);
    }
}
