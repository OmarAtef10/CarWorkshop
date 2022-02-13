import Context.DBContext;
import Controller.UserManager;
import Service.SQLiteService;

public class Main {
    public static void main(String[] args) {
        DBContext.getDBContext().setDbService(new SQLiteService());
        System.out.println(UserManager.getInstance().login("omar","test123").getRole());
    }
}
