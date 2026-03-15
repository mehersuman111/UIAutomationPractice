package services.authentication;

import org.openqa.selenium.WebDriver;

import pages.entry.AuthenticationPage;
import pages.entry.LandingPage;

public class LoginService {

	LandingPage landingPage;
	AuthenticationPage authenticationPage;

	public LoginService(WebDriver driver) {
		landingPage = new LandingPage(driver);
		authenticationPage = new AuthenticationPage(driver);
	}
	
	public LoginService userLogin(String userName, String password) {
		landingPage.clickOnSignInButton().provideEmailAddress(userName).providePassword(password).clickSigninButton();
		return this;
	}
	public LoginService isLogOutButtonDisplayed() {
		landingPage.
		return this;
	}
}
