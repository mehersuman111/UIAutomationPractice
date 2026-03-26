package dataproviders;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

import java.util.Random;

public class UserCredentialDataProvider {

    @DataProvider(name = "ValidUserCredentials")
    public Object[][] validUserCredentials() {

        Object[][] credentials = {
                {"figina7483@bigonla.com", "Suman@123", "Valid"}
        };
        return credentials;
    }
    @DataProvider(name = "InvalidUserCredentials")
    public Object[][] invalidUserCredentials() {

        Object[][] credentials = {
                {"figina7483@bigonla.com", "dkjnawenujaiwen", "Invalid"},
                {"aduhbadsbj@test.com", "Suman@123", "Invalid"},
                {"bnschndeui@test.com", "idbbduda", "Invalid"}
        };
        return credentials;
    }
}
