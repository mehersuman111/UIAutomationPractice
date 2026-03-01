package pages.entry;

import base.WebActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PasswordResetPage extends WebActions {

    @FindBy(xpath = "//div[@class='box']//h1")
    private WebElement passwordResetFormHeader;
    @FindBy(xpath = "//div[@class='box']//p[starts-with(text(),'Please')]")
    private WebElement passwordResetFormText;
    @FindBy(xpath = "//form[@id='form_forgotpassword']//label")
    private WebElement passwordResetFormEmailLabel;
    @FindBy(xpath = "//form[@id='form_forgotpassword']//input")
    private WebElement passwordResetFormEmailField;
    @FindBy(xpath = "//span[text()='Retrieve Password']//ancestor::button")
    private WebElement retrievePasswordButton;
    @FindBy(xpath = "//a[@title='Back to Login']")
    private WebElement backToLoginPageButton;

    public PasswordResetPage(WebDriver driver){
        super(driver);
    }
}
