package org.example.pa_project.Logs;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This class handles logging operations for the application.
 */
public class LoggerInfo {
    private final Logger logger;

    public LoggerInfo() {
        this.logger = Logger.getLogger("LoggerInfo");

        try {
            FileHandler fileHandler = new FileHandler("src/main/java/org/example/pa_project/Logs/application.log");
            fileHandler.setLevel(Level.ALL);
            this.logger.addHandler(fileHandler);
        } catch (Exception e) {
            this.logger.log(Level.SEVERE, "Error while configuring file logging", e);
        }
    }

    /**
     * Logs information about an exception.
     *
     * @param e The exception to be logged.
     */
    public void logException(Exception e) {
        this.logger.log(Level.SEVERE, "Exception: " + e.getMessage());
    }

    /**
     * Logs information about the execution time of a program.
     *
     * @param startTime The time when the program started.
     * @param endTime The time when the program ended.
     */
    public void logExecutionTime(long startTime, long endTime) {
        long executionTime = endTime - startTime;
        this.logger.info("Execution time for statements: " + executionTime + " milliseconds");
    }

    public void logDatabaseAccess(String message) {
        this.logger.info("Database access: " + message);
    }
}
