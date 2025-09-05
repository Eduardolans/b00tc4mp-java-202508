
// import data.User;
// import java.awt.*;
// import javax.swing.*;

// public class Main {

//     public static void main(String[] args) {

//         new User("Eduardo", "eduardo", "1234");

//         SwingUtilities.invokeLater(() -> {
//             new AppFrame().setVisible(true);
//         });
//     }
// }

// class AppFrame extends JFrame {

//     private static final String REGISTER_VIEW = "Register";
//     private static final String LOGIN_VIEW = "Login";
//     private static final String HOME_VIEW = "Home";

//     private JPanel cardPanel;
//     private CardLayout cardLayout;

//     public AppFrame() {
//         setTitle("Simple Swing App");
//         setSize(400, 300);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null); // Center the frame

//         // Initialize CardLayout
//         cardLayout = new CardLayout();
//         cardPanel = new JPanel(cardLayout);

//         // Create views
//         JPanel registerView = createRegisterView();
//         JPanel loginView = createLoginView();
//         JPanel homeView = createHomeView();

//         // Add views to CardLayout
//         cardPanel.add(registerView, REGISTER_VIEW);
//         cardPanel.add(loginView, LOGIN_VIEW);
//         cardPanel.add(homeView, HOME_VIEW);

//         // Add cardPanel to frame
//         add(cardPanel);

//         // Show Login view by default
//         cardLayout.show(cardPanel, LOGIN_VIEW);
//     }

//     private JPanel createRegisterView() {
//         JPanel panel = new JPanel(new GridBagLayout());
//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(10, 10, 10, 10);
//         gbc.fill = GridBagConstraints.HORIZONTAL;

//         // Name field
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         panel.add(new JLabel("Name:"), gbc);

//         gbc.gridx = 1;
//         JTextField nameField = new JTextField(15);
//         panel.add(nameField, gbc);

//         // Username field
//         gbc.gridx = 0;
//         gbc.gridy = 1;
//         panel.add(new JLabel("Username:"), gbc);

//         gbc.gridx = 1;
//         JTextField usernameField = new JTextField(15);
//         panel.add(usernameField, gbc);

//         // Password field
//         gbc.gridx = 0;
//         gbc.gridy = 2;
//         panel.add(new JLabel("Password:"), gbc);

//         gbc.gridx = 1;
//         JPasswordField passwordField = new JPasswordField(15);
//         panel.add(passwordField, gbc);

//         // Register button
//         gbc.gridx = 0;
//         gbc.gridy = 3;
//         gbc.gridwidth = 2;
//         JButton registerButton = new JButton("Register");
//         panel.add(registerButton, gbc);

//         // Link to Login view
//         gbc.gridy = 4;
//         JButton toLoginButton = new JButton("Already have an account? Login");
//         panel.add(toLoginButton, gbc);

//         // Actions
//         registerButton.addActionListener(e -> {
//             String username = usernameField.getText();

//             if (!username.isEmpty()) {
//                 cardLayout.show(cardPanel, LOGIN_VIEW);
//             } else {
//                 JOptionPane.showMessageDialog(this, "Please enter a username");
//             }
//         });

//         toLoginButton.addActionListener(e -> cardLayout.show(cardPanel, LOGIN_VIEW));

//         return panel;
//     }

//     private JPanel createLoginView() {
//         JPanel panel = new JPanel(new GridBagLayout());
//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(10, 10, 10, 10);
//         gbc.fill = GridBagConstraints.HORIZONTAL;

//         // Username field
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         panel.add(new JLabel("Username:"), gbc);

//         gbc.gridx = 1;
//         JTextField usernameField = new JTextField(15);
//         panel.add(usernameField, gbc);

//         // Password field
//         gbc.gridx = 0;
//         gbc.gridy = 1;
//         panel.add(new JLabel("Password:"), gbc);

//         gbc.gridx = 1;
//         JPasswordField passwordField = new JPasswordField(15);
//         panel.add(passwordField, gbc);

