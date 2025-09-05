
// import data.User;
// import data.Data;

// public class Logic {
//     public static void main(String[] args) {

//         registerUser("Eduardo", "eduardo", "1234");

//         User user = Data.getInstance().findUserByUsername("eduardo");
//         if (user != null) {
//             System.out.println("User found: " + user.getName());
//         } else {
//             System.out.println("User not found");
//         }
//     }

//     public static void registerUser(String name, String username, String password) {

//         Data data = Data.getInstance();

//         User user = new User(name, username, password);

//         if (data.addUser(user)) {
//             System.out.println("User registered successfully");
//         } else {
//             System.out.println("User registration failed");
//         }

//     }
// }

import data.Data;
import data.User;

public class Logic {
    public static void main(String[] args) {
        // Example usage
        registerUser("John Doe", "johndoe", "password123");
        registerUser("Jane Doe", "janedoe", "password456");

        // Imprimir todos los usuarios
        User[] allUsers = Data.getInstance().getAllUsers();
        System.out.println("=== Lista de todos los usuarios ===");

        for (int i = 0; i < allUsers.length; i++) {
            if (allUsers[i] != null) {
                User user = allUsers[i];
                System.out.println((i + 1) + ". Nombre: " + user.getName() +
                        ", Usuario: " + user.getUsername());
            }
        }
    }

    // public class Logic {

    // public static void main(String[] args) {
    // // Example usage
    // registerUser("John Doe", "johndoe", "password123");
    // registerUser("Jane Doe", "janedoe", "password456");

    // User[] allUsers = Data.getInstance().getAllUsers();
    // if (allUsers != null) {
    // for (User user : allUsers) {
    // if (user == null) {
    // continue;
    // }
    // System.out.println("User found: " + user.getName());
    // // System.out.println("User found: " + allUsers[1].getName());
    // }
    // } else {
    // System.out.println("User not found.");
    // }

    // // User user = Data.getInstance().findUserByUsername("johndoe");
    // // if (user != null) {
    // // System.out.println("User found: " + user.getName());
    // // } else {
    // // System.out.println("User not found.");
    // // }
    // }

    public static void registerUser(String name, String username, String password) {
        Data data = Data.getInstance();

        User user = new User(name, username, password);

        if (data.addUser(user)) {
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Registration failed. User array is full.");
        }
    }
}