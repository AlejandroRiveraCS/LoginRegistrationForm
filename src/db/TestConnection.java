package db;

import constants.CommonConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        // Connection details
        String dbUrl = "jdbc:mysql://127.0.0.1:3306/login_schema";
        String username = "root";
        String password = "Samantha21877!";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            e.printStackTrace();
        }
    }
}

