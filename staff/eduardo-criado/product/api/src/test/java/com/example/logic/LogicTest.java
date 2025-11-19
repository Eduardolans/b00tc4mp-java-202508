package com.example.logic;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.data.Data;
import com.example.data.User;
import com.example.errors.CredentialsException;
import com.example.errors.DuplicityException;
import com.example.errors.NotFoundException;

/**
 * Unit tests for the Logic class
 */
class LogicTest {

    private Logic logic;

    @BeforeEach
    void setUp() throws Exception {
        // Reset singleton instances before each test
        resetLogicInstance();
        resetDataInstance();
        logic = Logic.get();
    }

    @AfterEach
    void tearDown() throws Exception {
        // Clean up after each test
        resetLogicInstance();
        resetDataInstance();
    }

    @Test
    @DisplayName("Should register a user successfully")
    void testRegisterUser_Success() {
        String name = "Juan Pérez";
        String username = "juanperez";
        String password = "pass123";
        // Act & Assert - Should not throw exception
        assertDoesNotThrow(() -> {
            logic.registerUser(name, username, password);
        }, "Should register user without throwing exception");

        // Verify user was added to data
        Data data = Data.get();
        User user = data.findUserByUsername("juanperez");
        assertNotNull(user, "User should exist in data");
        assertEquals(name, user.getName());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }

    // ==================== registerUser tests ====================

    @Test
    @DisplayName("Should throw DuplicityException when registering duplicate user")
    void testRegisterUser_DuplicateUsername() throws DuplicityException {
        String name = "Original User";
        String username = "duplicate";
        String password = "pass1";

        String name2 = "Another User";
        String username2 = "duplicate";
        String password2 = "pass2";

        // Arrange

        logic.registerUser(name, username, password);

        // Act & Assert
        DuplicityException exception = assertThrows(
                DuplicityException.class,
                () -> logic.registerUser(name2, username2, password2),
                "Should throw DuplicityException for duplicate username");

        assertEquals("user already exists", exception.getMessage());
    }

    @Test
    @DisplayName("Should authenticate user successfully and return userId")
    void testAuthenticateUser_Success() throws CredentialsException, NotFoundException {
        // Arrange
        User user = new User("María García", "mariagarcia", "pass456");
        Data data = Data.get();
        data.addUser(user);

        // Act
        String userId = logic.authenticateUser(user.getUsername(), user.getPassword());

        // Assert
        assertEquals(user.getId(), userId, "Should return correct userId");
    }

    @Test
    @DisplayName("Should throw CredentialsException when user not found")
    void testAuthenticateUser_UserNotFound() {
        // Act & Assert
        CredentialsException exception = assertThrows(
                CredentialsException.class,
                () -> logic.authenticateUser("nonexistent", "pass123"),
                "Should throw CredentialsException when user not found");

        assertEquals("invalid credentials", exception.getMessage());
    }

    // ==================== authenticateUser tests ====================

    @Test
    @DisplayName("Should throw CredentialsException when password is incorrect")
    void testAuthenticateUser_WrongPassword() {
        // Arrange
        User user = new User("Pedro López", "pedrolopez", "correctpass");
        Data data = Data.get();
        data.addUser(user);

        // Act & Assert
        CredentialsException exception = assertThrows(
                CredentialsException.class,
                () -> logic.authenticateUser(user.getUsername(), "wrongpass"),
                "Should throw CredentialsException when password is incorrect");

        assertEquals("invalid credentials", exception.getMessage());
    }

    // ==================== getUserByUsername tests ====================

    @Test
    @DisplayName("Should get user info by userId successfully")
    void testGetUserInfo_Success() throws NotFoundException {
        // Arrange
        User user = new User("Carlos Ruiz", "carlosruiz", "pass321");
        Data data = Data.get();
        data.addUser(user);
        String userId = user.getId();

        // Act
        UserTO foundUser = logic.getUserInfo(userId);

        // Assert
        assertNotNull(foundUser, "User should not be null");
        assertEquals(userId, foundUser.getId());
        assertEquals(user.getName(), foundUser.getName());
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user not found by userId")
    void testGetUserInfo_NotFound() {
        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> logic.getUserInfo("nonexistent-id"),
                "Should throw NotFoundException when user not found by id");

        assertEquals("user not found", exception.getMessage());
    }

    // ==================== getUserInfo tests ====================

    @Test
    @DisplayName("Should get all users successfully with valid userId")
    void testGetAllUsers_Success() throws NotFoundException {
        // Arrange
        User user1 = new User("User One", "user1", "pass1");
        User user2 = new User("User Two", "user2", "pass2");
        User user3 = new User("User Three", "user3", "pass3");

        Data data = Data.get();
        data.addUser(user1);
        data.addUser(user2);
        data.addUser(user3);

        String userId = user1.getId();

        // Act
        List<User> allUsers = logic.getAllUsers(userId);

        // Assert
        assertNotNull(allUsers, "Users array should not be null");

        assertEquals(3, allUsers.size(), "Should have 3 users");

        // Verify users are in the list
        assertTrue(allUsers.contains(user1));
        assertTrue(allUsers.contains(user2));
        assertTrue(allUsers.contains(user3));
    }

    @Test
    @DisplayName("Should throw NotFoundException when userId is invalid in getAllUsers")
    void testGetAllUsers_InvalidUserId() {
        // Arrange
        User user = new User("User One", "user1", "pass1");
        Data data = Data.get();
        data.addUser(user);

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> logic.getAllUsers("invalid-user-id"),
                "Should throw NotFoundException when userId is invalid");

        assertEquals("user not found", exception.getMessage());
    }

    // ==================== getAllUsers tests ====================

    @Test
    @DisplayName("Should maintain singleton pattern")
    void testSingletonPattern() {
        // Act
        Logic instance1 = Logic.get();
        Logic instance2 = Logic.get();

        // Assert
        assertSame(instance1, instance2, "Logic should be a singleton");
    }

    /**
     * Helper method to reset the Logic singleton instance using reflection
     */
    private void resetLogicInstance() throws Exception {
        Field instanceField = Logic.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
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
