package com.poulastaa.service;

import com.poulastaa.service.database.entity.EntityCourse;
import com.poulastaa.service.database.entity.EntityStudent;
import com.poulastaa.service.database.entity.StudentCourse;
import com.poulastaa.service.model.dto.DtoCourse;
import com.poulastaa.service.model.dto.DtoStudent;
import com.poulastaa.service.service.ExamManagementService;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ExamManagementService service = ExamManagementService.instance();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(1500);

        System.out.println("===========================================");
        System.out.println("     EXAM MANAGEMENT SYSTEM - WELCOME!     ");
        System.out.println("===========================================");

        boolean flag = true;

        while (flag) {
            displayMainMenu();

            try {
                int choice = Integer.parseInt(sc.nextLine().trim());

                switch (choice) {
                    case 1 -> studentOperations();
                    case 2 -> courseOperations();
                    case 3 -> enrollmentOperations();
                    case 4 -> displayAllData();
                    case 0 -> {
                        flag = false;
                        service.shutdown();
                        System.out.println("\n Exiting Exam Management System...");
                        System.out.println("Thank you for using our system. Goodbye!");
                    }
                    default -> System.out.println("Invalid choice! Please select a valid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        sc.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Student Operations");
        System.out.println("2. Course Operations");
        System.out.println("3. Enrollment Operations");
        System.out.println("4. Display All Data");
        System.out.println("0. Exit");
        System.out.println("==============================");
        System.out.print("Enter your choice: ");
    }

    private static void studentOperations() {
        boolean menu = true;

        while (menu) {
            System.out.println("\n===== STUDENT OPERATIONS =====");
            System.out.println("1. Add New Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student Age");
            System.out.println("4. Delete Student");
            System.out.println("0. Back to Main Menu");
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
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
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
        System.out.println("Student BDate: " + date);

        DtoStudent dtoStudent = new DtoStudent(roll, name, age, date);

        EntityStudent savedStudent = service.insertStudent(dtoStudent);

        if (savedStudent != null) {
            System.out.println("Student added successfully!");
            System.out.println("Student Details: " + savedStudent);

            // After adding student, ask to enroll in courses
            enrollStudentInCourses(savedStudent.getId());
        } else {
            System.out.println("Failed to add student!");
        }
    }

    private static void enrollStudentInCourses(int studentId) {
        System.out.println("\n--- Enroll Student in Courses ---");
        List<EntityCourse> courses = service.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses available for enrollment.");
            return;
        }

        System.out.println("Available Courses:");
        System.out.println("0. Skip course enrollment");
        for (int i = 0; i < courses.size(); i++) {
            EntityCourse course = courses.get(i);
            System.out.println((i + 1) + ". " + course.getName() + " (" + course.getCode() + ") - " + course.getDuration() + " years");
        }

        boolean enrolling = true;
        while (enrolling) {
            System.out.print("\nSelect course to enroll (0 to finish): ");
            try {
                int choice = Integer.parseInt(sc.nextLine());

                if (choice == 0) {
                    enrolling = false;
                } else if (choice > 0 && choice <= courses.size()) {
                    EntityCourse selectedCourse = courses.get(choice - 1);
                    boolean enrolled = service.enrollStudentInCourse(studentId, selectedCourse.getId());
                    if (enrolled) {
                        System.out.println("Successfully enrolled in " + selectedCourse.getName());
                    } else {
                        System.out.println("Failed to enroll in " + selectedCourse.getName());
                    }
                } else {
                    System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private static void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        List<EntityStudent> students = service.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found in the database.");
        } else {
            System.out.println("Total Students: " + students.size());
            System.out.println("----------------------------------------");
            for (int i = 0; i < students.size(); i++) {
                EntityStudent student = students.get(i);
                System.out.println((i + 1) + ". " + student);

                // Show enrolled courses
                List<EntityCourse> enrolledCourses = service.getCoursesByStudentId(student.getId());
                if (!enrolledCourses.isEmpty()) {
                    System.out.print("   Enrolled Courses: ");
                    for (int j = 0; j < enrolledCourses.size(); j++) {
                        System.out.print(enrolledCourses.get(j).getName());
                        if (j < enrolledCourses.size() - 1) System.out.print(", ");
                    }
                    System.out.println();
                }
            }
            System.out.println("----------------------------------------");
        }
    }

    private static void updateStudentAge() {
        System.out.println("\n--- Update Student Age ---");

        System.out.print("Enter student ID: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Enter new age: ");
        int newAge = Integer.parseInt(sc.nextLine());

        if (service.updateStudentAgeById(id, newAge)) {
            System.out.println("Student age updated successfully!");
        } else {
            System.out.println("Failed to update student age. Student ID might not exist.");
        }
    }

    private static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");

        System.out.print("Enter student ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Are you sure you want to delete this student? (yes/no): ");
        String confirmation = sc.nextLine().trim().toLowerCase();

        if (confirmation.equals("yes") || confirmation.equals("y")) {
            boolean deleted = service.deleteStudentById(id);

            if (deleted) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Failed to delete student. Student ID might not exist.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void courseOperations() {
        boolean menu = true;

        while (menu) {
            System.out.println("\n===== COURSE OPERATIONS =====");
            System.out.println("1. Add New Course");
            System.out.println("2. View All Courses");
            System.out.println("3. View Students in Course");
            System.out.println("4. Delete Course");
            System.out.println("0. Back to Main Menu");
            System.out.println("=============================");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> addNewCourse();
                    case 2 -> viewAllCourses();
                    case 3 -> viewStudentsInCourse();
                    case 4 -> deleteCourse();
                    case 0 -> menu = false;
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private static void addNewCourse() {
        System.out.println("\n--- Add New Course ---");

        System.out.print("Enter course name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter course code: ");
        String code = sc.nextLine().trim();

        System.out.print("Enter duration (in years): ");
        int duration = Integer.parseInt(sc.nextLine());

        EntityCourse savedCourse = service.insertCourse(
                new DtoCourse(name, code, duration)
        );

        if (savedCourse != null) {
            System.out.println("Course added successfully!");
            System.out.println("Course Details: " + savedCourse);
        } else {
            System.out.println("Failed to add course!");
        }
    }

    private static void viewAllCourses() {
        System.out.println("\n--- All Courses ---");
        List<EntityCourse> courses = service.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses found in the database.");
        } else {
            System.out.println("Total Courses: " + courses.size());
            System.out.println("----------------------------------------");
            for (int i = 0; i < courses.size(); i++) {
                EntityCourse course = courses.get(i);
                System.out.println((i + 1) + ". " + course);

                // Show enrolled students count
                List<EntityStudent> enrolledStudents = service.getStudentsByCourseId(course.getId());
                System.out.println("    Enrolled Students: " + enrolledStudents.size());
            }
            System.out.println("----------------------------------------");
        }
    }

    private static void viewStudentsInCourse() {
        System.out.println("\n--- Students in Course ---");

        List<EntityCourse> courses = service.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        System.out.println("Available Courses:");
        for (int i = 0; i < courses.size(); i++) {
            EntityCourse course = courses.get(i);
            System.out.println((i + 1) + ". " + course.getName() + " (" + course.getCode() + ")");
        }

        System.out.print("Select course (1-" + courses.size() + "): ");
        try {
            int choice = Integer.parseInt(sc.nextLine());

            if (choice > 0 && choice <= courses.size()) {
                EntityCourse selectedCourse = courses.get(choice - 1);
                List<EntityStudent> students = service.getStudentsByCourseId(selectedCourse.getId());

                System.out.println("\nStudents enrolled in " + selectedCourse.getName() + ":");
                if (students.isEmpty()) {
                    System.out.println("No students enrolled in this course.");
                } else {
                    for (int i = 0; i < students.size(); i++) {
                        System.out.println((i + 1) + ". " + students.get(i));
                    }
                }
            } else {
                System.out.println("Invalid choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
        }
    }

    private static void deleteCourse() {
        System.out.println("\n--- Delete Course ---");

        System.out.print("Enter course ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Are you sure you want to delete this course? (yes/no): ");
        String confirmation = sc.nextLine().trim().toLowerCase();

        if (confirmation.equals("yes") || confirmation.equals("y")) {
            boolean deleted = service.deleteCourseById(id);

            if (deleted) {
                System.out.println("Course deleted successfully!");
            } else {
                System.out.println("Failed to delete course. Course ID might not exist.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void enrollmentOperations() {
        boolean menu = true;

        while (menu) {
            System.out.println("\n===== ENROLLMENT OPERATIONS =====");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. Remove Student from Course");
            System.out.println("3. View Student's Courses");
            System.out.println("4. View All Enrollments");
            System.out.println("0.  Back to Main Menu");
            System.out.println("=================================");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> enrollStudentInCourse();
                    case 2 -> removeStudentFromCourse();
                    case 3 -> viewStudentCourses();
                    case 4 -> viewAllEnrollments();
                    case 0 -> menu = false;
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    private static void enrollStudentInCourse() {
        System.out.println("\n--- Enroll Student in Course ---");

        // Show all students
        List<EntityStudent> students = service.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        System.out.println("Available Students:");
        for (int i = 0; i < students.size(); i++) {
            EntityStudent student = students.get(i);
            System.out.println((i + 1) + ". " + student.getName() + " (ID: " + student.getId() + ")");
        }

        System.out.print("Select student (1-" + students.size() + "): ");
        try {
            int studentChoice = Integer.parseInt(sc.nextLine());

            if (studentChoice > 0 && studentChoice <= students.size()) {
                EntityStudent selectedStudent = students.get(studentChoice - 1);

                // Show available courses
                List<EntityCourse> courses = service.getAllCourses();
                if (courses.isEmpty()) {
                    System.out.println("No courses available.");
                    return;
                }

                System.out.println("\nAvailable Courses:");
                for (int i = 0; i < courses.size(); i++) {
                    EntityCourse course = courses.get(i);
                    System.out.println((i + 1) + ". " + course.getName() + " (" + course.getCode() + ")");
                }

                System.out.print("Select course (1-" + courses.size() + "): ");
                int courseChoice = Integer.parseInt(sc.nextLine());

                if (courseChoice > 0 && courseChoice <= courses.size()) {
                    EntityCourse selectedCourse = courses.get(courseChoice - 1);

                    boolean enrolled = service.enrollStudentInCourse(selectedStudent.getId(), selectedCourse.getId());
                    if (enrolled) {
                        System.out.println("Successfully enrolled " + selectedStudent.getName() + " in " + selectedCourse.getName());
                    } else {
                        System.out.println("Failed to enroll student in course.");
                    }
                } else {
                    System.out.println("Invalid course choice!");
                }
            } else {
                System.out.println("Invalid student choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
        }
    }

    private static void removeStudentFromCourse() {
        System.out.println("\n--- Remove Student from Course ---");

        System.out.print("Enter student ID: ");
        int studentId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter course ID: ");
        int courseId = Integer.parseInt(sc.nextLine());

        boolean removed = service.removeStudentFromCourse(studentId, courseId);
        if (removed) {
            System.out.println("Student removed from course successfully!");
        } else {
            System.out.println("Failed to remove student from course.");
        }
    }

    private static void viewStudentCourses() {
        System.out.println("\n--- View Student's Courses ---");

        System.out.print("Enter student ID: ");
        int studentId = Integer.parseInt(sc.nextLine());

        List<EntityCourse> courses = service.getCoursesByStudentId(studentId);
        if (courses.isEmpty()) {
            System.out.println("Student is not enrolled in any courses.");
        } else {
            System.out.println("Courses enrolled by student ID " + studentId + ":");
            for (int i = 0; i < courses.size(); i++) {
                System.out.println((i + 1) + ". " + courses.get(i));
            }
        }
    }

    private static void viewAllEnrollments() {
        System.out.println("\n--- All Enrollments ---");
        List<StudentCourse> enrollments = service.getAllEnrollments();

        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
        } else {
            System.out.println("Total Enrollments: " + enrollments.size());
            System.out.println("----------------------------------------");
            for (int i = 0; i < enrollments.size(); i++) {
                StudentCourse enrollment = enrollments.get(i);
                System.out.println((i + 1) + ". Student ID: " + enrollment.getStudentId() +
                        ", Course ID: " + enrollment.getCourseId());
            }
            System.out.println("----------------------------------------");
        }
    }

    private static void displayAllData() {
        System.out.println("\n========== ALL DATA SUMMARY ==========");

        System.out.println("\nSTUDENTS:");
        viewAllStudents();

        System.out.println("\nCOURSES:");
        viewAllCourses();

        System.out.println("\nENROLLMENTS:");
        viewAllEnrollments();

        System.out.println("\n=====================================");
    }
}