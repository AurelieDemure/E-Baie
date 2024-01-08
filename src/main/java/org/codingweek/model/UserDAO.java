package org.codingweek.model;

public class UserDAO {

    public UserDAO() {
        /*try {
            //userDao = DaoManager.createDao(DatabaseManager.getConnectionSource(), User.class);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
    }

    public void save(User user) {
        /*try {
            userDao.create(user);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
    }

    public User findByEmail(String email) {
       /* try {
            return userDao.queryForId(email);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }*/
        return null;
    }
}
