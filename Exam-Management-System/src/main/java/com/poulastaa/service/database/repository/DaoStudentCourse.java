package com.poulastaa.service.database.repository;

import com.poulastaa.service.configuration.HibernateConfig;
import com.poulastaa.service.database.entity.EntityCourse;
import com.poulastaa.service.database.entity.EntityStudent;
import com.poulastaa.service.database.entity.StudentCourse;
import com.poulastaa.service.database.entity.StudentCourseId;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DaoStudentCourse {
    private static DaoStudentCourse instance;
    private final SessionFactory sessionFactory;

    private DaoStudentCourse() {
        this.sessionFactory = HibernateConfig.getSessionFactory();
        System.out.println("StudentCourse Dao Created");
    }

    public boolean enrollStudentInCourse(int studentId, int courseId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Check if the relationship already exists
            StudentCourseId id = new StudentCourseId(studentId, courseId);
            StudentCourse existing = session.find(StudentCourse.class, id);

            if (existing != null) {
                System.out.println("Student is already enrolled in this course");
                return false;
            }

            // Check if student and course exist
            EntityStudent student = session.find(EntityStudent.class, studentId);
            EntityCourse course = session.find(EntityCourse.class, courseId);

            if (student == null) {
                System.out.println("Student with ID " + studentId + " not found");
                return false;
            }

            if (course == null) {
                System.out.println("Course with ID " + courseId + " not found");
                return false;
            }

            StudentCourse studentCourse = new StudentCourse(studentId, courseId);
            session.persist(studentCourse);
            transaction.commit();

            System.out.println("Student " + student.getName() + " enrolled in course " + course.getName());
            return true;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Failed to enroll student in course: " + e.getMessage());
            return false;
        }
    }

    public List<EntityCourse> getCoursesByStudentId(int studentId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT sc.course FROM StudentCourse sc WHERE sc.studentId = :studentId";
            Query<EntityCourse> query = session.createQuery(hql, EntityCourse.class);
            query.setParameter("studentId", studentId);
            return query.list();
        } catch (Exception e) {
            System.out.println("Failed to find courses for student: " + e.getMessage());
            return List.of();
        }
    }

    public List<EntityStudent> getStudentsByCourseId(int courseId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT sc.student FROM StudentCourse sc WHERE sc.courseId = :courseId";
            Query<EntityStudent> query = session.createQuery(hql, EntityStudent.class);
            query.setParameter("courseId", courseId);
            return query.list();
        } catch (Exception e) {
            System.out.println("Failed to find students for course: " + e.getMessage());
            return List.of();
        }
    }

    public boolean removeStudentFromCourse(int studentId, int courseId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            StudentCourseId id = new StudentCourseId(studentId, courseId);
            StudentCourse studentCourse = session.find(StudentCourse.class, id);

            if (studentCourse != null) {
                session.remove(studentCourse);
                transaction.commit();
                System.out.println("Student removed from course successfully");
                return true;
            } else {
                System.out.println("Student-Course relationship not found");
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Failed to remove student from course: " + e.getMessage());
            return false;
        }
    }

    public List<StudentCourse> getAllEnrollments() {
        try (Session session = sessionFactory.openSession()) {
            Query<StudentCourse> query = session.createQuery("FROM StudentCourse", StudentCourse.class);
            return query.list();
        } catch (Exception e) {
            System.out.println("Failed to find all enrollments: " + e.getMessage());
            return List.of();
        }
    }

    public static DaoStudentCourse instance() {
        if (instance == null) instance = new DaoStudentCourse();
        return instance;
    }
}