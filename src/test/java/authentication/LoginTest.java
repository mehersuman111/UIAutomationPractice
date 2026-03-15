package authentication;

import org.testng.annotations.Test;

import base.BaseTest;
import data.models.Credential;
import data.providers.CredentialsProvider;
import pages.entry.AuthenticationPage;
import services.authentication.LoginService;

public class LoginTest extends BaseTest{
	
	AuthenticationPage authenticationPage = new AuthenticationPage(getDriver());
	
	@Test
	public void verifyLogin() {
		Credential admin = CredentialsProvider.getCredentialsByRole("ROLE_ADMIN");
		new LoginService(getDriver())
		.userLogin(admin.getUsername(), admin.getPassword());
	}
}
