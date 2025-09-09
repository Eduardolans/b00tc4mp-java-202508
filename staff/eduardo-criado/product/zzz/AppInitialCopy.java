
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AppInitialCopy {

    public static void main(String[] args) {

        JFrame registerFrame = new JFrame("Register");
        JFrame loginFrame = new JFrame("Login");

        // Register view
        {
            registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            registerFrame.setSize(500, 500);
            registerFrame.setLayout(new BoxLayout(registerFrame.getContentPane(), BoxLayout.Y_AXIS));

            JLabel titleLabel = new JLabel("Register View");
            registerFrame.add(titleLabel);
            // registerFrame.getContentPane().add(titleLabel);

            JLabel nameLabel = new JLabel("Name:");
            registerFrame.add(nameLabel);

            JTextField nameField = new JTextField();
            registerFrame.add(nameField);

            JLabel usernameLabel = new JLabel("Username:");
            registerFrame.add(usernameLabel);

            JTextField usernameField = new JTextField();
            registerFrame.add(usernameField);

            JLabel passwordLabel = new JLabel("Password:");
            registerFrame.add(passwordLabel);

            JTextField passwordField = new JTextField();
            registerFrame.add(passwordField);

            JButton registerButton = new JButton("Register");
            registerFrame.add(registerButton);

            registerButton.addActionListener(e -> {
                registerFrame.setVisible(false);
                loginFrame.setVisible(true);
            });

            registerFrame.setVisible(true);
        }

        // Login view

        {
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setSize(500, 500);
            loginFrame.setLayout(new BoxLayout(loginFrame.getContentPane(), BoxLayout.Y_AXIS));

            JLabel titleLabel = new JLabel("Login View");
            loginFrame.add(titleLabel);

            JLabel usernameLabel = new JLabel("Username:");
            loginFrame.add(usernameLabel);

            JTextField usernameField = new JTextField();
            loginFrame.add(usernameField);

            JLabel passwordLabel = new JLabel("Password:");
            loginFrame.add(passwordLabel);

            JTextField passwordField = new JTextField();
            loginFrame.add(passwordField);

            JButton loginButton = new JButton("Login");
            loginFrame.add(loginButton);

            // loginFrame.setVisible(true);
        }

        // Home view
    }
}
