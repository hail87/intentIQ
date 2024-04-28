package PageObjects;

import elements.Button;
import elements.DropDown;
import elements.TextField;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DataUtils;

public class ShippingPage extends PageObject {

    private static final Logger logger = LoggerFactory.getLogger(ShippingPage.class);
    private final WebDriver webDriver;
    By txtEmailAddress = By.id("customer-email");
    By txtFirstName = By.xpath("//div[@name='shippingAddress.firstname']//input");
    By txtLasttName = By.xpath("//div[@name='shippingAddress.lastname']//input");
    By txtCompany = By.xpath("//div[@name='shippingAddress.company']//input");
    By txtStreetAddress = By.xpath("//div[@name='shippingAddress.street.0']//input");
    By txtCity = By.xpath("//div[@name='shippingAddress.city']//input");
    By ddState = By.xpath("//div[@name='shippingAddress.region_id']//select");
    By txtZip = By.xpath("//div[@name='shippingAddress.postcode']//input");
    By ddCountry = By.xpath("//div[@name='shippingAddress.country_id']//select");
    By txtPhoneNumber = By.xpath("//div[@name='shippingAddress.telephone']//input");
    By rbFirstShippingMethod = By.xpath("//form[@id='co-shipping-method-form']//tr/td");
    By btnNext = By.xpath("//*[@id='shipping-method-buttons-container']//button[@type='submit']");

    By btnProceedToCheckout = By.xpath("//div[@id='cart-totals']/..//button[@title='Proceed to Checkout']");

    public ShippingPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        super.webDriver = webDriver;
        waitForJStoLoad();
        try {
            waitForElementToLoad(txtEmailAddress);
        } catch (TimeoutException e) {
            System.out.println("\nProceed to checkout\n");
            new Button(webDriver, btnProceedToCheckout).click();
        }
    }

    public ShippingPage fillAllFields(final String propertiesName) {
        String fullPropertiesName = propertiesName + ".properties";
        waitForElementToBeClickable(txtEmailAddress);
        new TextField(webDriver, txtEmailAddress).fillIn(DataUtils.getPropertyValue(fullPropertiesName, "email"));
        new TextField(webDriver, txtFirstName).fillIn(DataUtils.getPropertyValue(fullPropertiesName, "name"));
        new TextField(webDriver, txtLasttName).fillIn(DataUtils.getPropertyValue(fullPropertiesName, "surname"));
        new TextField(webDriver, txtCompany).fillIn(DataUtils.getPropertyValue(fullPropertiesName, "company"));
        new TextField(webDriver, txtStreetAddress).fillIn(DataUtils.getPropertyValue(fullPropertiesName, "street"));
        new TextField(webDriver, txtCity).fillIn(DataUtils.getPropertyValue(fullPropertiesName, "city"));
        new DropDown(webDriver, ddState).expandAndSelectByVisibleText(DataUtils.getPropertyValue(fullPropertiesName, "state"));
        new TextField(webDriver, txtZip).fillIn(DataUtils.getPropertyValue(fullPropertiesName, "zip"));
        new DropDown(webDriver, ddCountry).expandAndSelectByVisibleText(DataUtils.getPropertyValue(fullPropertiesName, "country"));
        new TextField(webDriver, txtPhoneNumber).fillIn(DataUtils.getPropertyValue(fullPropertiesName, "phone"));
        new Button(webDriver, rbFirstShippingMethod).click();
        return this;
    }

    public ReviewPaymentsPage clickNext() {
        new Button(webDriver, btnNext).click();
        return new ReviewPaymentsPage(webDriver);
    }
}
