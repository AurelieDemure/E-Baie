package org.codingweek.model;

import org.codingweek.db.DatabaseManager;
import org.codingweek.db.entity.User;

public class AuthHandler {

    public static User getUser(String email) {
        DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
        return db.getEntity(User.class, email);
    }

    public static boolean checkPassword(User user, String password) {
        DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
        return PasswordUtility.checkPassword(password, user.getPassword());
    }

}
