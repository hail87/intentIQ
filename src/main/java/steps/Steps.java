package steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.Util;
import utils.DataUtils;

import java.util.Map;

public abstract class Steps {

    private static final Logger logger = LoggerFactory.getLogger(Steps.class);

    public static String verifyExpectedResults(int ar, int er) {
        String actualResult = String.valueOf(ar);
        String expectedResult = String.valueOf(er);
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String callingMethod = stackTraceElements[2].getMethodName();
        String callingClass = Util.getCallingClass().getName().split("\\.")[Util.getCallingClass().getName().split("\\.").length - 1];
        if (actualResult.equalsIgnoreCase(expectedResult)) {
            logger.info(String.format("\n[%s:%s]: expected '%s' and actual '%s' results are the same\n", callingClass, callingMethod, expectedResult, actualResult));
            return "";
        } else {
            String message = String.format("\n[%s:%s]: expected '%s' and actual '%s' results are NOT the same\n", callingClass, callingMethod, expectedResult, actualResult);
            logger.error(message);
            return message;
        }
    }

    public String checkPostRequestForShippingInformation(WebDriver webDriver, String propertyName) {
        StringBuilder errorMessage = new StringBuilder();

        for (LogEntry entry : webDriver.manage().logs().get(LogType.PERFORMANCE)) {
            if (entry.getMessage().contains("\"method\":\"POST\"") && entry.getMessage().contains("/set-payment-information")) {
                System.out.println("\nPost request with next payment information was sent :\n" + entry
                        + "\n\nChecking for expected data...\n\n");

                String email = DataUtils.getPropertyValue(propertyName + ".properties", "email");
                if (!entry.getMessage().toString().replace("\"", "").replace("\\", "").contains("email:" + email))
                    errorMessage.append("\nEmail " + email + " wasn't found at http request!\n");
            }
        }

        for (LogEntry entry : webDriver.manage().logs().get(LogType.PERFORMANCE)) {
            if (entry.getMessage().contains("\"method\":\"POST\"") && entry.getMessage().contains("/shipping-information")) {
                System.out.println("\nPost request with next shipping information was sent :\n" + entry
                        + "\n\nChecking for expected data...\n\n");

                String name = DataUtils.getPropertyValue(propertyName + ".properties", "name");
                if (!entry.getMessage().toString().replace("\"", "").replace("\\", "").contains("firstname:" + name))
                    errorMessage.append("\nName " + name + " wasn't found at http request!\n");

                String surname = DataUtils.getPropertyValue(propertyName + ".properties", "surname");
                if (!entry.getMessage().toString().replace("\"", "").replace("\\", "").contains("lastname:" + surname))
                    errorMessage.append("\nSurname " + surname + " wasn't found at http request!\n");

                String company = DataUtils.getPropertyValue(propertyName + ".properties", "company");
                if (!entry.getMessage().toString().replace("\"", "").replace("\\", "").contains("company:" + company))
                    errorMessage.append("\nCompany " + company + " wasn't found at http request!\n");
            }
        }
        return errorMessage.toString();
    }

}
