import Context.DBContext;
import Controller.UserManager;
import Model.User;
import Service.SQLiteService;

import java.time.LocalDateTime;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        DBContext.getDBContext().setDbService(new SQLiteService());

        UserManager userManager = UserManager.getInstance();

        System.out.println(userManager.login("omar22","12345").getRole().toString());

        System.out.println();
        LocalDateTime dateTime = LocalDateTime.now();
        
        System.out.println(dateTime.getYear()+"/"+dateTime.getMonthValue()+"/"+dateTime.getDayOfMonth());
    }
}
