
// import data.Data;
// import data.User;

// public class Logic {
//     public static void main(String[] args) {
//         // Example usage
//         registerUser("John Doe", "johndoe", "password123");
//         registerUser("Jane Doe", "janedoe", "password456");

//         // Imprimir todos los usuarios
//         User[] allUsers = Data.getInstance().getAllUsers();
//         System.out.println("=== Lista de todos los usuarios ===");

//         for (int i = 0; i < allUsers.length; i++) {
//             if (allUsers[i] != null) {
//                 User user = allUsers[i];
//                 System.out.println((i + 1) + ". Nombre: " + user.getName() +
//                         ", Usuario: " + user.getUsername());
//             }
//         }

//         User user = Data.getInstance().findUserByUsername("eduardolo");
//         System.err.println("usuario encontrado filtrados por username");
//         if (user != null) {
//             System.out.println("usuario encontrado: " + user.getName());
//         } else {
//             System.out.println("usuario no encontrado.");
//         }
//     }
//     public static void registerUser(String name, String username, String password) {
//             Data data = Data.getInstance();

//             User user = new User(name, username, password);

//             if (data.addUser(user)) {
//                 System.out.println("User registered successfully.");
//             } else {
//                 System.out.println("Registration failed. User array is full.");
//             }
//         }
// }

/////////////////

import data.Data;
import data.User;

public class LogicCopy {

    public static void main(String[] args) {
        // Example usage
        registerUser("John Doe", "johndoe", "password123");
        registerUser("Jane Doe", "janedoe", "password456");
        registerUser("Eduardo", "eduardolo", "1234");

        User[] allUsers = Data.get().getAllUsers();
        System.out.println("=== Lista de todos los usuarios ===");
        if (allUsers != null) {
            for (User user : allUsers) {
                if (user == null) {
                    continue;
                }
                System.out.println("User found: " + user.getName());
                // System.out.println("User found: " + allUsers[1].getName());
            }
        } else {
            System.out.println("User not found.");
        }

        User user = Data.get().findUserByUsername("eduardolo");
        System.err.println("usuario encontrado filtrados por username");
        if (user != null) {
            System.out.println("usuario encontrado: " + user.getName());
        } else {
            System.out.println("usuario no encontrado.");
        }
    }

    public static void registerUser(String name, String username, String password) {
        Data data = Data.get();

        User user = new User(name, username, password);

        if (data.addUser(user)) {
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Registration failed. User array is full.");
        }
    }

}