package Model;

import java.util.ArrayList;

public class User {

    private String userName;
    private String password;
    private Role role;
    private ArrayList<Report> SessionReports; //TODO daily session report wala kol el invoices!

    public User (String userName,String password){
        this.userName=userName;
        this.password=password;
        this.role=Role.EMPLOYEE;
        this.SessionReports=new ArrayList<>();

    }
}
