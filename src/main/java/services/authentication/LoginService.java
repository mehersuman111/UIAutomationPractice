package services.authentication;

import org.openqa.selenium.WebDriver;

import pages.entry.AuthenticationPage;
import pages.entry.HomePage;
import pages.entry.LandingPage;

public class LoginService {

	LandingPage landingPage;
	AuthenticationPage authenticationPage;
	HomePage homePage;

	public LoginService(WebDriver driver) {
		landingPage = new LandingPage(driver);
		homePage = new HomePage(driver);
		authenticationPage = new AuthenticationPage(driver);
	}

	public LoginService userLogin(String userName, String password) {
		landingPage.clickOnSignInButton().provideEmailAddress(userName).providePassword	(password).clickSigninButton();
		return this;
	}

	public LoginService checkLogOutButton() {
		homePage.chechLogoutButtonAppeared(true);
		return this;
	}

    public LoginService checkLogInAlertAppeared() {
        authenticationPage.checkLoginAlert(true);
        return this;
    }
}
