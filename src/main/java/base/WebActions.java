package base;

import core.logging.LoggerManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import types.ElementType;

import java.time.Duration;
import java.util.logging.Logger;

public class WebActions extends BasePage{

    // Composition ('Has - A' Relationship)
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    LoggerManager logger = new LoggerManager(this.getClass());
    Actions actions = new Actions(driver);
    JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
    SoftAssert softAssert = new SoftAssert();
    Alert alert;
    String alertText;

    Select select;

    public WebActions(WebDriver driver){
        super(driver);
    }

    public WebActions clickOn(WebElement button, String elementName, ElementType elementType){
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
        logger.logMessage("info","Clicked on the %s %s".formatted( elementName, elementType.getValue()));
        return this;
    }
    public WebActions clickOn(By locator, String elementName, ElementType elementType){
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        logger.logMessage("info","Clicked on the %s %s.".formatted(elementName, elementType.getValue()));
        return this;
    }
    public Alert switchToAlert(){
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            alert = driver.switchTo().alert();
        } catch (TimeoutException te){
            logger.logMessage("info","Alert has not been appeared within the wait time...");
        } catch (NoAlertPresentException nape){
            logger.logMessage("info","No alert present at this moment...");
        }
        return alert;
    }
    public WebActions acceptTheAlert(){
        try {
            if(alert != null) {
                alert.accept();
                logger.logMessage("info", "Accepted the alert appeared on the screen...");
            } else{
                logger.logMessage("info","Not able to accept the alert as the alert reference is null...");
            }
        } catch (StaleElementReferenceException sere){
            logger.logMessage("info","Alert was dismissed before it could be accepted...");
        }
        return this;
    }
    public WebActions dismissTheAlert(){
        try{
            if(alert != null){
                alert.dismiss();
                logger.logMessage("info","Dismissed the alert appeared on the screen.");
            } else {
                logger.logMessage("info","Not able to dismiss the alert as the alert reference is null.");
            }
        } catch (StaleElementReferenceException sere){
            logger.logMessage("info","Alert was dismissed before it could be dismissed by driver.");
        }
        return this;
    }
    public WebActions getTextFromAlert(){
        try{
            if(alert != null){
                alertText = alert.getText();
                logger.logMessage("info","The text available on the appeared alert is " + alertText);
            } else{
                logger.logMessage("info","Not able to get the text from the alert as the alert reference is null.");
            }
        } catch (StaleElementReferenceException sere){
            logger.logMessage("info","Alert was dismissed before we fetch the text from it.");
        }
        return this;
    }
    public String getAlertText(){
        return alertText;
    }
    public WebActions provideTextInAlert(String textToBeProvided){
        try{
            if(alert != null){
                alert.sendKeys(textToBeProvided);
                logger.logMessage("info","The text provided in the alert is '" + textToBeProvided + "'.");
            } else {
                logger.logMessage("info","Not able to provide the text in the alert as the alert reference is null.");
            }
        } catch (StaleElementReferenceException sere){
            logger.logMessage("info","Alert was dismissed before we provide the required text in it.");
        }
        return this;
    }
}
