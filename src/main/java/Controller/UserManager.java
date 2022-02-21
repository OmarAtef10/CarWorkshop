package Controller;

import Model.Action;
import Model.Report;
import Model.Role;
import Model.User;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Objects;

public class UserManager {
    private Hashtable<Action, ArrayList<Role>> permissions;
    private User currentUser = null;
    private static UserManager userManager;
    private UserDao userDao = null;

    private UserManager() {
        this.userDao = new UserDao();
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

    public User login(String username, String password) throws Exception {
        String currentSessionStart = getCurrentDate();

        UserDao userDao = new UserDao();
        User user = userDao.getUser(username);
        if(user == null){
            throw new Exception("User doesn't exist!");
        }else if( user.getPassword().equals(password) ){
            this.currentUser = user;
        }else{
            throw new Exception("Wrong password!");
        }
        return currentUser;
    }

    public User register(String username,String password){
        User newRegistered = new User(username,password);
        if(! this.userDao.addUser(newRegistered)){
            return null;
        }
        return newRegistered;
    }

    public void logout(){
        Report userReport = new Report(currentUser.getUserName(),getCurrentDate());

        currentUser = null;
    }

    public User updateUser(User user){
        return this.userDao.updateUser(user);
    }

    public boolean deleteUser(String username){
        return userDao.deleteUser(username);
    }


    public String getCurrentDate(){
        LocalDateTime dateTime = LocalDateTime.now();
        //YY/MM/DD
        return dateTime.getYear()+"/"+dateTime.getMonthValue()+"/"+dateTime.getDayOfMonth()
                +" :: "+dateTime.getHour()+":"+dateTime.getMinute();
    }

}
