package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfirmationPage extends PageObject {

    private static final Logger logger = LoggerFactory.getLogger(ConfirmationPage.class);
    private final WebDriver webDriver;
    By orderID = By.xpath("//div[@class='checkout-success']/p/span");

    public ConfirmationPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        super.webDriver = webDriver;
        waitForJStoLoad();
        waitForElementToLoad(orderID);
    }

    public String getOrderId() {
        return webDriver.findElement(orderID).getText();
    }
}
