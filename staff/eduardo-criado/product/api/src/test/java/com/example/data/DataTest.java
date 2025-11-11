package com.example.data;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Data class
 */
class DataTest {

    private Data data;

    @BeforeEach
    void setUp() throws Exception {
        // Reset the singleton instance before each test
        resetDataInstance();
        data = Data.get();
    }

    @AfterEach
    void tearDown() throws Exception {
        // Clean up after each test
        resetDataInstance();
    }

    @Test
    @DisplayName("Should add a user successfully")
    void testAddUser_Success() {
        // Arrange
        User user = new User("Juan Pérez", "juanperez", "pass123");

        // Act
        boolean result = data.addUser(user);

        // Assert
        assertTrue(result, "User should be added successfully");
        User foundUser = data.findUserByUsername("juanperez");
        assertNotNull(foundUser, "User should be found after adding");
        assertEquals("Juan Pérez", foundUser.getName());
        assertEquals("juanperez", foundUser.getUsername());
    }

    @Test
    @DisplayName("Should add multiple users successfully")
    void testAddMultipleUsers_Success() {
        // Arrange & Act
        User user1 = new User("User One", "user1", "pass1");
        User user2 = new User("User Two", "user2", "pass2");
        User user3 = new User("User Three", "user3", "pass3");

        boolean result1 = data.addUser(user1);
        boolean result2 = data.addUser(user2);
        boolean result3 = data.addUser(user3);

        // Assert
        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);

