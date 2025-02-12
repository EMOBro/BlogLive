package com.bloglive;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogManager {
    private static final String LOG_FILE = "bloglive.log";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void log(String message) {
        String logEntry = "[" + dateFormat.format(new Date()) + "] " + message + "\n";
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(logEntry);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
