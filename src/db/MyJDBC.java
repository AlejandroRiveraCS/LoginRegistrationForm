package db;

import constants.CommonConstants;

import java.sql.*;

// Class to handle interactions with the MySQL database
public class MyJDBC {
    // Register a new user in the database
    // Returns true if registration is successful, false otherwise
    public static boolean register(String username, String password) {
        // Check if the username already exists
        if (!checkUser(username)) {
            try (Connection connection = DriverManager.getConnection(
                    CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
                 PreparedStatement insertUser = connection.prepareStatement(
                         "INSERT INTO " + CommonConstants.DB_USERS_TABLE_NAME + " (username, password) VALUES (?, ?)"
                 )) {
                // Set the username and password parameters
                insertUser.setString(1, username.trim());
                insertUser.setString(2, password.trim());

                // Execute the insert operation
                insertUser.executeUpdate();
                return true; // Return true if the user is registered successfully
            } catch (SQLException e) {
                e.printStackTrace(); // Print error details if registration fails
            }
        }
        return false; // Return false if the username already exists or an error occurs
    }

    // Check if a username already exists in the database
    // Returns true if the username is found, false otherwise
    public static boolean checkUser(String username) {
        try (Connection connection = DriverManager.getConnection(
                CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
             PreparedStatement checkUserExists = connection.prepareStatement(
                     "SELECT * FROM " + CommonConstants.DB_USERS_TABLE_NAME + " WHERE LOWER(username) = LOWER(?)"
             )) {
            // Set the username parameter
            checkUserExists.setString(1, username.trim());

            // Execute the query and check for results
            try (ResultSet resultSet = checkUserExists.executeQuery()) {
                return resultSet.next(); // Return true if a matching record is found
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print error details if an exception occurs
        }
        return false; // Return false if no match is found or an error occurs
    }

    // Validate login credentials by checking the username and password in the database
    // Returns true if the credentials are valid, false otherwise
    public static boolean validateLogin(String username, String password) {
        try (Connection connection = DriverManager.getConnection(
                CommonConstants.DB_URL, CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);
             PreparedStatement validateUser = connection.prepareStatement(
                     "SELECT * FROM " + CommonConstants.DB_USERS_TABLE_NAME + " WHERE LOWER(username) = LOWER(?) AND password = ?"
             )) {
            // Set the username and password parameters
            validateUser.setString(1, username.trim());
            validateUser.setString(2, password.trim());

            // Execute the query and check for a matching record
            try (ResultSet resultSet = validateUser.executeQuery()) {
                return resultSet.next(); // Return true if the credentials are valid
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print error details if an exception occurs
        }
        return false; // Return false if no match is found or an error occurs
    }
}














