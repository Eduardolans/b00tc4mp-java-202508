package com.example.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    private static Data instance;

    public static Data get() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    protected Map<String, User> usersByUsername;
    protected Map<String, User> usersById;

    private Data() {
        usersByUsername = new HashMap<>();
        usersById = new HashMap<>();
    }

    public void addUser(User user) {
        usersByUsername.put(user.getUsername(), user);
        usersById.put(user.getId(), user);
    }

    public User findUserByUsername(String username) {
        return usersByUsername.get(username);
    }

    public User findUserById(String id) {
        return usersById.get(id);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(usersById.values());
    }

    /**
     * Reset all data - FOR TESTING PURPOSES ONLY
     * Clears all users from memory
     */
    public void reset() {
        usersByUsername = new HashMap<>();
        usersById = new HashMap<>();
    }
}
