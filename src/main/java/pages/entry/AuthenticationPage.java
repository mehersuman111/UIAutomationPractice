package pages.entry;

import base.BasePage;
import base.WebActions;
import types.ElementType;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthenticationPage extends WebActions {

    @FindBy(id="create-account_form")
    private WebElement createAccountForm;
    @FindBy(xpath = "//form[@id='create-account_form']//h3")
    private WebElement createAccountFormHeader;
    @FindBy(xpath = "//form[@id='create-account_form']//p")
    private WebElement createAccountFormText;
    @FindBy(xpath = "(//div[@class='form-group'])[1]//label")
    private WebElement createAccountFormEmailLabel;
    @FindBy(xpath = "(//div[@class='form-group'])[1]//input")
    private WebElement createAccountFormEmailField;
    @FindBy(id = "SubmitCreate")
    private WebElement createAccountButton;
    @FindBy(id="login_form")
    private WebElement loginForm;
    @FindBy(xpath = "//form[@id='login_form']//h3")
    private WebElement signInFormHeader;
    @FindBy(xpath = "(//div[@class='form-group'])[2]//label")
    private WebElement signInFormEmailLabel;
    @FindBy(xpath = "(//div[@class='form-group'])[2]//input")
    private WebElement SignInFormEmailFiled;
    @FindBy(xpath = "(//div[@class='form-group'])[3]//label")
    private WebElement signInFormPasswordLabel;
    @FindBy(xpath = "//div[@class='form-group'])[3]//input")
    private WebElement signInFormPasswordField;
    @FindBy(linkText = "Forgot your password?")
    private WebElement forgotPasswordLink;
    @FindBy(id="SubmitLogin")
    private WebElement signInButton;
    @FindBy(id="email")
    private WebElement emailField;
    @FindBy(id="passwd")
    private WebElement passwordField;

    public AuthenticationPage(WebDriver driver){
        super(driver);
    }
    
    public AuthenticationPage provideEmailAddress(String emailAddr) {
    	sendKeys(emailField, "Email address", ElementType.TEXT_FIELD, emailAddr);
    	return this;
    }
    public AuthenticationPage providePassword(String password) {
    	sendKeys(passwordField, "Password", ElementType.TEXT_FIELD, password);
    	return this;
    }
    public AuthenticationPage clickSigninButton() {
    	clickOn(signInButton, "Signin", ElementType.BUTTON);
    	return this;
    }
    public PasswordResetPage clickForgotPassword(){
        return new PasswordResetPage(driver);
    }

}
