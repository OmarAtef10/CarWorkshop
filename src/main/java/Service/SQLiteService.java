package Service;

import Controller.UserManager;
import Model.Invoice;
import Model.Product;
import Model.Report;
import Model.User;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

public class SQLiteService implements IDataBaseService{
    private static Connection dbConnection;
    private final String url = "jdbc:sqlite:";
    private final String dbPath = "db/CarWorkshop.db";
    private static Statement statement;

    public SQLiteService(){
        try {
            File file = new File(this.dbPath);
            boolean exist = file.exists();
            dbConnection = DriverManager.getConnection(this.url + this.dbPath);
            statement = dbConnection.createStatement();
            if (!exist) {
                CreateDB();
                System.out.println("Database Created.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Users ->  Invoices -> ReportInvoices -> Reports -> Customers -> Products
    // InvoiceProduct -> ShelfProduct
    public void CreateDB(){
        String customersTable = "CREATE TABLE Customers(" +
                "customerId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(45)," +
                "phone VARCHAR(45)," +
                "carModel VARCHAR(45)," +
                "invoiceId VARCHAR(45)" +
                ");";


        String usersTable = "CREATE TABLE Users(" +
                "username VARCHAR(45) PRIMARY KEY NOT NULL," +
                "password VARCHAR(45)," +
                "role VARCHAR(45)" +
                ");";

        try {
            statement.execute(customersTable);
            statement.execute(usersTable);
        }catch (Exception err){
            err.printStackTrace();
        }
    }


    @Override
    public boolean addProduct(Product product) {
        return false;
    }

    @Override
    public Product getProduct(String productId) {
        return null;
    }

    @Override
    public boolean updateProduct(Product product) {
        return false;
    }

    @Override
    public boolean deleteProduct(String productId) {
        return false;
    }
/////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public User getUser(String username) {
        String getUser = "SELECT * FROM Users WHERE username =?";
        try{
            PreparedStatement preparedStatement = dbConnection.prepareStatement(getUser);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return User.fromResultSet(resultSet);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        return false;
    }
////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean addReport(Report report) {
        return false;
    }

    @Override
    public boolean deleteReport(int reportId) {
        return false;
    }

    @Override
    public Report updateReport(Report report) {
        return null;
    }

    @Override
    public ArrayList<Report> getReportsByUsername(String userName) {
        return null;
    }

    @Override
    public ArrayList<Report> getReportsByDate(String date) {
        return null;
    }

    @Override
    public Report getReportByUsernameDate(String userName, String date) {
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean addInvoice(Invoice invoice) {
        return false;
    }

    @Override
    public boolean removeInvoice(int Id) {
        return false;
    }
}

class Main{
    public static void main(String[] args) {
        SQLiteService sqLiteService = new SQLiteService();
    }
}
