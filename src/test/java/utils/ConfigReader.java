package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    // reading data from configuration.properties file
    public static String readProperty(String key){
        File configFile = new File("configuration.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(configFile));
        }catch (IOException e){
            e.fillInStackTrace(); // This method just prints out the error message nicely without
            // stopping the code execution
        }
        return properties.getProperty(key);
    }

}
