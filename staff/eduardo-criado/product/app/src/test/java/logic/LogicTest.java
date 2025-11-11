package logic;

import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import data.Data;
import data.User;
import errors.CredentialsException;
import errors.DuplicityException;

/**
 * Integration tests for Logic class with REST API
 * NOTE: These tests require the API to be running on http://localhost:8080
 * Start the API with: cd api && mvn jetty:run
 */
class LogicTest {

    private static int testCounter = 0;

    @BeforeAll
    static void checkApiRunning() {
        System.out.println("⚠️  NOTE: These tests require the API to be running on http://localhost:8080");
        System.out.println("   Start the API with: cd api && mvn jetty:run\n");
    }

    private Logic logic;

    private Data data;

    @BeforeEach
    void setUp() throws Exception {
        // Se ejecuta ANTES de cada test
        resetLogicSingleton();
        logic = Logic.get();

        resetDataSingleton();
        data = Data.get();

        testCounter++;
        System.out.println("🔧 Test #" + testCounter + " iniciado");
    }

    @AfterEach
    void tearDown() throws Exception {
        // Se ejecuta DESPUÉS de cada test
        // Útil para limpiar recursos, cerrar conexiones, etc.
        resetLogicSingleton();
        resetDataSingleton();

        System.out.println("✅ Test #" + testCounter + " finalizado\n");
    }

    @Test
    @DisplayName("Should register a new user successfully via API")
    void testRegisterUser_Success() {
        String name = "John Doe Test";
        String username = "johndoe_test_" + System.currentTimeMillis(); // Unique username
        String password = "password123";

        // Register user via API (POST /api/users)
        assertDoesNotThrow(() -> logic.registerUser(name, username, password),
                "Registration should succeed");

        // Verify by attempting to login with the new user (POST /api/users/auth)
        assertDoesNotThrow(() -> logic.loginUser(username, password),
                "Login should succeed with newly registered user");

        // Verify token was saved in Data
        assertNotNull(data.getToken(), "Token should be saved in Data after login");

        // Verify we can get user info with the token (GET /api/users/info)
        assertDoesNotThrow(() -> {
            String retrievedName = logic.getUsername();
            assertEquals(name, retrievedName, "Retrieved name should match registered name");
        }, "Should retrieve user info successfully");
    }

    @Test
    @DisplayName("Should throw DuplicityException when username already exists")
    void testRegisterUser_DuplicateUsername() {
        String name = "Jane Doe Test";
        String username = "janedoetest" + System.currentTimeMillis(); // Unique username
        String password = "password123";

        // First registration should succeed
        assertDoesNotThrow(() -> logic.registerUser(name, username, password),
                "First registration should succeed");

        // Second registration with same username should fail
        DuplicityException exception = assertThrows(
                DuplicityException.class,
                () -> logic.registerUser("Another Name", username, "anotherpass"),
                "Second registration with same username should throw DuplicityException");

        assertEquals("user already exists", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw CredentialsException for invalid login")
    void testLoginUser_InvalidCredentials() {
        String username = "nonexistent_user_" + System.currentTimeMillis();
        String password = "wrongpassword";

        // Login with non-existent user should fail
        CredentialsException exception = assertThrows(
                CredentialsException.class,
                () -> logic.loginUser(username, password),
                "Login with invalid credentials should throw CredentialsException");

        assertEquals("invalid credentials", exception.getMessage());
    }

    @Test
    @DisplayName("Should login successfully and save token in Data")
    void testLoginUser_Success() {
        String name = "Login Test User";
        String username = "logintestuser" + System.currentTimeMillis();
        String password = "testpass123";

        // Register user first
        assertDoesNotThrow(() -> logic.registerUser(name, username, password));

        // Login should succeed
        assertDoesNotThrow(() -> logic.loginUser(username, password));

        // Token should be saved in Data
        assertNotNull(data.getToken(), "Token should be saved in Data");
        assertTrue(data.getToken() instanceof String, "Token should be a String");

        // Verify we can get user info with the token (GET /api/users/info)
        assertDoesNotThrow(() -> {
            String retrievedName = logic.getUsername();
            assertEquals(name, retrievedName, "Retrieved name should match registered name");
        }, "Should retrieve user info successfully");
    }

    @Test
    @DisplayName("Should get all users successfully")
    void testGetAllUsers_Success() {

        // Registra 2 usuarios
        // Hace login
        // Obtiene todos los usuarios
        // Verifica que ambos están en la lista

        String name1 = "User One";
        String username1 = "userone" + System.currentTimeMillis();
        String password1 = "pass123";

        String name2 = "User Two";
        String username2 = "usertwo" + System.currentTimeMillis();
        String password2 = "pass456";

        // Register two users
        assertDoesNotThrow(() -> logic.registerUser(name1, username1, password1));
        assertDoesNotThrow(() -> logic.registerUser(name2, username2, password2));

        // Login with first user
        assertDoesNotThrow(() -> logic.loginUser(username1, password1));

        // Get all users
        assertDoesNotThrow(() -> {
            User[] users = logic.getAllUsers();
            assertNotNull(users, "Users array should not be null");
            assertTrue(users.length >= 2, "Should have at least 2 users");

            // Verify our users are in the list
            boolean foundUser1 = false;
            boolean foundUser2 = false;
            for (User user : users) {
                if (user.getUsername().equals(username1)) {
                    foundUser1 = true;
                    assertEquals(name1, user.getName(), "User 1 name should match");
                }
                if (user.getUsername().equals(username2)) {
                    foundUser2 = true;
                    assertEquals(name2, user.getName(), "User 2 name should match");
                }
            }
            assertTrue(foundUser1, "User 1 should be in the list");
            assertTrue(foundUser2, "User 2 should be in the list");
        }, "Should retrieve all users successfully");
    }

    @Test
    @DisplayName("Should get daily quote successfully")
    void testGetDailyQuote_Success() {

        // Obtiene quote del día
        // Verifica formato correcto

        // Test the external API call
        assertDoesNotThrow(() -> {
            String quote = logic.getDailyQuote();
            assertNotNull(quote, "Quote should not be null");
            assertTrue(quote.length() > 0, "Quote should not be empty");
            assertTrue(quote.contains("\""), "Quote should contain quotation marks");
            assertTrue(quote.contains("—"), "Quote should contain author separator");
        }, "Should retrieve daily quote successfully");
    }

    private void resetLogicSingleton() throws Exception {
        Field instance = Logic.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    private void resetDataSingleton() throws Exception {
        Field instance = Data.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }
}