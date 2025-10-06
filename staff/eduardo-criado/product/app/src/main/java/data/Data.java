package data;

public class Data {
    private static Data instance;

    public static Data get() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    private User[] users;

    private Data() {
        users = new User[100];
    }

    public boolean addUser(User user) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                users[i] = user;
                return true;
            }
        }
        return false; // Array is full
    }

    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user != null && user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // User not found
    }

    public User[] getAllUsers() {
        // return null;
        return users;
    }
}