package logic;

import data.Data;
import data.User;
import errors.DuplicityException;
import logic.Logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class LogicTest {

    private Logic logic;
    private Data data;

    @BeforeEach
    void setUp() throws Exception {
        // Resetear el singleton de Logic
        resetLogicSingleton();
        logic = Logic.get();

        // Resetear el singleton de Data
        resetDataSingleton();
        data = Data.get();
    }

    @Test
    @DisplayName("Should register a new user successfully")
    void testRegisterUser_Success() {
        // Arrange
        String name = "John Doe";
        String username = "johndoe";
        String password = "password123";

        // Act
        assertDoesNotThrow(() -> logic.registerUser(name, username, password));

        // Assert
        User registeredUser = data.findUserByUsername(username);
        assertNotNull(registeredUser, "User should be registered in data");
        assertEquals(name, registeredUser.getName());
        assertEquals(username, registeredUser.getUsername());
        assertEquals(password, registeredUser.getPassword());
    }

    @Test
    @DisplayName("Should throw DuplicityException when username already exists")
    void testRegisterUser_DuplicateUsername() {
        // Arrange
        String username = "johndoe";
        assertDoesNotThrow(() -> logic.registerUser("John Doe", username, "password123"));

        // Act & Assert
        DuplicityException exception = assertThrows(
                DuplicityException.class,
                () -> logic.registerUser("Jane Doe", username, "anotherpass"));

        assertEquals("user already exists", exception.getMessage());
    }

    @Test
    @DisplayName("Should allow multiple users with different usernames")
    void testRegisterUser_MultipleUsers() {
        // Act
        assertDoesNotThrow(() -> logic.registerUser("User One", "user1", "pass1"));
        assertDoesNotThrow(() -> logic.registerUser("User Two", "user2", "pass2"));
        assertDoesNotThrow(() -> logic.registerUser("User Three", "user3", "pass3"));

        // Assert
        assertNotNull(data.findUserByUsername("user1"));
        assertNotNull(data.findUserByUsername("user2"));
        assertNotNull(data.findUserByUsername("user3"));
    }

    // Métodos auxiliares
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

// package logic;

// import data.Data;
// import data.User;
// import errors.DuplicityException;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.DisplayName;

// import java.lang.reflect.Field;

// import static org.junit.jupiter.api.Assertions.*;

// class LogicTest {

// private Logic logic;
// private Data data;

// @BeforeEach
// void setUp() throws Exception {
// // Resetear el singleton de Logic
// resetLogicSingleton();
// logic = Logic.get();

// // Resetear el singleton de Data
// resetDataSingleton();
// data = Data.get();
// }

// @Test
// @DisplayName("Should register a new user successfully")
// void testRegisterUser_Success() {
// // Arrange
// String name = "John Doe";
// String username = "johndoe";
// String password = "password123";

// // Act
// assertDoesNotThrow(() -> logic.registerUser(name, username, password));

// // Assert
// User registeredUser = data.findUserByUsername(username);
// assertNotNull(registeredUser, "User should be registered in data");
// assertEquals(name, registeredUser.getName());
// assertEquals(username, registeredUser.getUsername());
// assertEquals(password, registeredUser.getPassword());
// }

// @Test
// @DisplayName("Should throw DuplicityException when username already exists")
// void testRegisterUser_DuplicateUsername() {
// // Arrange
// String name = "John Doe";
// String username = "johndoe";
// String password = "password123";

// // Registrar usuario por primera vez
// assertDoesNotThrow(() -> logic.registerUser(name, username, password));

// // Act & Assert
// DuplicityException exception = assertThrows(
// DuplicityException.class,
// () -> logic.registerUser("Jane Doe", username, "anotherpass"),
// "Should throw DuplicityException for duplicate username");

// assertEquals("user already exists", exception.getMessage());
// }

// @Test
// @DisplayName("Should allow multiple users with different usernames")
// void testRegisterUser_MultipleUsers() {
// // Arrange & Act
// assertDoesNotThrow(() -> logic.registerUser("User One", "user1", "pass1"));
// assertDoesNotThrow(() -> logic.registerUser("User Two", "user2", "pass2"));
// assertDoesNotThrow(() -> logic.registerUser("User Three", "user3", "pass3"));

// // Assert
// assertNotNull(data.findUserByUsername("user1"));
// assertNotNull(data.findUserByUsername("user2"));
// assertNotNull(data.findUserByUsername("user3"));
// }

// @Test
// @DisplayName("Should handle empty strings correctly")
// void testRegisterUser_EmptyStrings() {
// // Arrange
// String name = "";
// String username = "";
// String password = "";

// // Act & Assert
// assertDoesNotThrow(() -> logic.registerUser(name, username, password));

// User user = data.findUserByUsername(username);
// assertNotNull(user);
// assertEquals("", user.getName());
// assertEquals("", user.getUsername());
// }

// @Test
// @DisplayName("Should handle special characters in fields")
// void testRegisterUser_SpecialCharacters() {
// // Arrange
// String name = "José María";
// String username = "user@123";
// String password = "p@ssw0rd!#$";

// // Act & Assert
// assertDoesNotThrow(() -> logic.registerUser(name, username, password));

// User user = data.findUserByUsername(username);
// assertNotNull(user);
// assertEquals(name, user.getName());
// assertEquals(password, user.getPassword());
// }

// // Método auxiliar para resetear el singleton de Logic usando reflection
// private void resetLogicSingleton() throws Exception {
// Field instance = Logic.class.getDeclaredField("instance");
// instance.setAccessible(true);
// instance.set(null, null);
// }

// // Método auxiliar para resetear el singleton de Data usando reflection
// private void resetDataSingleton() throws Exception {
// Field instance = Data.class.getDeclaredField("instance");
// instance.setAccessible(true);
// instance.set(null, null);
// }
// }