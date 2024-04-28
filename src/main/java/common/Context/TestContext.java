package common.Context;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.DataUtils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@Getter
@Setter
public abstract class TestContext {

    private String testMethodName;
    @Getter
    private WebDriver webDriver;

    public void initializeFireFoxWebDriver() {
        System.setProperty("webdriver.chrome.driver", "/Users/HAiL/IdeaProjects/aqa/lib/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.get("https://dloqspth835qm.cloudfront.net/"); //QA
        driver.manage().window().maximize();
        this.webDriver = driver;
    }

    public void initializeChromeWebDriver() {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        System.setProperty("webdriver.chrome.driver", DataUtils.getAbsolutePathToRoot() + "/lib/chromedriver");
        final DesiredCapabilities capabilities = new DesiredCapabilities();

        LoggingPreferences logPref = new LoggingPreferences();
        logPref.enable(LogType.PERFORMANCE, Level.ALL);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPref);

        final ChromeOptions options = new ChromeOptions();
        //options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        var prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.cookies", 1);
        prefs.put("profile.cookie_controls_mode", 1);
        prefs.put("enableNetwork", true);
        options.setExperimentalOption("prefs", prefs);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        WebDriver driver = new ChromeDriver(capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        this.webDriver = driver;
    }

    public void initializeWebDriver() {
        initializeChromeWebDriver();
    }

    public void closeWebDriverConnection() {
        if (webDriver != null) {
            webDriver.close();
            webDriver.quit();
        }
    }

}
