package base;

import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {
    private static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    public static void initDriver(String browser){
        String os = System.getProperty("os.name").toLowerCase();
        switch(browser.toLowerCase()) {
            case "chrome":
            	WebDriverManager.chromedriver().setup();
                threadDriver.set(new ChromeDriver());
                break;
            case "edge":
            	//WebDriverManager.edgedriver().setup();
            	System.setProperty("webdriver.edge.driver", ".\\src\\test\\resources\\drivers\\msedgedriver.exe");
                threadDriver.set(new EdgeDriver());
                break;
            case  "firefox":
            	WebDriverManager.firefoxdriver().setup();
                threadDriver.set(new FirefoxDriver());
                break;
            case "ie":
            	WebDriverManager.iedriver().setup();
                threadDriver.set(new InternetExplorerDriver());
                break;
            case "safari":
                if(os.equals("mac")){
                	WebDriverManager.safaridriver().setup();
                    threadDriver.set(new SafariDriver());
                } else {
                    throw new IllegalArgumentException("Safari is only supported on Mac!");
                }
                break;
            default:
                throw new IllegalArgumentException("Browser not supported : " + browser);
        }
    }

    public static WebDriver getDriver(){
        return threadDriver.get();
    }

    public static void quitDriver(){
        if(threadDriver.get() != null){
            threadDriver.get().quit();
            threadDriver.remove();
        }
    }
}