//         // Login button
//         gbc.gridx = 0;
//         gbc.gridy = 2;
//         gbc.gridwidth = 2;
//         JButton loginButton = new JButton("Login");
//         panel.add(loginButton, gbc);

//         // Link to Register view
//         gbc.gridy = 3;
//         JButton toRegisterButton = new JButton("No account? Register");
//         panel.add(toRegisterButton, gbc);

//         // Actions
//         loginButton.addActionListener(e -> {
//             String username = usernameField.getText();

//             if (!username.isEmpty()) {
//                 cardLayout.show(cardPanel, HOME_VIEW);
//             } else {
//                 JOptionPane.showMessageDialog(this, "Please enter a username");
//             }
//         });

//         toRegisterButton.addActionListener(e -> cardLayout.show(cardPanel, REGISTER_VIEW));

//         return panel;
//     }

//     private JPanel createHomeView() {
//         JPanel panel = new JPanel(new GridBagLayout());
//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(10, 10, 10, 10);

//         // Welcome label
//         gbc.gridx = 0;
//         gbc.gridy = 0;
//         panel.add(new JLabel("Welcome to the Home Page!"), gbc);

//         // Logout button
//         gbc.gridy = 1;
//         JButton logoutButton = new JButton("Logout");
//         panel.add(logoutButton, gbc);

//         // Action
//         logoutButton.addActionListener(e -> cardLayout.show(cardPanel, LOGIN_VIEW));

//         return panel;
//     }
// }

import java.awt.*;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AppFrame().setVisible(true);
        });
    }
}

class AppFrame extends JFrame {

    private static final String REGISTER_VIEW = "Register";
    private static final String LOGIN_VIEW = "Login";
    private static final String HOME_VIEW = "Home";

    private JPanel cardPanel;
    private CardLayout cardLayout;

    public AppFrame() {
        setTitle("Simple Swing App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        // Initialize CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Create views
        JPanel registerView = createRegisterView();
        JPanel loginView = createLoginView();
        JPanel homeView = createHomeView();

        // Add views to CardLayout
        cardPanel.add(registerView, REGISTER_VIEW);
        cardPanel.add(loginView, LOGIN_VIEW);
        cardPanel.add(homeView, HOME_VIEW);

        // Add cardPanel to frame
        add(cardPanel);

        // Show Login view by default
        cardLayout.show(cardPanel, LOGIN_VIEW);
    }

    private JPanel createRegisterView() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        JTextField nameField = new JTextField(15);
        panel.add(nameField, gbc);

        // Username field
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // Password field
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // Register button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton registerButton = new JButton("Register");
        panel.add(registerButton, gbc);

        // Link to Login view
        gbc.gridy = 4;
        JButton toLoginButton = new JButton("Already have an account? Login");
        panel.add(toLoginButton, gbc);

        // Actions
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();

            if (!username.isEmpty()) {
                cardLayout.show(cardPanel, LOGIN_VIEW);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a username");
            }
        });

        toLoginButton.addActionListener(e -> cardLayout.show(cardPanel, LOGIN_VIEW));

        return panel;
    }

    private JPanel createLoginView() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username field
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // Password field
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // Login button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        panel.add(loginButton, gbc);

        // Link to Register view
        gbc.gridy = 3;
        JButton toRegisterButton = new JButton("No account? Register");
        panel.add(toRegisterButton, gbc);

        // Actions
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();

            if (!username.isEmpty()) {
                cardLayout.show(cardPanel, HOME_VIEW);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a username");
            }
        });

        toRegisterButton.addActionListener(e -> cardLayout.show(cardPanel, REGISTER_VIEW));

        return panel;
    }

    private JPanel createHomeView() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Welcome label
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Welcome to the Home Page!"), gbc);

        // Logout button
        gbc.gridy = 1;
        JButton logoutButton = new JButton("Logout");
        panel.add(logoutButton, gbc);

        // Action
        logoutButton.addActionListener(e -> cardLayout.show(cardPanel, LOGIN_VIEW));

        return panel;
    }
}