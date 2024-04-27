package steps.UiSteps;

import PageObjects.ShippingPage;
import PageObjects.VerificationPage;
import common.Context.Context;
import common.Context.UiTestContext;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import steps.Steps;

public class VerificationSteps extends Steps {

    private static final Logger logger = LoggerFactory.getLogger(VerificationSteps.class);

    private VerificationPage verificationPage;

    public VerificationSteps(VerificationPage verificationPage) {
        this.verificationPage = verificationPage;
    }
    public void fillAllFieldsAndClickNext(final String propertiesName) {

    }
}
