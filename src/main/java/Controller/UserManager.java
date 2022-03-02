package Controller;

import CustomizedUtilities.TimeUtility;
import Model.Action;
import Model.Report;
import Model.Role;
import Model.User;


import java.util.ArrayList;
import java.util.Hashtable;


public class UserManager {
    private final Hashtable<Role, ArrayList<Action>> permissions;
    private User currentUser = null;
    private static UserManager userManager;
    private UserDao userDao = null;

    private UserManager() {
        this.userDao = new UserDao();
        this.permissions = new Hashtable<>();
        initPermissions();
    }

    public static boolean userLoggedIn() {
        return getInstance().currentUser != null;
    }

    public static synchronized UserManager getInstance() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    public void initPermissions() {
        permissions.put(Role.ADMIN, new ArrayList<Action>() {{
            add(Action.VIEW_INVENTORY);
            add(Action.VIEW_SHELF);
            add(Action.EDIT_INVENTORY);
            add(Action.EDIT_SHELF);
            add(Action.VIEW_DAILY_REPORT);
            add(Action.ADD_USERS);
            add(Action.EDIT_USERS);
            add(Action.CHECK_OUT);
        }});

        permissions.put(Role.EMPLOYEE, new ArrayList<Action>() {{
            add(Action.VIEW_INVENTORY);
            add(Action.VIEW_SHELF);
            add(Action.CHECK_OUT);
        }});
    }

    public User login(String username, String password) throws Exception {
        String currentSessionStart = TimeUtility.getCurrentDate();

        UserDao userDao = new UserDao();
        User user = userDao.getUser(username);
        if (user == null) {
            throw new Exception("User doesn't exist!");
        } else if (user.getPassword().equals(password)) {
            this.currentUser = user;
        } else {
            throw new Exception("Wrong password!");
        }
        return currentUser;
    }

    public User register(String username, String password, Role role) {
        User newRegistered = new User(username, password);
        newRegistered.setRole(role);
        if (!this.userDao.addUser(newRegistered)) {
            return null;
        }
        return newRegistered;
    }

    public void logout() {
        Report userReport = new Report(currentUser.getUserName(), TimeUtility.getCurrentDate());

        ReportDao reportDao = new ReportDao();
        reportDao.addReport(userReport);

        currentUser = null;
    }

    public User updateUser(User user) {
        return this.userDao.updateUser(user);
    }

    public boolean deleteUser(String username) {
        return userDao.deleteUser(username);
    }

    public Report getMainDailyReport(String date) {
        ReportDao reportDao = new ReportDao();
        Report report = reportDao.getDailyMainReport(date);
        return report;
    }

    public Report getMainDailyReport() {
        ReportDao reportDao = new ReportDao();
        Report report = reportDao.getDailyMainReport(TimeUtility.getCurrentDate());
        return report;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Hashtable<Role, ArrayList<Action>> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(Action action) {;
        return UserManager.getInstance().getPermissions().get(this.currentUser.getRole()).contains(action);
    }
}

