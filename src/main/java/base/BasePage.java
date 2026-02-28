package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected WebDriver driver;                           // Accessible to all Child page classes
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);     // Initialize @FindBy elements in the child classes
    }
}