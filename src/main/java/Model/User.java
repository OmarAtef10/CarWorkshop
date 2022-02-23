package Model;

import java.sql.ResultSet;


public class User {

    private String userName;
    private String password;
    private Role role;
    private Report sessionReport; //TODO daily session report wala kol el invoices!

    public User(){}

    public User (String userName,String password){
        this.userName=userName;
        this.password=password;
        this.role=Role.EMPLOYEE;
        this.sessionReport =new Report();
    }

    public static User fromResultSet(ResultSet resultSet) {
        User user = new User();
        try {
            user.userName = resultSet.getString("username");
            user.password = resultSet.getString("password");
            user.role = Role.valueOf(resultSet.getString("role"));

        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Report getSessionReport() {
        return sessionReport;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
