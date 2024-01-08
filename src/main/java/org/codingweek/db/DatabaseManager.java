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
}
