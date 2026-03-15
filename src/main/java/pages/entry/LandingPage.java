package pages.entry;

import base.BasePage;
import base.WebActions;
import types.ElementType;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LandingPage extends WebActions {

    @FindBy(id="header_logo")
    private WebElement logo;
    @FindBy(xpath = "//div[@id='contact-link']//a[@title='Contact us']")
    private WebElement contactUs;
    @FindBy(xpath = "//a[normalize-space()='Sign in']")
    private WebElement signIn;
    @FindBy(id="search_query_top")
    private WebElement searchBox;
    @FindBy(name = "submit_search")
    private WebElement submitSearch;
    @FindBy(css = "div.shopping_cart")
    private WebElement shoppingCart;
    @FindBy(linkText = "Women")
    private WebElement womenCategory;
    @FindBy(linkText = "Dresses")
    private WebElement dresses;
    @FindBy(linkText = "T-shirts")
    private WebElement tShirts;

    public LandingPage(WebDriver driver){
        super(driver);
    }

    public void clickOnContactUs(){
        contactUs.click();
    }
    public AuthenticationPage clickOnSignInButton(){
    	clickOn(signIn, "Signin", ElementType.BUTTON);
        return new AuthenticationPage(driver);
    }
    public AuthenticationPage chechLogoutButtonAppeared(){
    	;
        return new AuthenticationPage(driver);
    }
}