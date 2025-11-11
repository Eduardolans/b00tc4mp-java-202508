package com.example.logic;

import com.example.data.Data;
import com.example.data.User;
import com.example.errors.CredentialsException;
import com.example.errors.DuplicityException;
import com.example.errors.NotFoundException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

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

    // ==================== registerUser tests ====================

    @Test
    @DisplayName("Should register a user successfully")
    void testRegisterUser_Success() {
        // Act & Assert - Should not throw exception
        assertDoesNotThrow(() -> {
            logic.registerUser("Juan Pérez", "juanperez", "pass123");
        }, "Should register user without throwing exception");

        // Verify user was added to data
        Data data = Data.get();
        User user = data.findUserByUsername("juanperez");
        assertNotNull(user, "User should exist in data");
        assertEquals("Juan Pérez", user.getName());
        assertEquals("juanperez", user.getUsername());
        assertEquals("pass123", user.getPassword());
    }

    @Test
    @DisplayName("Should register multiple users successfully")
    void testRegisterMultipleUsers_Success() {
        // Act & Assert
        assertDoesNotThrow(() -> {
            logic.registerUser("User One", "user1", "pass1");
            logic.registerUser("User Two", "user2", "pass2");
            logic.registerUser("User Three", "user3", "pass3");
        });

        // Verify all users exist
        Data data = Data.get();
        assertNotNull(data.findUserByUsername("user1"));
        assertNotNull(data.findUserByUsername("user2"));
        assertNotNull(data.findUserByUsername("user3"));
    }

    @Test
    @DisplayName("Should throw DuplicityException when registering duplicate user")
    void testRegisterUser_DuplicateUsername() throws DuplicityException {
        // Arrange
        logic.registerUser("Original User", "duplicate", "pass1");

        // Act & Assert
        DuplicityException exception = assertThrows(
            DuplicityException.class,
            () -> logic.registerUser("Another User", "duplicate", "pass2"),
            "Should throw DuplicityException for duplicate username"
        );

        assertEquals("user already exists", exception.getMessage());
    }

    // ==================== authenticateUser tests ====================

    @Test
    @DisplayName("Should authenticate user successfully and return userId")
    void testAuthenticateUser_Success() throws DuplicityException, CredentialsException, NotFoundException {
        // Arrange
        logic.registerUser("María García", "mariagarcia", "pass456");
        Data data = Data.get();
        User registeredUser = data.findUserByUsername("mariagarcia");

        // Act
        String userId = logic.authenticateUser("mariagarcia", "pass456");

        // Assert
        assertNotNull(userId, "UserId should not be null");
        assertEquals(registeredUser.getId(), userId, "Should return correct userId");
    }

    @Test
    @DisplayName("Should throw CredentialsException when user not found")
    void testAuthenticateUser_UserNotFound() {
        // Act & Assert
        CredentialsException exception = assertThrows(
            CredentialsException.class,
            () -> logic.authenticateUser("nonexistent", "pass123"),
            "Should throw CredentialsException when user not found"
        );

        assertEquals("invalid credentials", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw CredentialsException when password is incorrect")
    void testAuthenticateUser_WrongPassword() throws DuplicityException {
        // Arrange
        logic.registerUser("Pedro López", "pedrolopez", "correctpass");

        // Act & Assert
        CredentialsException exception = assertThrows(
            CredentialsException.class,
            () -> logic.authenticateUser("pedrolopez", "wrongpass"),
            "Should throw CredentialsException when password is incorrect"
        );

        assertEquals("invalid credentials", exception.getMessage());
    }

    // ==================== getUserByUsername tests ====================

    @Test
    @DisplayName("Should get user by username successfully")
    void testGetUserByUsername_Success() throws DuplicityException, NotFoundException {
        // Arrange
        logic.registerUser("Ana Martínez", "anamartinez", "pass789");

        // Act
        User user = logic.getUserByUsername("anamartinez");

        // Assert
        assertNotNull(user, "User should not be null");
        assertEquals("Ana Martínez", user.getName());
        assertEquals("anamartinez", user.getUsername());
        assertEquals("pass789", user.getPassword());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user not found by username")
    void testGetUserByUsername_NotFound() {
        // Act & Assert
        NotFoundException exception = assertThrows(
            NotFoundException.class,
            () -> logic.getUserByUsername("nonexistent"),
            "Should throw NotFoundException when user not found"
        );

        assertEquals("user not found", exception.getMessage());
    }

    // ==================== getUserInfo tests ====================

    @Test
    @DisplayName("Should get user info by userId successfully")
    void testGetUserInfo_Success() throws DuplicityException, NotFoundException {
        // Arrange
        logic.registerUser("Carlos Ruiz", "carlosruiz", "pass321");
        Data data = Data.get();
        User registeredUser = data.findUserByUsername("carlosruiz");
        String userId = registeredUser.getId();

        // Act
        User user = logic.getUserInfo(userId);

        // Assert
        assertNotNull(user, "User should not be null");
        assertEquals(userId, user.getId());
        assertEquals("Carlos Ruiz", user.getName());
        assertEquals("carlosruiz", user.getUsername());
        assertEquals("pass321", user.getPassword());
    }

    @Test
    @DisplayName("Should throw NotFoundException when user not found by userId")
    void testGetUserInfo_NotFound() {
        // Act & Assert
        NotFoundException exception = assertThrows(
            NotFoundException.class,
            () -> logic.getUserInfo("nonexistent-id"),
            "Should throw NotFoundException when user not found by id"
        );

        assertEquals("user not found", exception.getMessage());
    }

    // ==================== getAllUsers tests ====================

    @Test
    @DisplayName("Should get all users successfully with valid userId")
    void testGetAllUsers_Success() throws DuplicityException, NotFoundException {
        // Arrange
        logic.registerUser("User One", "user1", "pass1");
        logic.registerUser("User Two", "user2", "pass2");
        logic.registerUser("User Three", "user3", "pass3");

        Data data = Data.get();
        User validUser = data.findUserByUsername("user1");
        String userId = validUser.getId();

        // Act
        User[] allUsers = logic.getAllUsers(userId);

        // Assert
        assertNotNull(allUsers, "Users array should not be null");
        assertEquals(100, allUsers.length, "Array should have size 100");

        // Verify first 3 users are not null
        assertNotNull(allUsers[0]);
        assertNotNull(allUsers[1]);
        assertNotNull(allUsers[2]);

        // Verify usernames
        assertEquals("user1", allUsers[0].getUsername());
        assertEquals("user2", allUsers[1].getUsername());
        assertEquals("user3", allUsers[2].getUsername());
    }

    @Test
    @DisplayName("Should throw NotFoundException when userId is invalid in getAllUsers")
    void testGetAllUsers_InvalidUserId() throws DuplicityException {
        // Arrange
        logic.registerUser("User One", "user1", "pass1");

        // Act & Assert
        NotFoundException exception = assertThrows(
            NotFoundException.class,
            () -> logic.getAllUsers("invalid-user-id"),
            "Should throw NotFoundException when userId is invalid"
        );

        assertEquals("user not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should get empty user array when only one user registered")
    void testGetAllUsers_SingleUser() throws DuplicityException, NotFoundException {
        // Arrange
        logic.registerUser("Solo User", "solouser", "pass");
        Data data = Data.get();
        User user = data.findUserByUsername("solouser");
        String userId = user.getId();

        // Act
        User[] allUsers = logic.getAllUsers(userId);

        // Assert
        assertNotNull(allUsers, "Users array should not be null");
        assertEquals(100, allUsers.length);
        assertNotNull(allUsers[0], "First user should exist");
        assertEquals("solouser", allUsers[0].getUsername());

        // Rest should be null
        for (int i = 1; i < 100; i++) {
            assertNull(allUsers[i], "Position " + i + " should be null");
        }
    }

    // ==================== Singleton pattern test ====================

    @Test
    @DisplayName("Should maintain singleton pattern")
    void testSingletonPattern() {
        // Act
        Logic instance1 = Logic.get();
        Logic instance2 = Logic.get();

        // Assert
        assertSame(instance1, instance2, "Logic should be a singleton");
    }

    // ==================== Integration test ====================

    @Test
    @DisplayName("Complete workflow: register, authenticate, get info, get all users")
    void testCompleteWorkflow() throws DuplicityException, CredentialsException, NotFoundException {
        // 1. Register users
        logic.registerUser("Admin User", "admin", "adminpass");
        logic.registerUser("Regular User", "regular", "regularpass");

        // 2. Authenticate
        String adminId = logic.authenticateUser("admin", "adminpass");
        assertNotNull(adminId);

        String regularId = logic.authenticateUser("regular", "regularpass");
        assertNotNull(regularId);

        // 3. Get user info
        User adminUser = logic.getUserInfo(adminId);
        assertEquals("Admin User", adminUser.getName());
        assertEquals("admin", adminUser.getUsername());

        User regularUser = logic.getUserInfo(regularId);
        assertEquals("Regular User", regularUser.getName());
        assertEquals("regular", regularUser.getUsername());

        // 4. Get all users
        User[] allUsers = logic.getAllUsers(adminId);
        assertNotNull(allUsers);
        assertEquals(2, countNonNullUsers(allUsers), "Should have 2 users");

        // 5. Verify user by username
        User foundUser = logic.getUserByUsername("admin");
        assertEquals(adminId, foundUser.getId());
    }

    /**
     * Helper method to count non-null users in array
     */
    private int countNonNullUsers(User[] users) {
        int count = 0;
        for (User user : users) {
            if (user != null) {
                count++;
            }
        }
        return count;
    }
}
