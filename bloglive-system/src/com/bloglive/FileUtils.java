package com.bloglive;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FileUtils {
    private static final Logger LOGGER = Logger.getLogger(FileUtils.class.getName());
    private static final String DB_URL = "jdbc:h2:~/BlogLive";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void storeFile(String filePath) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(
                 "INSERT INTO Files (filename, file_data) VALUES (?, ?)"
             );
             FileInputStream fis = new FileInputStream(filePath)) {

            pstmt.setString(1, new File(filePath).getName());
            pstmt.setBinaryStream(2, fis);
            pstmt.executeUpdate();
            LOGGER.log(Level.INFO, "File stored successfully: " + filePath);
        } catch (IOException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Error storing file: " + e.getMessage(), e);
        }
    }

    public static void retrieveFile(int fileId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(
                 "SELECT filename, file_data FROM Files WHERE id = ?"
             )) {

            pstmt.setInt(1, fileId);
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String filename = rs.getString("filename");
                    InputStream fileStream = rs.getBinaryStream("file_data");

                    try (FileOutputStream fos = new FileOutputStream("retrieved_" + filename)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = fileStream.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                    LOGGER.log(Level.INFO, "File retrieved successfully: " + fileId);
                }
            }
        } catch (SQLException | IOException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving file: " + e.getMessage(), e);
        }
    }
}
