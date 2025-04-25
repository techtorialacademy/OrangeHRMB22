package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BrowserUtils {

    public static String captureScreenShot(WebDriver driver, String scenarioName){

        String timeStamp =
                new SimpleDateFormat("yyyyMMdd-HH:mm:ss").format(new Date());

        String screenShotPath =
                "target/screenshots/" + scenarioName + "-" + timeStamp + ".png";
        File screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenShot, new File(screenShotPath));
        }catch (IOException ioException){
            ioException.fillInStackTrace();
        }

        return screenShotPath;
    }

}
