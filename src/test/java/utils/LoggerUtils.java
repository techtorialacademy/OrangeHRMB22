package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtils {
    private static final Logger logger = LogManager.getLogger(LoggerUtils.class);

    // Method to log INFO messages
    public static void info(String message) {
        logger.info(message);
    }

    // Method to log DEBUG messages
    public static void debug(String message) {
        logger.debug(message);
    }

    // Method to log WARN messages
    public static void warn(String message) {
        logger.warn(message);
    }

    // Method to log ERROR messages
    public static void error(String message) {
        logger.error(message);
    }
}
