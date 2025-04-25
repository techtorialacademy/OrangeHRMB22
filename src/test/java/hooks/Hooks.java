package hooks;


import io.cucumber.java.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.BrowserUtils;
import utils.Driver;
import utils.LoggerUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Hooks {
   public static WebDriver driver ;
   @Before
   public void setDriver() {
       LoggerUtils.info("Instantiating driver.");

      driver = Driver.getDriver();

      LoggerUtils.info(((RemoteWebDriver)driver).getSessionId().toString());
   }

   @After
    public void tearDown(Scenario scenario){
       if(scenario.isFailed()){
           LoggerUtils.error("Test failed! Capturing the screenshot...");
           String screenShotPath =
                   BrowserUtils.captureScreenShot(driver,scenario.getName());
           try {
               byte[] screenShotBytes =
                       Files.readAllBytes(new File(screenShotPath).toPath());
               scenario.attach(screenShotBytes,"image/png","Screenshot on failure");
           }catch (IOException e){
               e.fillInStackTrace();
               LoggerUtils.error("Screenshot has failed!!!");
           }


       }

       driver.manage().deleteAllCookies();


   }



}
