package com.poulastaa.service;

import com.poulastaa.service.configuration.JDBCConnection;
import com.poulastaa.service.database.entity.EntityStudent;
import com.poulastaa.service.database.entity.EntitySubject;
import com.poulastaa.service.model.dto.DtoStudent;
import com.poulastaa.service.model.dto.DtoSubject;
import com.poulastaa.service.service.ExamManagementService;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ExamManagementService service = ExamManagementService.instance();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("    EXAM MANAGEMENT SYSTEM - WELCOME!    ");
        System.out.println("===========================================");

        boolean flag = true;

        while (flag) {
            displayMainMenu();

            try {
                int choice = Integer.parseInt(sc.nextLine().trim());

                switch (choice) {
                    case 1 -> studentOperations();
                    case 2 -> subjectOperations();
                    case 3 -> displayAllData();
                    case 0 -> {
                        flag = false;
                        JDBCConnection.closeConnection();
                        System.out.println("\n📤 Exiting Exam Management System...");
                        System.out.println("Thank you for using our system. Goodbye!");
                    }
                    default -> System.out.println("❌ Invalid choice! Please select a valid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a number.");
            } catch (Exception e) {
                System.out.println("❌ An error occurred: " + e.getMessage());
            }
        }

        sc.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. 👨‍🎓 Student Operations");
        System.out.println("2. 📚 Subject Operations");
        System.out.println("3. 📊 Display All Data");
        System.out.println("0. 🚪 Exit");
        System.out.println("==============================");
        System.out.print("Enter your choice: ");
    }

    private static void studentOperations() {
        boolean menu = true;

        while (menu) {
            System.out.println("\n===== STUDENT OPERATIONS =====");
            System.out.println("1. ➕ Add New Student");
            System.out.println("2. 📋 View All Students");
            System.out.println("3. 🔄 Update Student Age");
            System.out.println("4. ❌ Delete Student");
            System.out.println("0. ↩️  Back to Main Menu");
            System.out.println("=============================");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> addNewStudent();
                    case 2 -> viewAllStudents();
                    case 3 -> updateStudentAge();
                    case 4 -> deleteStudent();
                    case 0 -> menu = false;
                    default -> System.out.println("❌ Invalid choice! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a number.");
            }
        }
    }

    private static void addNewStudent() {
        System.out.println("\n--- Add New Student ---");

        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter student roll: ");
        String roll = sc.nextLine().trim();

        System.out.print("Enter student age: ");
        int age = Integer.parseInt(sc.nextLine());

        Date date = Date.valueOf("2003-01-13");
        System.out.print("Student BDate: " + date);

        DtoStudent dtoStudent = new DtoStudent(roll, name, age, date);

        EntityStudent savedStudent = service.insertStudent(dtoStudent);

        if (savedStudent != null) {
            System.out.println("✅ Student added successfully!");
            System.out.println("Student Details: " + savedStudent);
        } else {
            System.out.println("❌ Failed to add student!");
        }
    }

    private static void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        List<EntityStudent> students = service.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("📭 No students found in the database.");
        } else {
            System.out.println("Total Students: " + students.size());
            System.out.println("----------------------------------------");
            for (int i = 0; i < students.size(); i++)
                System.out.println((i + 1) + ". " + students.get(i));
            System.out.println("----------------------------------------");
        }
    }

    private static void updateStudentAge() {
        System.out.println("\n--- Update Student Age ---");

        System.out.print("Enter student ID: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Enter new age: ");
        int newAge = Integer.parseInt(sc.nextLine());

        if (service.updateStudentAgeById(id, newAge)) System.out.println("✅ Student age updated successfully!");
        else System.out.println("❌ Failed to update student age. Student ID might not exist.");
    }

    private static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");

        System.out.print("Enter student ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Are you sure you want to delete this student? (yes/no): ");
        String confirmation = sc.nextLine().trim().toLowerCase();

        if (confirmation.equals("yes") || confirmation.equals("y")) {
            boolean deleted = service.deleteStudentById(id);

            if (deleted) System.out.println("✅ Student deleted successfully!");
            else System.out.println("❌ Failed to delete student. Student ID might not exist.");
        } else System.out.println("❌ Deletion cancelled.");
    }

    private static void subjectOperations() {
        boolean menu = true;

        while (menu) {
            System.out.println("\n===== SUBJECT OPERATIONS =====");
            System.out.println("1. ➕ Add New Subject");
            System.out.println("2. 📋 View All Subjects");
            System.out.println("0. ↩️  Back to Main Menu");
            System.out.println("=============================");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> addNewSubject();
                    case 2 -> viewAllSubjects();
                    case 0 -> menu = false;
                    default -> System.out.println("❌ Invalid choice! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a number.");
            }
        }
    }

    private static void addNewSubject() {
        System.out.println("\n--- Add New Subject ---");

        System.out.print("Enter subject name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter subject code: ");
        String code = sc.nextLine().trim();

        System.out.print("Enter credits: ");
        double credits = Double.parseDouble(sc.nextLine());

        EntitySubject savedSubject = service.insertSubject(
                new DtoSubject(
                        name,
                        code,
                        credits
                )
        );

        if (savedSubject != null) {
            System.out.println("✅ Subject added successfully!");
            System.out.println("Subject Details: " + savedSubject);
        } else {
            System.out.println("❌ Failed to add subject!");
        }
    }

    private static void viewAllSubjects() {
        System.out.println("\n--- All Subjects ---");
        List<EntitySubject> subjects = service.getAllSubjects();

        if (subjects.isEmpty()) {
            System.out.println("📭 No subjects found in the database.");
        } else {
            System.out.println("Total Subjects: " + subjects.size());
            System.out.println("----------------------------------------");
            for (int i = 0; i < subjects.size(); i++) {
                System.out.println((i + 1) + ". " + subjects.get(i));
            }
            System.out.println("----------------------------------------");
        }
    }

    private static void displayAllData() {
        System.out.println("\n========== ALL DATA SUMMARY ==========");

        System.out.println("\n📚 STUDENTS:");
        viewAllStudents();

        System.out.println("\n📖 SUBJECTS:");
        viewAllSubjects();

        System.out.println("\n=====================================");
    }
}
