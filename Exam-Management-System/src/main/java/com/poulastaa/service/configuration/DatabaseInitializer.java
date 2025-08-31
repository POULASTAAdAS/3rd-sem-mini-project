package com.poulastaa.service.configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static DatabaseInitializer instance;

    private final static String student = "CREATE TABLE IF NOT EXISTS student (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "roll VARCHAR(20) NOT NULL," +
            "title VARCHAR(100) NOT NULL," +
            "age INT NOT NULL," +
            "bDate DATE NOT NULL" +
            ")";

    private final static String course = "CREATE TABLE IF NOT EXISTS course (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "title VARCHAR(100) NOT NULL," +
            "code VARCHAR(20) UNIQUE NOT NULL," +
            "duration INT NOT NULL" +
            ")";

    private DatabaseInitializer() {
        try (Connection con = JDBCConnection.connection();
             Statement st = con.createStatement()) {

            st.executeUpdate(student);
            System.out.println("Students table created successfully!");

            st.executeUpdate(course);
            System.out.println("courses table created successfully!");
        } catch (SQLException e) {
            System.out.println("Error Initializing Database");
            System.out.println(e.getMessage());
        }
    }

    public static void instance() {
        if (instance == null) instance = new DatabaseInitializer();
    }
}
