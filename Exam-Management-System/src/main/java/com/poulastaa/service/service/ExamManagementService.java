package com.poulastaa.service.service;

import com.poulastaa.service.configuration.DatabaseInitializer;
import com.poulastaa.service.database.entity.EntityStudent;
import com.poulastaa.service.database.entity.EntitySubject;
import com.poulastaa.service.database.repository.DaoStudent;
import com.poulastaa.service.database.repository.DaoSubject;
import com.poulastaa.service.model.dto.DtoStudent;
import com.poulastaa.service.model.dto.DtoSubject;

import java.util.List;

public class ExamManagementService {
    private static ExamManagementService instance;

    private static DaoStudent daoStudent;
    private static DaoSubject daoSubject;

    private ExamManagementService() {
        DatabaseInitializer.instance();
        daoStudent = DaoStudent.instance();
        daoSubject = DaoSubject.instance();

        System.out.println("ExamManagementService Instance are created");
    }

    // student
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

    // subject
    public EntitySubject insertSubject(DtoSubject subject) {
        return daoSubject.insert(subject);
    }

    public List<EntitySubject> getAllSubjects() {
        return daoSubject.getAll();
    }

    public static ExamManagementService instance() {
        if (instance == null) instance = new ExamManagementService();
        return instance;
    }
}
