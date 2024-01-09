package org.codingweek.model;

import org.codingweek.db.DatabaseManager;

public class DatabaseHandler {

    private static DatabaseHandler instance = null;

    private final DatabaseManager dbManager;

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

    /** Need to call this function when doing test */
    public static void setDatabaseTest() {
        DatabaseHandler.getInstance().dbManager.setupTest();
    }

    public DatabaseManager getDbManager() {
        return dbManager;
    }

}
