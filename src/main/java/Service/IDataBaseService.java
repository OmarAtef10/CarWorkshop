package Service;

import Model.*;

import java.util.ArrayList;
import java.util.Hashtable;

//TODO
public interface IDataBaseService {
    boolean addProduct(Product product);
    ArrayList<Product> getProduct(String productName , String vendor);
    Product getProduct(String productName);
    boolean updateProduct(Product product);
    boolean deleteProduct(String productName, String vendor);
    Product getOrNull(String productId);
    ArrayList<ProductHistoryItem> getHistory(String productId);

    boolean addProductHistoryCreate(ProductHistoryItem productHistoryItem);
    boolean addProductHistoryEdit(ProductHistoryItem productHistoryItem);
    boolean addProductHistorySell(ProductHistoryItem productHistoryItem);


    boolean addProductShelf(Product product);
    Hashtable<String,Integer> getProductShelf(String productName , String vendor);

    boolean addUser(User user);
    User getUser(String username);
    User updateUser(User user);
    boolean deleteUser(String username);

    Report addReport(Report report);
    boolean deleteReport(String reportId);
    Report updateReport(Report report); //TODO
    ArrayList<Report> getReportsByUsername(String userName);
    ArrayList<Report> getReportsByDate(String date);//TODO
    Report getReportByUsernameDate(String userName, String date);//TODO


    Invoice addInvoice(Invoice invoice);
    Invoice updateInvoice(Invoice invoice);
    Invoice getInvoice(String id);
    boolean deleteInvoice(String id);
    boolean addInvoiceProduct(Invoice invoice);
    ArrayList<Invoice> getDailyUserInvoices(String username,String date);
    ArrayList<Invoice> getDailyInvoices(String date);
    Cart getCart(Invoice invoice);

    boolean addCustomer(Customer customer);
    Customer getCustomer(String phone);
    boolean deleteCustomer(String phone);
}
