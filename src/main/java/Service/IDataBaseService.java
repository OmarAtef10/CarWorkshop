package Service;

import Model.*;

import java.util.ArrayList;

public interface IDataBaseService {
    public boolean addProduct(Product product);
    public Product getProduct(String productName , String manufacturer);
    public boolean updateProduct(Product product);
    public boolean deleteProduct(String productName, String manufacturer);
    public Product getOrNull(int productId);
    public ArrayList<ProductHistoryItem> getHistory(int productId);
    public boolean addProductHistory(ProductHistoryItem productHistoryItem);

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
    public boolean removeInvoice(String Id);
    public Invoice updateInvoice(Invoice invoice);
    public Invoice getInvoice(String  id);

    public boolean addInvoiceProduct(Invoice invoice , Cart cart);



    public boolean addCustomer(Customer customer);
    public Customer getCustomer(String phone);
    public boolean deleteCustomer(String phone);

}
