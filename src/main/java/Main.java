import Context.DBContext;
import Controller.*;
import Model.*;
import Service.SQLiteService;

import java.util.ArrayList;
import java.util.Hashtable;

//YY MM DD
public class Main {
    public static void main(String[] args) throws Exception {
        DBContext.getDBContext().setDbService(new SQLiteService());

        ProductDao productDao = new ProductDao();
        ReportDao reportDao = new ReportDao();
        InvoiceDao invoiceDao = new InvoiceDao();
        CustomerDao customerDao = new CustomerDao();
        UserDao userDao = new UserDao();
//
//        Customer c = customerDao.getCustomer("010");
//        Product p = productDao.getProduct("Shell 1000 200V");
//        Product p2 = productDao.getProduct("Gas Filter");
//        User u = userDao.getUser("omar");
//        Cart c1 = new Cart();
//        c1.addProduct(p,12);
//        c1.addProduct(p2,2);
//        Invoice invoice = new Invoice(u.getUserName(),c1,c.getMobileNumber(), c1.getTotal());
//
//        invoiceDao.addInvoice(invoice);
//
//        Invoice invoice =invoiceDao.getInvoice("42ddc7ee");
//
//        invoice.setTotalPaid(69);
//        invoiceDao.updateInvoice(invoice);
//
//        ArrayList<Invoice> invoices = invoiceDao.getDailyUserInvoices("omar","2022/2/23 :: 15:9");
//

//
//        UserManager userManager=UserManager.getInstance();
//        User u = userManager.login("omar","1234");
//        userManager.logout();
      ArrayList<Report> ar =   reportDao.getReportsByDate("2022/2/23 :: 16:8:6:934000000");


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