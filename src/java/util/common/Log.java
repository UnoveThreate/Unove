/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.common;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.Handler;

/**
 *
 * @author Per
 */
public class Log {

    private static final Logger LOGGER = Logger.getLogger(Log.class.getName());

    static {
        // Configure the logger to use a ConsoleHandler with a SimpleFormatter
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);  // Log all levels
        consoleHandler.setFormatter(new SimpleFormatter());  // Basic formatter
        LOGGER.addHandler(consoleHandler);
        LOGGER.setLevel(Level.ALL);  // Set the overall log level
        LOGGER.setUseParentHandlers(false);  // Disable parent handlers to avoid duplicate logs
    }

    // Log an informational message
    public static void info(String message) {
        LOGGER.log(Level.INFO, message);
    }

    // Log a warning message
    public static void warning(String message) {
        LOGGER.log(Level.WARNING, message);
    }

    // Log an error message with an exception stack trace
    public static void error(String message, Throwable throwable) {
        LOGGER.log(Level.SEVERE, message, throwable);
    }

    // Log a debug message
    public static void debug(String message) {
        LOGGER.log(Level.FINE, message);
    }

    // Log a custom level message
    public static void log(Level level, String message) {
        LOGGER.log(level, message);
    }
}
