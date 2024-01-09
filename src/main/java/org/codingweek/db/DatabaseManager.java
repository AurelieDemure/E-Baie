package org.codingweek.db;

import org.codingweek.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class DatabaseManager {
    private SessionFactory factory;

    public void setup() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Offer.class)
                .addAnnotatedClass(Chat.class)
                .addAnnotatedClass(Notification.class)
                .addAnnotatedClass(Query.class)
                .setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC")
                .setProperty("hibernate.connection.url", "jdbc:sqlite:src/main/resources/org/codingweek/db/database.sqlite")
                .setProperty("hibernate.dialect", "org.codingweek.db.SQLiteDialect")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "update");

        factory = configuration.buildSessionFactory();
    }

    public <T> void saveEntity(T entity) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public <T> void updateEntity(T entity) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public <T> void deleteEntity(T entity) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public <T> T getEntity(Class<T> entityClass, int id) {
        Session session = factory.openSession();
        Transaction tx = null;
        T entity = null;
        try {
            tx = session.beginTransaction();
            entity = (T) session.get(entityClass, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return entity;
        }
    }

    public void tearDown() {
        factory.close();
    }

    public void deleteAll() {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.createQuery("delete from Offer").executeUpdate();
            session.createQuery("delete from Chat").executeUpdate();
            session.createQuery("delete from Notification").executeUpdate();
            session.createQuery("delete from Query").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void dump() {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.createQuery("from User").list().forEach(System.out::println);
            session.createQuery("from Offer").list().forEach(System.out::println);
            session.createQuery("from Chat").list().forEach(System.out::println);
            session.createQuery("from Notification").list().forEach(System.out::println);
            session.createQuery("from Query").list().forEach(System.out::println);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
