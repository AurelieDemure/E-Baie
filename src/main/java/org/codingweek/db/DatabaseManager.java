package org.codingweek.db;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

import java.sql.SQLException;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:src/main/resources/org/codingweek/db/database.sqlite";
    private static ConnectionSource connectionSource;

    public static ConnectionSource getConnectionSource() {
        if (connectionSource == null) {
            try {
                connectionSource = new JdbcConnectionSource(DATABASE_URL);
            } catch (SQLException e) {
                System.err.println("Erreur lors de la création de la connexion à la base de données : " + e.getMessage());
            }
        }
        return connectionSource;
    }
}

