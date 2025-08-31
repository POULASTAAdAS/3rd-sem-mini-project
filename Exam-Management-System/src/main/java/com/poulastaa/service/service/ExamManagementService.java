package com.poulastaa.service.service;

import com.poulastaa.service.configuration.DataInitializer;
import com.poulastaa.service.configuration.HibernateConfig;
import com.poulastaa.service.database.entity.EntityCourse;
import com.poulastaa.service.database.entity.EntityStudent;
import com.poulastaa.service.database.entity.StudentCourse;
import com.poulastaa.service.database.repository.DaoCourse;
import com.poulastaa.service.database.repository.DaoStudent;
import com.poulastaa.service.database.repository.DaoStudentCourse;
import com.poulastaa.service.model.dto.DtoCourse;
import com.poulastaa.service.model.dto.DtoStudent;

import java.util.List;

public class ExamManagementService {
    private static ExamManagementService instance;

    private static DaoStudent daoStudent;
    private static DaoCourse daoCourse;
    private static DaoStudentCourse daoStudentCourse;

    private ExamManagementService() {
        // Initialize Hibernate SessionFactory
        HibernateConfig.getSessionFactory(); // This will trigger static initialization

        // Initialize default data
        DataInitializer.initialize();

        daoStudent = DaoStudent.instance();
        daoCourse = DaoCourse.instance();
        daoStudentCourse = DaoStudentCourse.instance();

        System.out.println("ExamManagementService Instance created with Hibernate");
    }

    // Student operations
    public EntityStudent insertStudent(DtoStudent student) {
        return daoStudent.insert(student);
    }

    public List<EntityStudent> getAllStudents() {
        return daoStudent.getAll();
    }

    public boolean deleteStudentById(int id) {
        return daoStudent.deleteById(id);
    }

    public boolean updateStudentAgeById(int id, int age) {
        return daoStudent.updateAgeById(id, age);
    }

    // Course operations
    public EntityCourse insertCourse(DtoCourse course) {
        return daoCourse.insert(course);
    }

    public List<EntityCourse> getAllCourses() {
        return daoCourse.getAll();
    }

    public boolean deleteCourseById(int id) {
        return daoCourse.deleteById(id);
    }

    public EntityCourse findCourseById(int id) {
        return daoCourse.findById(id);
    }

    // Student-Course relationship operations
    public boolean enrollStudentInCourse(int studentId, int courseId) {
        return daoStudentCourse.enrollStudentInCourse(studentId, courseId);
    }

    public List<EntityCourse> getCoursesByStudentId(int studentId) {
        return daoStudentCourse.getCoursesByStudentId(studentId);
    }

    public List<EntityStudent> getStudentsByCourseId(int courseId) {
        return daoStudentCourse.getStudentsByCourseId(courseId);
    }

    public boolean removeStudentFromCourse(int studentId, int courseId) {
        return daoStudentCourse.removeStudentFromCourse(studentId, courseId);
    }

    public List<StudentCourse> getAllEnrollments() {
        return daoStudentCourse.getAllEnrollments();
    }

    // Cleanup method
    public void shutdown() {
        HibernateConfig.shutdown();
    }

    public static ExamManagementService instance() {
        if (instance == null) instance = new ExamManagementService();
        return instance;
    }
}