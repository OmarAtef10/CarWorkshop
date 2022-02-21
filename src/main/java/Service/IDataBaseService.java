package Service;

import Model.*;

import java.util.ArrayList;
//TODO
public interface IDataBaseService {
    public boolean addProduct(Product product);
    public Product getProduct(String productName , String manufacturer);
    public boolean updateProduct(Product product);
    public boolean deleteProduct(String productName, String manufacturer);
    public Product getOrNull(int productId);
    public ArrayList<ProductHistoryItem> getHistory(int productId);

    public boolean addProductHistoryCreate(ProductHistoryItem productHistoryItem);
    public boolean addProductHistoryEdit(ProductHistoryItem productHistoryItem);
    public boolean addProductHistorySell(ProductHistoryItem productHistoryItem);


    public boolean addProductShelf(Product product);
    public Product getProductShelf(String productName , String vendor);
    public boolean updateProductShelf(Product product);


    public boolean addUser(User user);
    public User getUser(String username);
    public User updateUser(User user);
    public boolean deleteUser(String username);

    public Report addReport(Report report);
    public boolean deleteReport(int reportId);
    public Report updateReport(Report report);
    public ArrayList<Report> getReportsByUsername(String userName);
    public ArrayList<Report> getReportsByDate(String date);
    public Report getReportByUsernameDate(String userName, String date);


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
