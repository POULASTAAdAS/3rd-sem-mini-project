package com.poulastaa.service.database.repository;

import com.poulastaa.service.configuration.HibernateConfig;
import com.poulastaa.service.database.entity.EntityCourse;
import com.poulastaa.service.model.dto.DtoCourse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DaoCourse {
    private static DaoCourse instance;
    private final SessionFactory sessionFactory;

    private DaoCourse() {
        this.sessionFactory = HibernateConfig.getSessionFactory();
        System.out.println("Course Dao Created");
    }

    public EntityCourse insert(DtoCourse course) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            EntityCourse entityCourse = new EntityCourse(
                    course.title(),
                    course.code(),
                    course.duration()
            );

            session.persist(entityCourse);
            transaction.commit();

            System.out.println("Inserted Course with ID: " + entityCourse.getId());
            return entityCourse;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Course Insertion Failed");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public EntityCourse insertEntity(EntityCourse course) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.persist(course);
            transaction.commit();

            System.out.println("Inserted Course: " + course.getName() + " with ID: " + course.getId());
            return course;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Course Insertion Failed");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<EntityCourse> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<EntityCourse> query = session.createQuery("FROM EntityCourse", EntityCourse.class);
            return query.list();
        } catch (Exception e) {
            System.out.println("Getting All Courses Failed");
            System.out.println(e.getMessage());
            return List.of();
        }
    }

    public boolean deleteById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            EntityCourse course = session.find(EntityCourse.class, id);
            if (course != null) {
                session.remove(course);
                transaction.commit();
                System.out.println("Deleted Course with ID: " + id);
                return true;
            } else {
                System.out.println("Course with ID " + id + " not found");
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Deleting Course Failed");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public EntityCourse findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(EntityCourse.class, id);
        } catch (Exception e) {
            System.out.println("Finding Course by ID Failed");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static DaoCourse instance() {
        if (instance == null) instance = new DaoCourse();
        return instance;
    }
}