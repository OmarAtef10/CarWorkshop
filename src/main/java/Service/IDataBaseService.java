package Service;

import Model.Invoice;
import Model.Product;
import Model.Report;
import Model.User;

import java.util.ArrayList;

public interface IDataBaseService {
    public boolean addProduct(Product product);
    public Product getProduct(String productName , String manufacturer);
    public boolean updateProduct(Product product);
    public boolean deleteProduct(String productName, String manufacturer);

    public boolean addUser(User user);
    public User getUser(String username);
    public User updateUser(User user);
    public boolean deleteUser(String username);

    public Report addReport(Report report);
    public Report deleteReport(int reportId);
    public Report updateReport(Report report);
    public ArrayList<Report> getReportsByUsername(String userName);
    public ArrayList<Report> getReportsByDate(String date);
    public Report getReportByUsernameDate(String userName, String date);

    public Invoice addInvoice(Invoice invoice);
    public Invoice removeInvoice(int Id);
    public Invoice updatInvoice(Invoice invoice);
    public Invoice getInvoice(int id);


}
