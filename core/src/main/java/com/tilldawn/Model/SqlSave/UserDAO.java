package com.tilldawn.Model.SqlSave;

import com.tilldawn.Model.PlayerTypes;
import com.tilldawn.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String DB_URL = "jdbc:sqlite:users.db";  // <<=== HERE

    public UserDAO() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL, " +
                "securityAnswer TEXT, " +
                "score INTEGER DEFAULT 0, " +
                "kills INTEGER DEFAULT 0, " +
                "timeSurvived INTEGER DEFAULT 0)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(User user) {
        System.out.println("⏳ Attempting to save to database: " + user.getUsername());

        String sql = "INSERT OR REPLACE INTO users(username, password, securityAnswer, score, kills, timeSurvived) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getAnswerOfSecurity());
            pstmt.setInt(4, user.getScore());
            pstmt.setInt(5, user.getNumberOfKills());
            pstmt.setInt(6, user.getMostTimeSurvived());

            pstmt.executeUpdate();
            System.out.println("✅ SQL Save Successful!");

        } catch (SQLException e) {
            System.err.println("❌ SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
