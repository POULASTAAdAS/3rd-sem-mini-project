package com.poulastaa.service.configuration;

import com.poulastaa.service.database.entity.EntityCourse;
import com.poulastaa.service.database.entity.EntityStudent;
import com.poulastaa.service.database.entity.StudentCourse;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty(
                    "hibernate.connection.url",
                    "jdbc:mysql://localhost:3306/exam?createDatabaseIfNotExist=true&serverTimezone=UTC"
            );

            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "password");

            // Hibernate properties
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");

            // Connection pool settings
            configuration.setProperty("hibernate.connection.pool_size", "10");
            configuration.setProperty("hibernate.current_session_context_class", "thread");

            // Add annotated entity classes
            configuration.addAnnotatedClass(EntityStudent.class);
            configuration.addAnnotatedClass(EntityCourse.class);
            configuration.addAnnotatedClass(StudentCourse.class);

            configuration.setProperty("hibernate.show_sql", "false");
            configuration.setProperty("hibernate.format_sql", "false");

            sessionFactory = configuration.buildSessionFactory();
            System.out.println("Hibernate SessionFactory created successfully!");

        } catch (Exception e) {
            System.err.println("Failed to create SessionFactory: " + e.getMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("Hibernate SessionFactory closed.");
        }
    }
}