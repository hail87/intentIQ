package steps.UiSteps;

import PageObjects.BagsPage;
import PageObjects.ShippingPage;
import lombok.Getter;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import PageObjects.MainPage;
import common.Context.Context;
import common.Context.UiTestContext;
import steps.Steps;
import utils.DataUtils;

@Getter
public class MainSteps extends Steps {

    private static final Logger logger = LoggerFactory.getLogger(MainSteps.class);

    private WebDriver webDriver;

    private MainPage mainPage;

    public MainSteps(MainPage mainPage, TestInfo testInfo) {
        this.mainPage = mainPage;
        webDriver = Context.getTestContext(testInfo.getTestMethod().get().getName(), UiTestContext.class).getWebDriver();
    }

    public MainSteps(TestInfo testInfo) {
        webDriver = Context.getTestContext(testInfo.getTestMethod().get().getName(), UiTestContext.class).getWebDriver();
    }

    protected void openUrl (String url) {
        webDriver.get(url);
    }

    public ShippingPage openShippingPage() {
        openUrl(DataUtils.getPropertyValue("url.properties", "shoppingCart"));
        return new ShippingPage(webDriver);
    }

    public void openSettingsPage () {
        openUrl(DataUtils.getPropertyValue("url.properties", "settingsPage"));
    }

    public MainPage openMainPage () {
        openUrl(DataUtils.getPropertyValue("url.properties", "mainPage"));
        mainPage = new MainPage(webDriver);
        return mainPage;
    }

    public MainPage closeWelcomePopUp () {
        return mainPage.clickConsentButton();
    }

    public BagsSteps goToBags () {
        mainPage.hoverGearButton();
        return new BagsSteps(mainPage.goToBags());
    }

}
