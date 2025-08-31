package com.poulastaa.service.configuration;

import com.poulastaa.service.database.entity.EntityCourse;
import com.poulastaa.service.database.repository.DaoCourse;

import java.util.List;

public class DataInitializer {
    private static DataInitializer instance;

    private DataInitializer() {
        insertDefaultCourses();
    }

    private void insertDefaultCourses() {
        DaoCourse daoCourse = DaoCourse.instance();

        // Check if courses already exist
        List<EntityCourse> existingCourses = daoCourse.getAll();
        if (!existingCourses.isEmpty()) {
            System.out.println("Default courses already exist. Skipping initialization.");
            return;
        }

        // Insert default courses
        System.out.println("Inserting default courses...");

        try {
            // Course 1: MCA
            EntityCourse mcaCourse = new EntityCourse("MCA", "RCC-MCA", 2);
            daoCourse.insertEntity(mcaCourse);

            // Course 2: ECE
            EntityCourse eceCourse = new EntityCourse("ECE", "RCC-ECE", 4);
            daoCourse.insertEntity(eceCourse);

            // Course 3: CSE
            EntityCourse cseCourse = new EntityCourse("CSE", "RCC-CSE", 4);
            daoCourse.insertEntity(cseCourse);

            System.out.println("✅ Default courses inserted successfully!");

        } catch (Exception e) {
            System.out.println("❌ Failed to insert default courses: " + e.getMessage());
        }
    }

    public static void initialize() {
        if (instance == null) {
            instance = new DataInitializer();
        }
    }
}