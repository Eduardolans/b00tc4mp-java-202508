package com.example.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private static Data instance;
    private DatabaseConnection dbConnection;

    public static Data get() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    private Data() {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    /**
     * Add a new user to the database
     */
    public void addUser(User user) {
        String sql = "INSERT INTO usuarios (id, name, username, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getUsername());
            stmt.setString(4, user.getPassword());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error adding user to database", e);
        }
    }

    /**
     * Find user by username
     */
    public User findUserByUsername(String username) {
        String sql = "SELECT id, name, username, password FROM usuarios WHERE username = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by username", e);
        }
    }

    /**
     * Find user by id
     */
    public User findUserById(String id) {
        String sql = "SELECT id, name, username, password FROM usuarios WHERE id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by id", e);
        }
    }

    /**
     * Get all users from database
     */
    public List<User> getAllUsers() {
        String sql = "SELECT id, name, username, password FROM usuarios ORDER BY created_at";
        List<User> users = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("password")
                ));
            }

            return users;

        } catch (SQLException e) {
            throw new RuntimeException("Error getting all users", e);
        }
    }

    /**
     * Reset all data - FOR TESTING PURPOSES ONLY
     * Deletes all users from database
     */
    public void reset() {
        String sql = "DELETE FROM usuarios";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error resetting database", e);
        }
    }
}
