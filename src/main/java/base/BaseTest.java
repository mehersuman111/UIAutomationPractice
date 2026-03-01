package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseTest {

    static {
        System.setProperty("log4j2.configurationFile","src/main/resources/config/logging/log4j2.xml");
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser){
        DriverFactory.initDriver(browser);
    }

    public WebDriver getDriver(){
        return DriverFactory.getDriver();
    }

    @AfterMethod
    public void tearDown(){
        DriverFactory.quitDriver();
    }
}
