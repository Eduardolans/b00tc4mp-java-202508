import java.awt.*;
import javax.swing.*;

import errors.*;
import logic.Logic;

public class App extends JFrame {

    private static final String REGISTER_VIEW = "Register";
    private static final String LOGIN_VIEW = "Login";
    private static final String HOME_VIEW = "Home";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new App().setVisible(true);
        });
    }

    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JLabel homeWelcomeLabel;
    private JLabel quoteLabel;

    private Logic logic;

    public App() {
        setTitle("Simple Swing App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        logic = Logic.get();

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
        JTextField nameField = new JTextField(15); // assign to field
        panel.add(nameField, gbc);

        // Username field
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        JTextField usernameField = new JTextField(15); // assign to field
        panel.add(usernameField, gbc);

        // Password field
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(15); // assign to field
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
        registerButton.addActionListener(event -> {
            String name = nameField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                logic.registerUser(name, username, password);

                nameField.setText("");
                usernameField.setText("");
                passwordField.setText("");

                cardLayout.show(cardPanel, LOGIN_VIEW);

            } catch (DuplicityException exception) {
                exception.printStackTrace();

                JOptionPane.showMessageDialog(this, exception.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);

                // JOptionPane.showMessageDialog(this, exception.getMessage());

                nameField.setText("");
                usernameField.setText("");
                passwordField.setText("");
            }

        });

        toLoginButton.addActionListener(event -> {

            nameField.setText("");
            usernameField.setText("");
            passwordField.setText("");

            cardLayout.show(cardPanel, LOGIN_VIEW);
        });

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

        // Action
        loginButton.addActionListener(event -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try {
                logic.loginUser(username, password);

                usernameField.setText("");
                passwordField.setText("");

                homeWelcomeLabel.setText("Welcome, " + logic.getUsername() + "!");

                // String quote = logic.getDailyQuote();
                // quoteLabel.setText("<html><div style='width: 300px; text-align:
                // center;'>Today's quote: " + quote
                // + "</div></html>");

                try {
                    String quote = logic.getDailyQuote();
                    quoteLabel.setText("<html><div style='width: 300px; text-align:center;'>Today's quote: " + quote
                            + "</div></html>");
                } catch (ConnectionError | ServerError | ClientError | ContentError e) {
                    e.printStackTrace();
                    quoteLabel
                            .setText("<html><div style='width: 300px; text-align: center;'>Could not load daily quote: "
                                    + e.getMessage() + "</div></html>");
                }

                cardLayout.show(cardPanel, HOME_VIEW);

                // try {
                // String quote = logic.getDailyQuote();
                // quoteLabel.setText("<html><div style='width: 300px; text-align:
                // center;'>Today's quote: " + quote
                // + "</div></html>");
                // } catch (ConnectionError | ServerError | ClientError | ContentError e) {
                // e.printStackTrace();
                // JOptionPane.showMessageDialog(this,
                // "Could not load daily quote: " + e.getMessage(),
                // "Quote Error",
                // JOptionPane.WARNING_MESSAGE);
                // quoteLabel.setText(
                // "<html><div style='width: 300px; text-align: center;'>Quote
                // unavailable</div></html>");
                // }

            } catch (NotFoundException exception) {
                exception.printStackTrace();

                JOptionPane.showMessageDialog(this, exception.getMessage());

            } catch (CredentialsException exception) {
                exception.printStackTrace();

                JOptionPane.showMessageDialog(this, exception.getMessage());
            }
        });

        toRegisterButton.addActionListener(event -> {

            usernameField.setText("");
            passwordField.setText("");

            cardLayout.show(cardPanel, REGISTER_VIEW);

        });

        return panel;
    }

    private JPanel createHomeView() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Welcome label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        homeWelcomeLabel = new JLabel("Welcome to the Home Page!");
        homeWelcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(homeWelcomeLabel, gbc);

        // panel.add(homeWelcomeLabel = new JLabel("Welcome to the Home Page!"), gbc);

        // Quote label
        // gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        quoteLabel = new JLabel(""); // Inicializa el label para la quote
        quoteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quoteLabel.setVerticalAlignment(SwingConstants.TOP);
        panel.add(quoteLabel, gbc);
        // panel.add(new JLabel(QuoteService.getDailyQuote()), gbc);

        // Logout button
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton logoutButton = new JButton("Logout");
        panel.add(logoutButton, gbc);

        // Action
        logoutButton.addActionListener(event -> cardLayout.show(cardPanel, LOGIN_VIEW));

        return panel;
    }

}