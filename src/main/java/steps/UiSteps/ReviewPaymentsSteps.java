package steps.UiSteps;

import PageObjects.ReviewPaymentsPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import steps.Steps;

public class ReviewPaymentsSteps extends Steps {

    private static final Logger logger = LoggerFactory.getLogger(ReviewPaymentsSteps.class);

    private ReviewPaymentsPage reviewPaymentsPage;

    public ReviewPaymentsSteps(ReviewPaymentsPage reviewPaymentsPage) {
        this.reviewPaymentsPage = reviewPaymentsPage;
    }
    public ConfirmationSteps placeOrder() {
        return new ConfirmationSteps(reviewPaymentsPage.clickPlaceOrder());
    }
}
