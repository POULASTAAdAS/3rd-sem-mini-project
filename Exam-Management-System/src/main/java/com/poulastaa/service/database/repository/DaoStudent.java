package com.poulastaa.service.database.repository;

import com.poulastaa.service.configuration.JDBCConnection;
import com.poulastaa.service.database.entity.EntityStudent;
import com.poulastaa.service.model.dto.DtoStudent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoStudent {
    private static DaoStudent instance;

    private static final String insert = "INSERT INTO student (roll, name, age , bDate) VALUES (?, ?, ?, ?)";
    private static final String all = "SELECT * FROM student";
    private static final String delete = "DELETE FROM student WHERE id = ?";
    final String updateAgeById = "UPDATE student SET age = ? WHERE id = ?";

    public EntityStudent insert(DtoStudent student) {
        try (Connection con = JDBCConnection.connection();
             PreparedStatement statement = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, student.roll());
            statement.setString(2, student.name());
            statement.setInt(3, student.age());
            statement.setDate(4, student.bDate());

            int isDone = statement.executeUpdate();

            if (isDone > 0) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        EntityStudent newStudent = student.withId(id);
                        System.out.println("Inserted: " + newStudent);

                        return newStudent;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Student Insertion Failed");
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<EntityStudent> getAll() {
        List<EntityStudent> students = new ArrayList<>();

        try (Connection con = JDBCConnection.connection();
             PreparedStatement st = con.prepareStatement(all);
             ResultSet rs = st.executeQuery(all)) {
            while (rs.next()) {
                students.add(
                        new EntityStudent(
                                rs.getInt("id"),
                                rs.getString("roll"),
                                rs.getString("name"),
                                rs.getInt("age"),
                                rs.getDate("bDate")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println("Getting All Student Failed");
            System.out.println(e.getMessage());
        }

        return students;
    }

    public boolean deleteById(int id) {
        try (Connection con = JDBCConnection.connection();
             PreparedStatement st = con.prepareStatement(delete)) {
            st.setInt(1, id);
            int isDone = st.executeUpdate();

            if (isDone > 0) {
                System.out.println("Deleted Student with ID: " + id);
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Deleting Student Failed");
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean updateAgeById(int id, int age) {
        try (Connection con = JDBCConnection.connection();
             PreparedStatement st = con.prepareStatement(updateAgeById)) {
            st.setInt(1, age);
            st.setInt(2, id);

            int isDone = st.executeUpdate();
            if (isDone > 0) {
                System.out.println("Updated Student with ID: " + id);
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Update Student Failed");
            System.out.println(e.getMessage());
        }

        return false;
    }

    private DaoStudent() {
        System.out.println("Student Dao Created");
    }

    public static DaoStudent instance() {
        if (instance == null) instance = new DaoStudent();
        return instance;
    }
}
