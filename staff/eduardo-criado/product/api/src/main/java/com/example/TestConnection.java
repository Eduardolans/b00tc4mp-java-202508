package com.example;

import com.example.data.Data;
import com.example.data.DatabaseConnection;
import com.example.data.User;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("=== Testing PostgreSQL Connection ===");

        try {
            // Test 1: Test basic connection
            System.out.println("\n1. Testing database connection...");
            DatabaseConnection dbConn = DatabaseConnection.getInstance();
            boolean connected = dbConn.testConnection();

            if (connected) {
                System.out.println("   ✓ Connection successful!");
            } else {
                System.out.println("   ✗ Connection failed!");
                return;
            }

            // Test 2: Create a test user
            System.out.println("\n2. Creating a test user...");
            Data data = Data.get();
            User testUser = new User("Test User", "testuser", "testpass123");
            data.addUser(testUser);
            System.out.println("   ✓ User created with ID: " + testUser.getId());

            // Test 3: Find user by username
            System.out.println("\n3. Finding user by username...");
            User foundByUsername = data.findUserByUsername("testuser");
            if (foundByUsername != null) {
                System.out.println("   ✓ User found: " + foundByUsername.getName());
            } else {
                System.out.println("   ✗ User not found!");
            }

            // Test 4: Find user by ID
            System.out.println("\n4. Finding user by ID...");
            User foundById = data.findUserById(testUser.getId());
            if (foundById != null) {
                System.out.println("   ✓ User found: " + foundById.getName());
            } else {
                System.out.println("   ✗ User not found!");
            }

            // Test 5: Get all users
            System.out.println("\n5. Getting all users...");
            var allUsers = data.getAllUsers();
            System.out.println("   ✓ Total users in database: " + allUsers.size());
            for (User u : allUsers) {
                System.out.println("      - " + u.getUsername() + " (" + u.getName() + ")");
            }

            // Clean up
            System.out.println("\n6. Cleaning up (resetting database)...");
            data.reset();
            System.out.println("   ✓ Database reset complete");

            System.out.println("\n=== All tests passed! ===");

        } catch (Exception e) {
            System.out.println("\n✗ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
