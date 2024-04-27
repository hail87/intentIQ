package PageObjects;

import elements.DropDown;
import elements.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DataUtils;

public class VerificationPage extends PageObject {

    private static final Logger logger = LoggerFactory.getLogger(VerificationPage.class);
    private final WebDriver webDriver;
    By btnPlaceOrder = By.xpath("//button[@title='Place Order']");

    public VerificationPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        super.webDriver = webDriver;
        waitForJStoLoad();
        waitForElementToLoad(btnPlaceOrder);
    }

}
