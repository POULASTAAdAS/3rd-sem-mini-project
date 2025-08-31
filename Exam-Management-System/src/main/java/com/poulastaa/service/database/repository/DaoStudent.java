package com.poulastaa.service.database.repository;

import com.poulastaa.service.configuration.HibernateConfig;
import com.poulastaa.service.database.entity.EntityStudent;
import com.poulastaa.service.model.dto.DtoStudent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DaoStudent {
    private static DaoStudent instance;
    private final SessionFactory sessionFactory;

    private DaoStudent() {
        this.sessionFactory = HibernateConfig.getSessionFactory();
        System.out.println("Student Dao Created");
    }

    public EntityStudent insert(DtoStudent student) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            EntityStudent entityStudent = new EntityStudent(
                    student.roll(),
                    student.name(),
                    student.age(),
                    student.bDate()
            );

            session.persist(entityStudent);
            transaction.commit();

            System.out.println("Inserted: " + entityStudent);
            return entityStudent;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Student Insertion Failed");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<EntityStudent> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<EntityStudent> query = session.createQuery("FROM EntityStudent", EntityStudent.class);
            return query.list();
        } catch (Exception e) {
            System.out.println("Getting All Students Failed");
            System.out.println(e.getMessage());
            return List.of();
        }
    }

    public boolean deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            EntityStudent student = session.find(EntityStudent.class, id);
            if (student != null) {
                session.remove(student);
                transaction.commit();
                System.out.println("Deleted Student with ID: " + id);
                return true;
            } else {
                System.out.println("Student with ID " + id + " not found");
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Deleting Student Failed");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateAgeById(int id, int age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            EntityStudent student = session.find(EntityStudent.class, id);
            if (student != null) {
                student.setAge(age);
                session.merge(student);
                transaction.commit();
                System.out.println("Updated Student with ID: " + id);
                return true;
            } else {
                System.out.println("Student with ID " + id + " not found");
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Update Student Failed");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static DaoStudent instance() {
        if (instance == null) instance = new DaoStudent();
        return instance;
    }
}