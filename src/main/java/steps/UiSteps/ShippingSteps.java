package steps.UiSteps;

import PageObjects.BagsPage;
import PageObjects.ShippingPage;
import common.Context.Context;
import common.Context.UiTestContext;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import steps.Steps;

public class ShippingSteps extends Steps {

    private static final Logger logger = LoggerFactory.getLogger(ShippingSteps.class);

    //WebDriver webDriver;

    private ShippingPage shippingPage;

    public ShippingSteps(ShippingPage shippingPage) {
        //webDriver = Context.getTestContext(testInfo.getTestMethod().get().getName(), UiTestContext.class).getWebDriver();
        this.shippingPage = shippingPage;
    }

    public VerificationSteps fillAllFieldsAndClickNext(final String propertiesName) {
        shippingPage.fillAllFields(propertiesName);
        return new VerificationSteps(shippingPage.clickNext());
    }
}
