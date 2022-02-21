import Context.DBContext;
import Controller.InvoiceDao;
import Controller.UserManager;
import Controller.WindowLoader;
import Model.Invoice;
import Model.User;
import Service.SQLiteService;
import View.MainWindow;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {
        DBContext.getDBContext().setDbService(new SQLiteService());
        //WindowLoader.initLoaders();


        //MainWindow window = new MainWindow();
       // window.view();


    }
}
