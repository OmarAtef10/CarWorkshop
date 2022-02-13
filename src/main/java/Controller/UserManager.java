package Controller;

import Model.Action;
import Model.Role;
import Model.User;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;

public class UserManager {
    private Hashtable<Action, ArrayList<Role>> permissions;
    private User currentUser = null;
    private static UserManager userManager;

    private UserManager() {
        this.permissions = new Hashtable<>();
        initPermissions();
    }

    public static synchronized UserManager getInstance() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    public void initPermissions() {
        for (Action action : Action.values()) {
            permissions.put(action, new ArrayList<Role>());
            permissions.get(action).add(Role.ADMIN);
        }
        permissions.get(Action.VIEW_SHELF).add(Role.EMPLOYEE);
        permissions.get(Action.EDIT_SHELF).add(Role.EMPLOYEE);
    }

    public User login(String username, String password) {
        UserDao userDao = new UserDao();
        User user = userDao.getUser(username);
        if( user.getPassword().equals(password) ){
            this.currentUser = user;
        }else{
            System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        }
        return currentUser;
    }

    public void register(String username, String password){
        User registered = new User(username,password);
        //TODO ht-add el user fl DB
    }

    public static void main(String[] args) {

        UserManager userManager = UserManager.getInstance();
        System.out.println(userManager.permissions);


    }
}
