package PageObjects;

import elements.Element;
import elements.ProductItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import elements.Button;

public class BagsPage extends PageObject {

    private static final Logger logger = LoggerFactory.getLogger(BagsPage.class);
    private final WebDriver webDriver;
    By productsAtThePage = By.xpath("//li[@class='item product product-item']");

    public BagsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        super.webDriver = webDriver;
        waitForJStoLoad();
        waitForElementToLoad(productsAtThePage);
    }

    public ProductItem hoverProduct(int index) {
        ProductItem productItem = new ProductItem(webDriver, webDriver.findElements(productsAtThePage).get(index - 1));
        productItem.hover();
        return productItem;
    }
}
