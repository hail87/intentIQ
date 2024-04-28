package steps.UiSteps;

import PageObjects.ShippingPage;
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

    public ReviewPaymentsSteps fillAllFieldsAndClickNext(final String propertiesName) {
        shippingPage.fillAllFields(propertiesName);
        return new ReviewPaymentsSteps(shippingPage.clickNext());
    }
}
