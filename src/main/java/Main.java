import Context.DBContext;
import Controller.*;
import Model.*;
import Service.SQLiteService;

import java.util.ArrayList;

//YY MM DD
public class Main {
    public static void main(String[] args) throws Exception {
        DBContext.getDBContext().setDbService(new SQLiteService());

        ProductDao productDao = new ProductDao();
        ReportDao reportDao = new ReportDao();
        InvoiceDao invoiceDao = new InvoiceDao();



    }
}




//        productDao.addProduct(new Oil("Shell",12,2.55,1.22,"100V",1100,
//                "2022/2/21"));
//
//        productDao.addProduct(new Oil("Toros",22,3.99,2.15,"100V",1200,
//                "2022/2/25"));
//
//        productDao.addProduct(new ServicePart("Air Filter","JKM",6,2.66,1.44));
//        productDao.addProduct(new ServicePart("Gas Filter","GMC",22,600_000,7.3));