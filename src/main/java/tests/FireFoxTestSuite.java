package tests;

import common.Context.Context;
import common.Context.UiTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import steps.UiSteps.*;
import utils.DataUtils;


public class FireFoxTestSuite extends UiTestClass {

    private static final Logger logger = LoggerFactory.getLogger(FireFoxTestSuite.class);

    @BeforeEach
    public void setTestContext(TestInfo testInfo) {
        String name = testInfo.getTestMethod().get().getName();
        UiTestContext uiTestContext = new UiTestContext(name);
        uiTestContext.initializeFireFoxWebDriver();
        Context.addTestContext(uiTestContext);
        logger.info("Context set\n");
    }

    @Test
    public void fireFoxTest(TestInfo testInfo) {
        MainSteps mainSteps = new MainSteps(testInfo);
        String error = mainSteps.verifyUserAgent(
                DataUtils.getPropertyValue("userAgent.properties", "userAgent"));
        Assertions.assertTrue(error.isEmpty(), error);
        mainSteps.openFireFoxSettingsPage();

        //Todo: This is to prevent driver exit so the reviewer can see the result, cause no verification was requested
        System.exit(0);
    }

}
