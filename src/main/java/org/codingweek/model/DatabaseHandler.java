package org.codingweek.model;

import org.codingweek.db.DatabaseManager;

public class DatabaseHandler {

    private static DatabaseHandler instance = null;

    private DatabaseManager dbManager;


    private DatabaseHandler() {
        this.dbManager = new DatabaseManager();
        dbManager.setup();
    }

    public static DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }

    public DatabaseManager getDbManager() {
        return dbManager;
    }

}
