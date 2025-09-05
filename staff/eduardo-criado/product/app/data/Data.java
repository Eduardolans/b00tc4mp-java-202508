
// package data;

// public class Data {
//     private static Data instance;

//     public static Data getInstance() {
//         if (instance == null) {
//             instance = new Data();
//         }
//         return instance;
//     }

//     private User[] users;

//     public Data() {
//         users = new User[100];
//     }

//     public boolean addUser(User user) {
//         for (int i = 0; i < users.length; i++) {
//             if (users[i] == null) {
//                 users[i] = user;
//                 return true;
//             }
//         }
//         return false;
//     }

//     public User findUserByUsername(String username) {
//         for (User user : users) {
//             if (user != null && user.getUsername().equals(username)) {
//                 return user;
//             }
//         }
//         return null;
//     }

//     public User[] getAllUsers() {
//         return null;
//     }

// }

package data;

public class Data {

    private static Data instance;

    public static Data getInstance() {
        return instance == null ? instance = new Data() : instance;
    }

    private User[] users;

    public Data() {
        users = new User[100]; // Fixed size array for simplicity
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
        return users;
    }
}