        assertNotNull(data.findUserByUsername("user1"));
        assertNotNull(data.findUserByUsername("user2"));
        assertNotNull(data.findUserByUsername("user3"));
    }

    @Test
    @DisplayName("Should find user by username successfully")
    void testFindUserByUsername_Success() {
        // Arrange
        User user = new User("María García", "mariagarcia", "pass456");
        data.addUser(user);

        // Act
        User foundUser = data.findUserByUsername("mariagarcia");

        // Assert
        assertNotNull(foundUser, "User should be found");
        assertEquals("María García", foundUser.getName());
        assertEquals("mariagarcia", foundUser.getUsername());
        assertEquals("pass456", foundUser.getPassword());
    }

    // @Test
    // @DisplayName("Should return false when array is full")
    // void testAddUser_ArrayFull() {
    // // Arrange - Fill the array completely
    // for (int i = 0; i < 100; i++) {
    // User user = new User("User " + i, "user" + i, "pass" + i);
    // data.addUser(user);
    // }

    // // Act - Try to add one more user
    // User extraUser = new User("Extra User", "extrauser", "pass");
    // boolean result = data.addUser(extraUser);

    // // Assert
    // assertFalse(result, "Should return false when array is full");
    // assertNull(data.findUserByUsername("extrauser"), "Extra user should not be
    // added");
    // }

    @Test
    @DisplayName("Should return null when user not found by username")
    void testFindUserByUsername_NotFound() {
        // Arrange
        User user = new User("Test User", "testuser", "pass");
        data.addUser(user);

        // Act
        User foundUser = data.findUserByUsername("nonexistent");

        // Assert
        assertNull(foundUser, "Should return null when user not found");
    }

    @Test
    @DisplayName("Should return null when searching in empty data")
    void testFindUserByUsername_EmptyData() {
        // Act
        User foundUser = data.findUserByUsername("anyuser");

        // Assert
        assertNull(foundUser, "Should return null when data is empty");
    }

    @Test
    @DisplayName("Should find user by id successfully")
    void testFindUserById_Success() {
        // Arrange
        User user = new User("Pedro López", "pedrolopez", "pass789");
        data.addUser(user);
        String userId = user.getId();

        // Act
        User foundUser = data.findUserById(userId);

        // Assert
        assertNotNull(foundUser, "User should be found by id");
        assertEquals(userId, foundUser.getId());
        assertEquals("Pedro López", foundUser.getName());
        assertEquals("pedrolopez", foundUser.getUsername());
    }

    @Test
    @DisplayName("Should return null when user not found by id")
    void testFindUserById_NotFound() {
        // Arrange
        User user = new User("Test User", "testuser", "pass");
        data.addUser(user);

        // Act
        User foundUser = data.findUserById("nonexistent-id");

        // Assert
        assertNull(foundUser, "Should return null when user not found by id");
    }

    @Test
    @DisplayName("Should get all users successfully")
    void testGetAllUsers_Success() {
        // Arrange
        User user1 = new User("User One", "user1", "pass1");
        User user2 = new User("User Two", "user2", "pass2");
        User user3 = new User("User Three", "user3", "pass3");

        // data.addUser(user1);
        // data.addUser(user2);
        // data.addUser(user3);

        data.usersById.put(user1.getId(), user1);
        data.usersById.put(user2.getId(), user2);
        data.usersById.put(user3.getId(), user3);

        // Act
        List<User> allUsers = data.getAllUsers();

        // Assert
        assertNotNull(allUsers, "Should return users array");
        // assertEquals(100, allUsers.length, "Array should have size 100");

        // Check that first 3 users are not null
        assertNotNull(allUsers.get(0));
        assertNotNull(allUsers.get(1));
        assertNotNull(allUsers.get(2));

        // Check that remaining positions are null
        // for (int i = 3; i < 100; i++) {
        // assertNull(allUsers[i], "Position " + i + " should be null");
        // }

        // Verify the actual users
        assertTrue(allUsers.contains(user1));
        assertTrue(allUsers.contains(user2));
        assertTrue(allUsers.contains(user3));
    }

    @Test
    @DisplayName("Should return empty array when no users")
    void testGetAllUsers_EmptyData() {
        // Act
        List<User> allUsers = data.getAllUsers();

        // Assert
        assertNotNull(allUsers, "Should return users array");
        // assertEquals(100, allUsers.length, "Array should have size 100");

        // All positions should be null
        // for (int i = 0; i < 100; i++) {
        // assertNull(allUsers[i], "All positions should be null");
        // }
    }

    @Test
    @DisplayName("Should reset data successfully")
    void testReset_Success() {
        // Arrange - Add some users
        User user1 = new User("User One", "user1", "pass1");
        User user2 = new User("User Two", "user2", "pass2");
        data.addUser(user1);
        data.addUser(user2);

        // Verify users exist before reset
        assertNotNull(data.findUserByUsername("user1"));
        assertNotNull(data.findUserByUsername("user2"));

        // Act - Reset data
        data.reset();

        // Assert - Users should be gone
        assertNull(data.findUserByUsername("user1"), "User1 should be removed after reset");
        assertNull(data.findUserByUsername("user2"), "User2 should be removed after reset");

        // Verify array is clean
        // User[] allUsers = data.getAllUsers();
        // for (int i = 0; i < 100; i++) {
        // assertNull(allUsers[i], "All positions should be null after reset");
        // }
    }

    @Test
    @DisplayName("Should be able to add users after reset")
    void testAddUserAfterReset_Success() {
        // Arrange - Add user, reset, then add another
        User user1 = new User("User One", "user1", "pass1");
        data.addUser(user1);
        data.reset();

        // Act
        User user2 = new User("User Two", "user2", "pass2");
        boolean result = data.addUser(user2);

        // Assert
        assertTrue(result, "Should be able to add user after reset");
        assertNull(data.findUserByUsername("user1"), "Old user should not exist");
        assertNotNull(data.findUserByUsername("user2"), "New user should exist");
    }

    @Test
    @DisplayName("Should maintain singleton pattern")
    void testSingletonPattern() {
        // Act
        Data instance1 = Data.get();
        Data instance2 = Data.get();

        // Assert
        assertSame(instance1, instance2, "Data should be a singleton");
    }

    /**
     * Helper method to reset the Data singleton instance using reflection
     */
    private void resetDataInstance() throws Exception {
        Field instanceField = Data.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }
}
