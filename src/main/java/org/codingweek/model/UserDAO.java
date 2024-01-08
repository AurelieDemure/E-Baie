package org.codingweek.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import org.codingweek.db.DatabaseManager;
import java.sql.SQLException;

public class UserDAO {
    private Dao<User, String> userDao;

    public UserDAO() {
        try {
            userDao = DaoManager.createDao(DatabaseManager.getConnectionSource(), User.class);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void save(User user) {
        try {
            userDao.create(user);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public User findByEmail(String email) {
        try {
            return userDao.queryForId(email);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
