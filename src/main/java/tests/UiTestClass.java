package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import common.Context.Context;
import common.Context.UiTestContext;


public abstract class UiTestClass {

    private static final Logger logger = LoggerFactory.getLogger(UiTestClass.class);

    @BeforeAll
    static void createContext() {
        Context.initialize();
    }

    @AfterEach
    public void cleanTestDataAndCloseConnection(TestInfo testInfo) {
        UiTestContext uiTestContext = getUiTestContext(testInfo);
        uiTestContext.closeWebDriverConnection();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Context.deleteTestContext(uiTestContext);
    }

    public UiTestContext getUiTestContext(TestInfo testInfo) {
        return Context.getTestContext(testInfo.getTestMethod().get().getName(), UiTestContext.class);
    }
}
