package Controller;

import Context.DBContext;
import Model.User;

import java.util.ArrayList;

public class UserDao {
    //TODO: Implement methods
    public boolean addUser(User user) {
        return DBContext.getDBContext().getDbService().addUser(user);
    }

    public User getUser(String username) {
        return DBContext.getDBContext().getDbService().getUser(username);
    }

    public User updateUser(User user) {
        return DBContext.getDBContext().getDbService().updateUser(user);
    }

    public boolean deleteUser(String username) {
        return DBContext.getDBContext().getDbService().deleteUser(username);
    }

    public ArrayList<User> getAll(){
        return DBContext.getDBContext().getDbService().getAllUsers();
    }
}

