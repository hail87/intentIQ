package steps.UiSteps;

import PageObjects.ConfirmationPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import steps.Steps;
import utils.DataUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ConfirmationSteps extends Steps {

    private static final Logger logger = LoggerFactory.getLogger(ConfirmationSteps.class);

    private ConfirmationPage confirmationPage;

    public ConfirmationSteps(ConfirmationPage confirmationPage) {
        this.confirmationPage = confirmationPage;
    }

    public String getAndSaveOrderIdToFile(String fileName) {
        StringBuilder errorMessage = new StringBuilder();
        String orderId = confirmationPage.getOrderId();
        if (orderId.isEmpty()) {
            errorMessage.append("\nThere is no orderId found!\n");
        } else {
            File file = new File(DataUtils.getAbsolutePathToRoot() + "/" + fileName);
            try (PrintWriter out = new PrintWriter(file)) {
                out.println(confirmationPage.getOrderId());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return errorMessage.toString();
    }
}
