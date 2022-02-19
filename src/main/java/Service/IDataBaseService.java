package Service;

import Model.*;

import java.util.ArrayList;

public interface IDataBaseService {
    public boolean addProduct(Product product);
    public Product getProduct(String productName , String manufacturer);
    public boolean updateProduct(Product product);
    public boolean deleteProduct(String productName, String manufacturer);
    public Product getOrNull(int productId);

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
    public boolean removeInvoice(int Id);
    public Invoice updateInvoice(Invoice invoice);
    public Invoice getInvoice(int id);

    public boolean addCustomer(Customer customer);
    public Customer getCustomer(int customerId);
    public boolean deleteCustomer(int customerId);

}
