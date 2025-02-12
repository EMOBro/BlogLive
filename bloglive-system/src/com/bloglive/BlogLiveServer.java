package com.bloglive;

import spark.Spark;

public class BlogLiveServer {
    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();

        // 应用程序路由
        Spark.get("/", (req, res) -> {
            LogManager.log("Root endpoint accessed");
            return "BlogLive Server is running!";
        });

        Spark.post("/upload", (req, res) -> {
            String filePath = req.body();
            FileUtils.storeFile(filePath);
            LogManager.log("File uploaded: " + filePath);
            return "File uploaded successfully!";
        });

        Spark.get("/file/:id", (req, res) -> {
            int fileId = Integer.parseInt(req.params(":id"));
            FileUtils.retrieveFile(fileId);
            LogManager.log("File retrieved: " + fileId);
            return "File retrieved successfully!";
        });

        Spark.get("/users/:username", (req, res) -> {
            String username = req.params(":username");
            LogManager.log("User endpoint accessed: " + username);
            // 替换为实际的用户查询逻辑
            return "User: " + username;
        });

        // 启动服务器
        Spark.awaitInitialization();
        LogManager.log("BlogLive Server started on port " + Spark.port());
    }
}
