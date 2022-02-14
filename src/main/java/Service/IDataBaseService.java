package Service;

import Model.Invoice;
import Model.Product;
import Model.Report;
import Model.User;

import java.util.ArrayList;

public interface IDataBaseService {
    public boolean addProduct(Product product);
    public Product getProduct(String productId);
    public boolean updateProduct(Product product);
    public boolean deleteProduct(String productId);

    public boolean addUser(User user);
    public User getUser(String username);
    public boolean updateUser(User user);
    public boolean deleteUser(String username);

    public boolean addReport(Report report);
    public boolean deleteReport(int reportId);
    public Report updateReport(Report report);
    public ArrayList<Report> getReportsByUsername(String userName);
    public ArrayList<Report> getReportsByDate(String date);
    public Report getReportByUsernameDate(String userName, String date);

    public Invoice addInvoice(Invoice invoice);
    public Invoice removeInvoice(int Id);
    public Invoice updatInvoice(Invoice invoice);
    public Invoice getInvoice(int id);


}
