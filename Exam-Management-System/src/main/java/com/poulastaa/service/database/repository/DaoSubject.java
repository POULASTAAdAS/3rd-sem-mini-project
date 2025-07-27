package com.poulastaa.service.database.repository;

import com.poulastaa.service.configuration.JDBCConnection;
import com.poulastaa.service.database.entity.EntitySubject;
import com.poulastaa.service.model.dto.DtoSubject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoSubject {
    private static DaoSubject instance;

    private static final String insert = "INSERT INTO subjects (name, code, credits) VALUES (?, ?, ?)";
    private static final String all = "SELECT * FROM subject";

    public EntitySubject insert(DtoSubject subject) {
        try (Connection conn = JDBCConnection.connection();
             PreparedStatement st = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, subject.name());
            st.setString(2, subject.code());
            st.setDouble(3, subject.credits());

            int isDone = st.executeUpdate();
            if (isDone > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int subjectId = generatedKeys.getInt(1);
                            EntitySubject newSubject = subject.withId(subjectId);
                            System.out.println("Inserted Subject with ID: " + subjectId);

                            return newSubject;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Subject Insertion Failed");
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<EntitySubject> getAll() {
        List<EntitySubject> subjects = new ArrayList<>();

        try (Connection conn = JDBCConnection.connection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(all)) {
            while (rs.next()) {
                subjects.add(
                        new EntitySubject(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("code"),
                                rs.getDouble("credits")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println("Getting All Subject Failed");
            System.out.println(e.getMessage());
        }

        return subjects;
    }

    public static DaoSubject instance() {
        if (instance == null) instance = new DaoSubject();
        return instance;
    }
}
