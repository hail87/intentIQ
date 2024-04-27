package steps.UiSteps;

import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import PageObjects.BagsPage;
import PageObjects.MainPage;
import common.Context.Context;
import common.Context.UiTestContext;
import steps.Steps;

public class BagsSteps extends Steps {

    private static final Logger logger = LoggerFactory.getLogger(BagsSteps.class);

    private BagsPage bagsPage;

//    public BagsSteps(TestInfo testInfo) {
//        webDriver = Context.getTestContext(testInfo.getTestMethod().get().getName(), UiTestContext.class).getWebDriver();
//    }

    public BagsSteps(BagsPage bagsPage) {
        this.bagsPage = bagsPage;
    }

    public void addFirstBagToShoppingCart() {
        bagsPage.hoverProduct(1).addToCart();
    }
}
