package Service;

import Model.*;

import java.util.ArrayList;
import java.util.Hashtable;

//TODO
public interface IDataBaseService {
    public boolean addProduct(Product product);
    public ArrayList<Product> getProduct(String productName , String vendor);
    public Product getProduct(String productName);
    public boolean updateProduct(Product product);
    public boolean deleteProduct(String productName, String vendor);
    public Product getOrNull(String productId);
    public ArrayList<ProductHistoryItem> getHistory(String productId);
    ArrayList<Product> getAllProducts();

    public boolean addProductHistoryCreate(ProductHistoryItem productHistoryItem);
    public boolean addProductHistoryEdit(ProductHistoryItem productHistoryItem);
    public boolean addProductHistorySell(ProductHistoryItem productHistoryItem);


    public boolean addProductShelf(Product product);
    public Hashtable<String,Integer> getProductShelf(String productName , String vendor);
    public Hashtable<String,Integer> getProductShelf(String productId);

    public boolean addUser(User user);
    public User getUser(String username);
    public User updateUser(User user);
    public boolean deleteUser(String username);
    public ArrayList<User>getAllUsers();


    public Report addReport(Report report);
    public boolean deleteReport(String reportId);
    public Report updateReport(Report report); //TODO
    public ArrayList<Report> getReportsByUsername(String userName);
    public ArrayList<Report> getReportsByDate(String date);//TODO
    public Report getReportByUsernameDate(String userName, String date);//TODO


    public Invoice addInvoice(Invoice invoice);
    public Invoice updateInvoice(Invoice invoice);
    public Invoice getInvoice(String id);
    public boolean deleteInvoice(String id);
    public boolean addInvoiceProduct(Invoice invoice);
    public ArrayList<Invoice> getDailyUserInvoices(String username,String date);
    public ArrayList<Invoice> getDailyInvoices(String date);
    public Cart getCart(Invoice invoice);

    public boolean addCustomer(Customer customer);
    public Customer getCustomer(String phone);
    public boolean deleteCustomer(String phone);
}
