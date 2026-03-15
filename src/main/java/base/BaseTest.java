package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import core.logging.LoggerManager;

public class BaseTest {

    /*static {
        System.out.println("=== LOG4J2 DEBUG ===");
        System.out.println("Config path: " + System.getProperty("log4j2.configurationFile"));
        System.out.println("File exists: " + new java.io.File(
            System.getProperty("log4j2.configurationFile")).exists());
        System.out.println("===================");
    }*/
	
	SoftAssert softAssert = new SoftAssert();
	private LoggerManager logger = new LoggerManager(this.getClass());
	
    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser){
        DriverFactory.initDriver(browser);
        DriverFactory.getDriver().get("https://automationpractice.techwithjatin.com/");
        DriverFactory.getDriver().manage().window().maximize();
    }

    public WebDriver getDriver(){
        return DriverFactory.getDriver();
    }

    public void softAssertEquals(Object actual, Object expected, String message) {
		softAssert.assertEquals(actual, expected, message);
		logger.logMessage("info", "Soft assert equals — expected: '%s', actual: '%s'.".formatted(expected, actual));
	}

	public void softAssertTrue(boolean condition, String message) {
		softAssert.assertTrue(condition, message);
		logger.logMessage("info", "Soft assert true — condition evaluated to: " + condition);
	}

	public void softAssertFalse(boolean condition, String message) {
		softAssert.assertFalse(condition, message);
		logger.logMessage("info", "Soft assert false — condition evaluated to: " + condition);
	}


	public void assertAll() {
	    try {
	        softAssert.assertAll();
	        logger.logMessage("info", "All soft assertions passed successfully.");
	    } catch (AssertionError ae) {
	        logger.logMessage("error", "One or more soft assertions failed. " + ae.getMessage());
	        throw ae; // re-throw so TestNG still marks test as FAILED
	    }
	}
	public void resetSoftAssert() {
	    softAssert = new SoftAssert();
	}
	
    @AfterMethod
    public void tearDown(){
        DriverFactory.quitDriver();
    }
}
