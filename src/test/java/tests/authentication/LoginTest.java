package tests.authentication;

import org.testng.annotations.Test;

import base.BaseTest;
import models.Credential;
import pages.entry.AuthenticationPage;
import services.authentication.LoginService;
import types.TestType;

public class LoginTest extends BaseTest {

    @Test(groups = {"Smoke"}, dataProvider = "ValidUserCredentials", dataProviderClass = dataproviders.UserCredentialDataProvider.class)
    public void verifyLoginWithValidCredentials(String username, String password, String isValid) {
        LoginService loginService = new LoginService(getDriver());
        loginService.userLogin(username, password);
        if (isValid.equals("Valid")) {
            loginService.checkLogOutButton();
        }
    }

    @Test(groups = {"Regression"}, dataProvider = "InvalidUserCredentials", dataProviderClass = dataproviders.UserCredentialDataProvider.class)
    public void verifyLoginWithInvalidCredentials(String username, String password, String isValid) {
        LoginService loginService = new LoginService(getDriver());
        loginService.userLogin(username, password);
        if (isValid.equals("Invalid")) {
            loginService.checkLogInAlertAppeared();
        }
    }
}