import Context.DBContext;
import Controller.UserManager;
import Model.User;
import Service.SQLiteService;

public class Main {
    public static void main(String[] args) throws Exception {
        DBContext.getDBContext().setDbService(new SQLiteService());

        UserManager userManager = UserManager.getInstance();
        User u =userManager.register("omar22","123");

        System.out.println(userManager.login("omar22","123").getRole().toString());

        System.out.println(userManager.updateUser(new User("omar22","12345")));
    }
}
