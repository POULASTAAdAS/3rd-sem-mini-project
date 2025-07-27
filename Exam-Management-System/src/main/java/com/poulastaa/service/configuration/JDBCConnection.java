package com.poulastaa.service.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnection {
    private static final String SERVER_URL = "jdbc:mysql://localhost:3306/";
    private static final String URL = "jdbc:mysql://localhost:3306/exam";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";

    private static JDBCConnection instance;
    private static Connection connection;

    private JDBCConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = serverConnection();
                 Statement st = con.createStatement();) {
                st.execute("CREATE DATABASE IF NOT EXISTS exam");
            } catch (SQLException e) {
                System.out.println("Failed to create connection to database: " + e.getMessage());
                System.exit(1);
            }

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connection established successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
            System.exit(1);
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            System.exit(1);
        }
    }

    private static synchronized JDBCConnection getInstance() {
        if (instance == null) instance = new JDBCConnection();
        return instance;
    }

    private static Connection serverConnection() throws SQLException {
        return DriverManager.getConnection(SERVER_URL, USERNAME, PASSWORD);
    }

    private Connection createConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            System.out.println("Connection was closed. Creating new connection...");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return connection;
    }

    public static Connection connection() throws SQLException {
        return getInstance().createConnection();
    }

    public static void closeConnection() {
        if (connection != null) try {
            connection.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
