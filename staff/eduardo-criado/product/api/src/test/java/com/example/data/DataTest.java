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
        data.addUser(user);

        // Assert
        assertEquals(user, data.usersByUsername.get(user.getUsername()));
        assertEquals(user, data.usersById.get(user.getId()));
    }

    @Test
    @DisplayName("Should find user by username successfully")
    void testFindUserByUsername_Success() {
        // Arrange
        User user = new User("María García", "mariagarcia", "pass456");
        data.usersByUsername.put(user.getUsername(), user);

        // Act
        User foundUser = data.findUserByUsername(user.getUsername());

        // Assert
        assertNotNull(foundUser, "User should be found");
        assertEquals(user.getName(), foundUser.getName());
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getPassword(), foundUser.getPassword());
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    @DisplayName("Should return null when searching in empty data")
    void testFindUserByUsername_forNonExistingUser() {
        // Act
        User foundUser = data.findUserByUsername("anyuser");

        // Assert
        assertNull(foundUser, "Should return null when user doesn't exist");
    }

    @Test
    @DisplayName("Should find user by id successfully")
    void testFindUserById_Success() {
        // Arrange
        User user = new User("Pedro López", "pedrolopez", "pass789");
        data.usersById.put(user.getId(), user);

        // Act
        User foundUser = data.findUserById(user.getId());

        // Assert
        assertNotNull(foundUser, "User should be found by id");
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getName(), foundUser.getName());
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getPassword(), foundUser.getPassword());
    }

    @Test
    @DisplayName("Should return null when user not found by id")
    void testFindUserById_forNonExistingUser() {
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

        data.usersById.put(user1.getId(), user1);
        data.usersById.put(user2.getId(), user2);
        data.usersById.put(user3.getId(), user3);

        // Act
        List<User> allUsers = data.getAllUsers();

        // Assert
        assertNotNull(allUsers, "Should return users array");

        // Check that first 3 users are not null
        assertNotNull(allUsers.get(0));
        assertNotNull(allUsers.get(1));
        assertNotNull(allUsers.get(2));

        // Verify the actual users
        assertTrue(allUsers.contains(user1));
        assertTrue(allUsers.contains(user2));
        assertTrue(allUsers.contains(user3));

        assertEquals(3, allUsers.size(), "Should have 3 users");
    }

    @Test
    @DisplayName("Should return empty array when no users")
    void testGetAllUsers_EmptyData() {
        // Act
        List<User> allUsers = data.getAllUsers();

        // Assert
        assertNotNull(allUsers, "Should return users array");
    }

    @Test
    @DisplayName("Should reset data successfully")
    void testReset_Success() {
        // Arrange - Add some users
        User user1 = new User("User One", "user1", "pass1");
        User user2 = new User("User Two", "user2", "pass2");
        data.usersByUsername.put(user1.getUsername(), user1);
        data.usersByUsername.put(user2.getUsername(), user2);
        data.usersById.put(user1.getId(), user1);
        data.usersById.put(user2.getId(), user2);

        // Act - Reset data
        data.reset();

        // Assert - Users should be gone
        assertNull(data.usersByUsername.get("user1"), "User1 should be removed after reset");
        assertNull(data.usersByUsername.get("user2"), "User2 should be removed after reset");
        assertNull(data.usersById.get(user1.getId()), "User1 should be removed from usersById");
        assertNull(data.usersById.get(user2.getId()), "User2 should be removed from usersById");
        assertTrue(data.usersByUsername.isEmpty(), "usersByUsername should be empty");
        assertTrue(data.usersById.isEmpty(), "usersById should be empty");
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
