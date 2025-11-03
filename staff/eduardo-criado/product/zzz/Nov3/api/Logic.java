package logic;

import data.Data;
import data.User;
import errors.CredentialsException;
import errors.DuplicityException;
import errors.NotFoundException;

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

    public User loginUser(String username, String password) throws NotFoundException, CredentialsException {
        Data data = Data.get();

        User user = data.findUserByUsername(username);

        if (user == null) {
            throw new CredentialsException("invalid credentials");
        }

        if (!user.getPassword().equals(password)) {
            throw new CredentialsException("invalid credentials");
        }

        return user;
    }

    public User getUserByUsername(String username) throws NotFoundException {
        Data data = Data.get();
        User user = data.findUserByUsername(username);

        if (user == null) {
            throw new NotFoundException("user not found");
        }

        return user;
    }

    public User[] getAllUsers() {
        Data data = Data.get();
        return data.getAllUsers();
    }

}
