package com.bloglive;

public class AuthManager {
    public static boolean authenticateUser(String username, String password) {
        // 替换为实际的用户验证逻辑
        // 示例：验证用户名为 "admin" 且密码为 "password"
        return "admin".equals(username) && "password".equals(password);
    }
}
