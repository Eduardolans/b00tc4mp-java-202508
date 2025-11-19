package com.example.logic;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.data.Data;
import com.example.data.User;
import com.example.errors.CredentialsException;
import com.example.errors.DuplicityException;
import com.example.errors.NotFoundException;

public class WorklfowTest {

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
        UserTO adminUser = logic.getUserInfo(adminId);
        assertEquals("Admin User", adminUser.getName());
        assertEquals("admin", adminUser.getUsername());

        UserTO regularUser = logic.getUserInfo(regularId);
        assertEquals("Regular User", regularUser.getName());
        assertEquals("regular", regularUser.getUsername());

        // 4. Get all users
        List<User> allUsers = logic.getAllUsers(adminId);
        assertNotNull(allUsers);
        assertEquals(2, countNonNullUsers(allUsers), "Should have 2 users");

    }

    /**
     * Helper method to reset the Logic singleton instance using reflection
     */
    private void resetLogicInstance() throws Exception {
        Field instanceField = Logic.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }

    // ==================== Integration test ====================

    /**
     * Helper method to reset the Data singleton instance using reflection
     */
    private void resetDataInstance() throws Exception {
        Field instanceField = Data.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, null);
    }

    /**
     * Helper method to count non-null users in array
     */
    private int countNonNullUsers(List<User> users) {
        int count = 0;
        for (User user : users) {
            if (user != null) {
                count++;
            }
        }
        return count;
    }

}
