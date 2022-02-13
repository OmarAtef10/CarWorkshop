package Controller;

import Context.DBContext;
import Model.User;

public class UserDao {
    public boolean addUser(User user) {
        return false;
    }

    public User getUser(String username) {
        return DBContext.getDBContext().getDbService().getUser(username);
    }

    public boolean updateUser(User user) {
        return false;
    }

    public boolean deleteUser(String username) {
        return false;
    }
}

