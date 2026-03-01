package base;

import core.logging.LoggerManager;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

public class WebActions extends BasePage{

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    LoggerManager logger = new LoggerManager(this.getClass());

    public WebActions(WebDriver driver){
        super(driver);
    }

    public WebActions clickOnButton(WebElement button, String buttonName){
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
        logger.logMessage("info","Clicked on the " + buttonName + " button");
        return this;
    }
}
