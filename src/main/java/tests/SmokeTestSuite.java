package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import common.Context.Context;
import common.Context.UiTestContext;
import steps.UiSteps.*;


public class SmokeTestSuite extends UiTestClass {

    private static final Logger logger = LoggerFactory.getLogger(SmokeTestSuite.class);

    @BeforeEach
    public void setTestContext(TestInfo testInfo) {
        String name = testInfo.getTestMethod().get().getName();
        UiTestContext uiTestContext = new UiTestContext(name);
        uiTestContext.initializeWebDriver();
        Context.addTestContext(uiTestContext);
        logger.info("Context set\n");
    }

    @Test
    public void openChromeCookiesSettings(TestInfo testInfo) {
        new MainSteps(testInfo).openSettingsPage();
        System.exit(0);
    }

    @Test
    public void buyingProcess(TestInfo testInfo) {
        MainSteps mainSteps = new MainSteps(testInfo);
        mainSteps.openMainPage();
        mainSteps.closeWelcomePopUp();
        BagsSteps bagsSteps = mainSteps.goToBags();
        bagsSteps.addFirstBagToShoppingCart();
        ShippingSteps shippingSteps = mainSteps.openShippingPage();
        ReviewPaymentsSteps reviewPaymentsSteps = shippingSteps.fillAllFieldsAndClickNext("shippingInformation");
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(reviewPaymentsSteps.checkPostRequestForShippingInformation(
                Context.getTestContext(testInfo).getWebDriver(), "shippingInformation"));
        ConfirmationSteps confirmationSteps = reviewPaymentsSteps.placeOrder();
        errorMessage.append(confirmationSteps.getAndSaveOrderIdToFile("test_data.txt"));

        Assertions.assertTrue(errorMessage.toString().isEmpty(), errorMessage.toString());
    }
}
