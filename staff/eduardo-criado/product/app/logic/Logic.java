package logic;

import javax.security.auth.login.CredentialException;

import data.Data;
import data.User;
import errors.DuplicityException;
import errors.CredentialsException;

public class Logic {

    private static Logic instance;

    public static Logic get() {
        if (instance == null) {
            instance = new Logic();
        }
        return instance;
    }

    public static void main(String[] args) {

        Logic logic = Logic.get();

        try {
            logic.registerUser("Eduardo", "edulo", "1234");
            logic.registerUser("Eduardo", "edulo", "1234");
        } catch (DuplicityException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
        }

        User user = Data.get().findUserByUsername("edulo");
        if (user != null) {
            System.out.println("User found: " + user.getName());
        } else {
            System.out.println("User not found");
        }
    }

    private String username;

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

    public void loginUser(String username, String password) throws CredentialsException {
        Data data = Data.get();

        User user = data.findUserByUsername(username);

        if (user == null || !user.getUsername().equals(username)) {
            throw new CredentialsException("invalid credentials");
        }

        this.username = username;

        System.out.println("User logged in: " + user.getName());

    }

}