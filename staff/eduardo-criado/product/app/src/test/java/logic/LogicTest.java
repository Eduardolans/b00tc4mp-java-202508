package logic;

import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import data.Data;
import data.User;
import errors.DuplicityException;

class LogicTest {

    private Logic logic;
    private Data data;

    @BeforeEach
    void setUp() throws Exception {
        // Se ejecuta ANTES de cada test
        resetLogicSingleton();
        logic = Logic.get();

        resetDataSingleton();
        data = Data.get();

        System.out.println("🔧 Test iniciado");
    }

    @AfterEach
    void tearDown() throws Exception {
        // Se ejecuta DESPUÉS de cada test
        // Útil para limpiar recursos, cerrar conexiones, etc.
        resetLogicSingleton();
        resetDataSingleton();

        System.out.println("✅ Test finalizado\n");
    }

    @Test
    @DisplayName("Should register a new user successfully")
    void testRegisterUser_Success() {
        String name = "John Doe";
        String username = "johndoe";
        String password = "password123";

        assertDoesNotThrow(() -> logic.registerUser(name, username, password));

        User registeredUser = data.findUserByUsername(username);
        assertNotNull(registeredUser, "User should be registered in data");
        assertEquals(name, registeredUser.getName());
        assertEquals(username, registeredUser.getUsername());
        assertEquals(password, registeredUser.getPassword());
    }

    @Test
    @DisplayName("Should throw DuplicityException when username already exists")
    void testRegisterUser_DuplicateUsername() {
        String username = "johndoe";
        assertDoesNotThrow(() -> logic.registerUser("John Doe", username, "password123"));

        DuplicityException exception = assertThrows(
                DuplicityException.class,
                () -> logic.registerUser("Jane Doe", username, "anotherpass"));

        assertEquals("user already exists", exception.getMessage());
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