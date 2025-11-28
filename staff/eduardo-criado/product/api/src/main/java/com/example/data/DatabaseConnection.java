package com.example.data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Manages database connections using JDBC.
 * Implements Singleton pattern to ensure single instance.
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private String url;
    private String username;
    private String password;
    private String driver;

    private DatabaseConnection() {
        loadProperties();
    }

    /**
     * Get singleton instance of DatabaseConnection
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Load database configuration from db.properties file
     */
    private void loadProperties() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new RuntimeException("Unable to find db.properties");
            }

            props.load(input);

            this.url = props.getProperty("db.url");
            this.username = props.getProperty("db.username");
            this.password = props.getProperty("db.password");
            this.driver = props.getProperty("db.driver");

            // Load PostgreSQL driver
            Class.forName(this.driver);

        } catch (IOException e) {
            throw new RuntimeException("Error loading database properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found", e);
        }
    }

    /**
     * Get a new database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Close a database connection
     * @param connection Connection to close
     */
    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Test database connection
     * @return true if connection is successful
     */
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Connection test failed: " + e.getMessage());
            return false;
        }
    }
}
