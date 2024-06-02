package org.example.pa_project.Logs;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * Put in the logger the information about an exception
     */
    public void logException(Exception e) {
        this.logger.log(Level.SEVERE, "Exception: " + e.getMessage());
    }

    /**
     * Put in the logger the information about the elapsed time
     *
     * @param startTime - when program start
     * @param endTime   - when program stop
     */
    public void logExecutionTime(long startTime, long endTime) {
        long executionTime = endTime - startTime;
        this.logger.info("Execution time for statements: " + executionTime + " milliseconds");
    }

    public void logDatabaseAccess(String message) {
        this.logger.info("Database access: " + message);
    }
}
