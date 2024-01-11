package org.codingweek.db;

import org.codingweek.db.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;

public class DatabaseManager {
    private SessionFactory factory;

    private String databaseName = "database.sqlite";

    private final String databaseFolder = "codingweek-11";

    private final String userHome = System.getProperty("user.home");

    private void createFolder() {
        File folder = new File(userHome, databaseFolder);
        if (!folder.exists()) {
            if (!folder.mkdir()) {
                throw new RuntimeException("Cannot create folder");
            } else {
                System.out.println("Folder created");
            }
        }
    }

    private void createFile() {
        File file = new File(userHome + "/" + databaseFolder + "/" + databaseName);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new RuntimeException("Cannot create file");
                } else {
                    System.out.println("File created");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void setup() {
        createFolder();
        createFile();

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Configuration configuration = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Offer.class)
                .addAnnotatedClass(Chat.class)
                .addAnnotatedClass(Notification.class)
                .addAnnotatedClass(Query.class)
                .setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC")
                .setProperty("hibernate.connection.url", "jdbc:sqlite:" + Paths.get(userHome, databaseFolder, databaseName))
                .setProperty("hibernate.dialect", "org.codingweek.db.SQLiteDialect")
                .setProperty("hibernate.show_sql", "false")
                .setProperty("hibernate.hbm2ddl.auto", "update")
                .setProperty("hibernate.classLoader.application", "org.hibernate.boot.registry.classloading.internal.ClassLoaderServiceImpl");

        factory = configuration.buildSessionFactory();
    }

    public void setupTest() {
        databaseName = "database_test.sqlite";
        setup();
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

    public <T> List<T> getAllEntity(Class<T> entityClass) {
        Session session = factory.openSession();
        Transaction tx = null;
        List entity = null;
        try {
            tx = session.beginTransaction();
            entity =  session.createQuery("FROM " + entityClass.getName()).list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            return entity;
        }
    }


    public <T> T getEntity(Class<T> entityClass, String email) {
        Session session = factory.openSession();
        Transaction tx = null;
        T entity = null;
        try {
            tx = session.beginTransaction();
            entity = (T) session.get(entityClass, email);
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
