import Context.DBContext;
import Controller.UserManager;
import Controller.WindowLoader;
import Model.User;
import Service.SQLiteService;
import View.MainWindow;

import java.time.LocalDateTime;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        DBContext.getDBContext().setDbService(new SQLiteService());
        WindowLoader.initLoaders();

        // UserManager userManager = UserManager.getInstance();

        // System.out.println(userManager.login("omar22","12345").getRole().toString());

        // System.out.println();
        // LocalDateTime dateTime = LocalDateTime.now();
        
        // System.out.println(dateTime.getYear()+"/"+dateTime.getMonthValue()+"/"+dateTime.getDayOfMonth());

        MainWindow window = new MainWindow();
        window.view();
    }
}
