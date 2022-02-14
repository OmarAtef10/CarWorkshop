package Controller;

import Context.DBContext;
import Model.User;

public class UserDao {
    //TODO: Implement methods
    public User addUser(User user) {
        return null;
    }

    public User getUser(String username) {
        return DBContext.getDBContext().getDbService().getUser(username);
    }

    public User updateUser(User user) {
        return null;
    }

    public User deleteUser(String username) {
        return null;
    }
}

