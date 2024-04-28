package PageObjects;

import elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReviewPaymentsPage extends PageObject {

    private static final Logger logger = LoggerFactory.getLogger(ReviewPaymentsPage.class);
    private final WebDriver webDriver;
    By btnPlaceOrder = By.xpath("//button[@title='Place Order']");

    public ReviewPaymentsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        super.webDriver = webDriver;
        waitForJStoLoad();
        waitForElementToLoad(btnPlaceOrder);
    }

    public ConfirmationPage clickPlaceOrder(){
        new Button(webDriver, btnPlaceOrder).click();
        return new ConfirmationPage(webDriver);
    }

}
