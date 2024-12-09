package guis;

import constants.CommonConstants;
import db.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFormGUI extends Form {

    public LoginFormGUI() {
        super("Login");
        setTitleBarIcon("C:\\Users\\alexr\\IdeaProjects\\LoginRegistrationFormkmk\\src\\A_realistic_image_of_an_airplane_flying_in_a_clear.jpeg");
        addGuiComponents();
    }

    private void addGuiComponents() {
        // Create an image label
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\alexr\\IdeaProjects\\LoginRegistrationFormkmk\\src\\A_realistic_image_of_an_airplane_flying_in_a_clear.jpeg");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(350, getHeight(), Image.SCALE_SMOOTH); // Set width to 350
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setBounds(0, 0, 350, getHeight()); // Set bounds with width at 350
        add(imageLabel);

        // Adjust x-position of components to start after the image width (350)
        int startX = 550; // Start x-position after the image label
        int componentWidth = 350; // Set the width of the form components

        // Create login label
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setBounds(startX, 25, componentWidth, 100); // Adjust x-position and set width
        loginLabel.setForeground(CommonConstants.TEXT_COLOR);
        loginLabel.setFont(new Font("Dialog", Font.BOLD, 40));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loginLabel);

        // Create username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(startX, 150, componentWidth, 25); // Adjust x-position and set width
        usernameLabel.setForeground(CommonConstants.TEXT_COLOR);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(usernameLabel);

        // Create username text field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(startX, 185, componentWidth, 55); // Adjust x-position and set width
        usernameField.setBackground(CommonConstants.SECONDARY_COLOR);
        usernameField.setForeground(CommonConstants.TEXT_COLOR);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(usernameField);

        // Create password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(startX, 335, componentWidth, 25); // Adjust x-position and set width
        passwordLabel.setForeground(CommonConstants.TEXT_COLOR);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(passwordLabel);

        // Create password field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(startX, 365, componentWidth, 55); // Adjust x-position and set width
        passwordField.setBackground(CommonConstants.SECONDARY_COLOR);
        passwordField.setForeground(CommonConstants.TEXT_COLOR);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(passwordField);

        // Create login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Dialog", Font.BOLD, 18));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setBackground(CommonConstants.TEXT_COLOR);
        loginButton.setBounds(startX + (componentWidth - 250) / 2, 520, 250, 50); // Center the button within the form width
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (MyJDBC.validateLogin(username, password)) {
                    JOptionPane.showMessageDialog(LoginFormGUI.this, "Login Successful!");
                    LoginFormGUI.this.dispose();
                    new MainPageGUI().setVisible(true); // Navigate to Booking Page
                } else {
                    JOptionPane.showMessageDialog(LoginFormGUI.this, "Login Failed...");
                }
            }
        });

        add(loginButton);

        // Create register label
        JLabel registerLabel = new JLabel("Not a user? Register Here");
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLabel.setForeground(CommonConstants.TEXT_COLOR);
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LoginFormGUI.this.dispose();
                new RegisterFormGUI().setVisible(true);
            }
        });
        registerLabel.setBounds(startX + (componentWidth - 250) / 2, 600, 250, 30); // Center the label within the form width
        add(registerLabel);
    }
}





