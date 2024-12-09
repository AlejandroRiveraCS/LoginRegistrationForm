package guis;

import constants.CommonConstants;
import db.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class RegisterFormGUI extends Form {
    public RegisterFormGUI() {
        super("Register"); // Set the title of the window
        addGuiComponents(); // Add components to the window
    }


    private void addGuiComponents() {
        // Load and display the image on the left side
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\Alex\\IdeaProjects\\LoginRegistrationFormSQL\\src\\A_realistic_image_of_an_airplane_flying_in_a_clear.jpeg"); // Replace with your image path
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(350, getHeight(), Image.SCALE_SMOOTH); // Scale the image to width 350
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setBounds(0, 0, 350, getHeight()); // Position the image label on the left side
        add(imageLabel);


        // Set positions and dimensions for form components
        int startX = 490; // X-position for components to start after the image
        int componentWidth = 350; // Width for form components


        // Create and configure the "Register" title label
        JLabel registerLabel = new JLabel("Register");
        registerLabel.setBounds(460, 25, 520, 100);
        registerLabel.setForeground(CommonConstants.TEXT_COLOR); // Set text color
        registerLabel.setFont(new Font("Dialog", Font.BOLD, 40)); // Set font style and size
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center-align the text
        add(registerLabel);


        // Create and configure the "Username" label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(startX, 150, 400, 25);
        usernameLabel.setForeground(CommonConstants.TEXT_COLOR);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(usernameLabel);


        // Create and configure the username input field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(startX, 185, 450, 55);
        usernameField.setBackground(CommonConstants.SECONDARY_COLOR);
        usernameField.setForeground(CommonConstants.TEXT_COLOR);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(usernameField);


        // Create and configure the "Password" label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(startX, 255, 400, 25);
        passwordLabel.setForeground(CommonConstants.TEXT_COLOR);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(passwordLabel);


        // Create and configure the password input field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(startX, 285, 450, 55);
        passwordField.setBackground(CommonConstants.SECONDARY_COLOR);
        passwordField.setForeground(CommonConstants.TEXT_COLOR);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(passwordField);


        // Create and configure the "Re-enter Password" label
        JLabel rePasswordLabel = new JLabel("Re-enter Password:");
        rePasswordLabel.setBounds(startX, 365, 400, 25);
        rePasswordLabel.setForeground(CommonConstants.TEXT_COLOR);
        rePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(rePasswordLabel);


        // Create and configure the re-enter password input field
        JPasswordField rePasswordField = new JPasswordField();
        rePasswordField.setBounds(startX, 395, 450, 55);
        rePasswordField.setBackground(CommonConstants.SECONDARY_COLOR);
        rePasswordField.setForeground(CommonConstants.TEXT_COLOR);
        rePasswordField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(rePasswordField);


        // Create and configure the "Register" button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Dialog", Font.BOLD, 18));
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand on hover
        registerButton.setBackground(CommonConstants.TEXT_COLOR);
        registerButton.setBounds(590, 520, 250, 50);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input from fields
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String rePassword = new String(rePasswordField.getPassword());


                // Validate input and attempt to register user
                if (validateUserInput(username, password, rePassword)) {
                    if (MyJDBC.register(username, password)) {
                        RegisterFormGUI.this.dispose(); // Close the register form
                        new LoginFormGUI().setVisible(true); // Open the login form
                        JOptionPane.showMessageDialog(null, "Registered Account Successfully!"); // Display success message
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Username already taken"); // Display error if username exists
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Username must be at least 6 characters \n" +
                            "and/or Passwords must match"); // Display validation error
                }
            }
        });


        add(registerButton);

        // Create and configure the "Login Here" label for navigation
        JLabel loginLabel = new JLabel("Have an account? Login Here");
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change cursor to hand on hover
        loginLabel.setForeground(CommonConstants.TEXT_COLOR);
        loginLabel.setBounds(590, 600, 250, 30);
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterFormGUI.this.dispose(); // Close the register form
                new LoginFormGUI().setVisible(true); // Open the login form
            }
        });
        add(loginLabel);
    }


    // Validate the input fields
    private boolean validateUserInput(String username, String password, String rePassword) {
        // Check if any field is empty
        if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) return false;

        // Check if username is at least 6 characters long
        if (username.length() < 6) return false;

        // Check if passwords match
        return password.equals(rePassword);
    }
}