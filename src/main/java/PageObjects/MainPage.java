package PageObjects;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import elements.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainPage extends PageObject {

    private static final Logger logger = LoggerFactory.getLogger(MainPage.class);
    private final WebDriver webDriver;
    By btnConsent = By.xpath("//button[@aria-label='Consent']");
    By btnGear = By.id("ui-id-6");
    By btnBags = By.id("ui-id-25");


    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        super.webDriver = webDriver;
        waitForJStoLoad();
        waitForElementToLoad(btnGear);
    }

    public MainPage clickConsentButton(){
        new Button(webDriver, this.btnConsent).click();
        return this;
    }

    public MainPage hoverGearButton(){
        new Button(webDriver, this.btnGear).hover();
        return this;
    }

    public BagsPage goToBags(){
        new Button(webDriver, this.btnBags).click();
        return new BagsPage(webDriver);
    }

}
