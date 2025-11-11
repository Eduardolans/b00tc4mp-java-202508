package com.example.logic;

import java.util.List;

import com.example.data.Data;
import com.example.data.User;
import com.example.errors.CredentialsException;
import com.example.errors.DuplicityException;
import com.example.errors.NotFoundException;

public class Logic {

    private static Logic instance;

    public static Logic get() {
        if (instance == null) {
            instance = new Logic();
        }
        return instance;
    }

    private Logic() {
    }

    public void registerUser(String name, String username, String password) throws DuplicityException {

        Data data = Data.get();

        User user = data.findUserByUsername(username);

        if (user != null) {
            throw new DuplicityException("user already exists");
        }

        user = new User(name, username, password);

        data.addUser(user);

    }

    public String authenticateUser(String username, String password) throws NotFoundException, CredentialsException {
        Data data = Data.get();

        User user = data.findUserByUsername(username);

        if (user == null) {
            throw new CredentialsException("invalid credentials");
        }

        if (!user.getPassword().equals(password)) {
            throw new CredentialsException("invalid credentials");
        }

        return user.getId();
    }

    public User getUserByUsername(String username) throws NotFoundException {
        Data data = Data.get();
        User user = data.findUserByUsername(username);

        if (user == null) {
            throw new NotFoundException("user not found");
        }

        return user;
    }

    public User getUserInfo(String userId) throws NotFoundException {
        Data data = Data.get();
        User user = data.findUserById(userId);

        if (user == null) {
            throw new NotFoundException("user not found");
        }

        return user;
    }

    public List<User> getAllUsers(String userId) throws NotFoundException {
        Data data = Data.get();
        User user = data.findUserById(userId);

        if (user == null) {
            throw new NotFoundException("user not found");

        }
        return data.getAllUsers();
    }

}
