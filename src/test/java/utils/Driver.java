package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class Driver {

    // Singleton Design Pattern ?
    // Using one object for the all framework.
    // We are going to create a private constructor so no object could be created from
    // this class. And we will only assign the driver if it is empty or not usable.

     private static WebDriver driver;

     public static WebDriver getDriver(){

          if(driver == null ||((RemoteWebDriver)driver).getSessionId() == null) {
               switch (ConfigReader.readProperty("browser")) {
                    case "chrome":
                         ChromeOptions options = new ChromeOptions();
//                         options.addArguments("--headless");
//                         options.addArguments("--disable-dev-shm-usage");
//                         options.addArguments("--disable-gpu");
                         options.addArguments("--window-size=1920,1080");
                         driver = new ChromeDriver(options);
                         LoggerUtils.info("The driver setup is complete with chrome driver.");
                         break;
                    case "firefox":
                         FirefoxOptions options1 = new FirefoxOptions();
                         options1.addArguments("--headless");
                         options1.addArguments("--disable-dev-shm-usage");
                         options1.addArguments("--disable-gpu");
                         options1.addArguments("--window-size=1920,1080");
                         driver = new FirefoxDriver(options1);
                         LoggerUtils.info("The driver setup is complete with firefox driver.");
                         break;
                    case "safari":
                         driver = new SafariDriver();
                         LoggerUtils.warn("The driver setup is complete with safari driver. MACOS ONLY.");
                         break;
                    default:
                         // since there is no break statement in this case it will
                         // go to next case which would be chrome.
               }
          }
          driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

          return driver;

     }
}
