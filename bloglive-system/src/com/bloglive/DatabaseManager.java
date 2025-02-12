package com.bloglive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());
    private static final String DB_URL = "jdbc:h2:~/BlogLive";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void initializeDatabase() {
        String sqlCreateTables =
            "CREATE TABLE IF NOT EXISTS Files (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "filename VARCHAR(255) NOT NULL," +
                "file_data BLOB" +
            ");" +

            "CREATE TABLE IF NOT EXISTS Articles (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "title VARCHAR(255) NOT NULL," +
                "content TEXT NOT NULL," +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "updated_at DATETIME," +
                "views INT DEFAULT 0," +
                "file_id INT," +
                "FOREIGN KEY (file_id) REFERENCES Files(id)" +
            ");" +

            "CREATE TABLE IF NOT EXISTS Users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) NOT NULL UNIQUE," +
                "email VARCHAR(100) NOT NULL UNIQUE," +
                "password VARCHAR(100) NOT NULL," +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP" +
            ");" +

            "CREATE TABLE IF NOT EXISTS TrafficLogs (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "user_id INT," +
                "ip_address VARCHAR(50)," +
                "access_time DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "source VARCHAR(255)," +
                "target VARCHAR(255)," +
                "FOREIGN KEY (user_id) REFERENCES Users(id)" +
            ");";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.execute("SET LOCK_MODE 3");
            stmt.execute(sqlCreateTables);
            LOGGER.log(Level.INFO, "Database initialized successfully!");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error initializing database: " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
    }
}
