import Context.DBContext;
import Controller.CustomerDao;
import Controller.InvoiceDao;
import Controller.ProductDao;
import Model.Customer;
import Model.Invoice;
import Model.Oil;
import Model.Product;
import Service.SQLiteService;

public class Main {
    public static void main(String[] args) throws Exception {
        DBContext.getDBContext().setDbService(new SQLiteService());
        //WindowLoader.initLoaders();


        //MainWindow window = new MainWindow();
       // window.view();
        ProductDao productDao = new ProductDao();
        CustomerDao customerDao = new CustomerDao();
        InvoiceDao invoiceDao = new InvoiceDao();
/*
        Product product = productDao.getProduct("shell 2000 50/20W","shell");
        Customer customer = new Customer("jack","015","camaro");
        customerDao.addCustomer(customer);

        customer.getCart().addProduct(product,5);

        Invoice invoice = new Invoice("omar",customer.getCart(),
                customer.getMobileNumber(),customer.getCart().getTotal());

        invoiceDao.addInvoice(invoice);
*/
       // System.out.println(invoiceDao.getCart( invoiceDao.getInvoice("0a89cc9d") ).toString());
        invoiceDao.getDailyUserInvoices("omar","2022/2/21 :: 11:4");
    }
}
