package logic;

import data.Data;
import data.User;

import errors.*;

public class Logic {

    private static Logic instance;

    public static Logic get() {
        if (instance == null) {
            instance = new Logic();
        }
        return instance;
    }

    // public static void main(String[] args) {

    // Logic logic = Logic.get();
    // Data data = Data.get();

    // try {
    // logic.registerUser("Eduardo", "edulo", "1234");
    // logic.registerUser("Eduardo", "edulo", "1234");
    // } catch (DuplicityException exception) {
    // // TODO Auto-generated catch block
    // exception.printStackTrace();
    // }

    // User user = data.findUserByUsername("edulo");
    // if (user != null) {
    // System.out.println("User found: " + user.getName());
    // } else {
    // System.out.println("User not found");
    // }
    // }

    private String username;

    private Logic() {
    }

    public void registerUser(String name, String username, String password) throws DuplicityException {

        // this.username = username;

        Data data = Data.get();

        User user = data.findUserByUsername(username);

        if (user != null) {
            throw new DuplicityException("user already exists");
        }

        user = new User(name, username, password);

        data.addUser(user);

    }

    public void loginUser(String username, String password) throws NotFoundException, CredentialsException {
        Data data = Data.get();

        User user = data.findUserByUsername(username);

        if (user == null) {
            throw new CredentialsException("user not found");
        }

        if (!user.getPassword().equals(password)) {
            throw new CredentialsException("invalid password");
        }

        this.username = username;

    }

    public String getUsername() throws NotFoundException {
        Data data = Data.get();
        User user = data.findUserByUsername(this.username);

        if (user == null) {
            throw new NotFoundException("user not found");
        }

        return user.getName();
    }

}