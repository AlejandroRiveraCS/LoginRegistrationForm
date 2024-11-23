package db;

import constants.CommonConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        // Connection details
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/login_schema"; // Replace with srv826.hstgr.io if preferred
        String username = "root"; // Replace with your actual MySQL username
        String password = "Samantha21877!"; // Replace with your actual MySQL password

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        }
    }
}

