package guis;

import constants.CommonConstants;
import javax.swing.*;
import java.awt.*;

public class Form extends JFrame {

    // Constructor to set up the form
    public Form(String title) {
        super(title); // Set the title of the window
        setSize(1100, 680); // Set the size of the window
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Close the program when the window is closed
        setLayout(null); // Use absolute positioning for components (no layout manager)
        setLocationRelativeTo(null); // Center the window on the screen
        setResizable(false); // Prevent the window from being resized
        getContentPane().setBackground(CommonConstants.PRIMARY_COLOR); // Set the background color of the window
    }

    public void setTitleBarIcon(String imagePath) {
        ImageIcon icon = new ImageIcon("C:\\Users\\alexr\\IdeaProjects\\LoginRegistrationFormkmk\\src\\A_realistic_image_of_an_airplane_flying_in_a_clear.jpeg");
        setIconImage(icon.getImage()); // Set the image as the icon for the title bar
    }
}
