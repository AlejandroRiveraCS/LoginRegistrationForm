package guis;

import constants.CommonConstants;
import javax.swing.*;

public class Form extends JFrame {
    // Constructor to set up the form
    public Form(String title) {
        // Set the title of the window
        super(title);

        // Set the size of the window
        setSize(1100, 680);

        // Close the program when the window is closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Use absolute positioning for components (no layout manager)
        setLayout(null);

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Prevent the window from being resized
        setResizable(false);

        // Set the background color of the window
        getContentPane().setBackground(CommonConstants.PRIMARY_COLOR);
    }
}
