package Service;

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
            Class.forName("org.sqlite.JDBC"); //remove if doesn't work
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
    /**
     * date is YYY-MM-DD or DD/MM/YY  VARCHAR(12) 4-Year 2-Month 2-Day 2-Slashes
     * and 2 EXTRA*/
    public void CreateDB(){
        String customersTable = "CREATE TABLE Customers ("+
                "customerId	INTEGER PRIMARY KEY AUTOINCREMENT,"+ 
                "name VARCHAR(45)," + 
                "phone VARCHAR(45),"+
				"carModel VARCHAR(45),"+
				"invoiceId VARCHAR(45),"+
				"FOREIGN KEY(invoiceId) REFERENCES Invoices(invoiceId) ON DELETE CASCADE"+
				");";

        String usersTable = "CREATE TABLE Users("+ 
                "username VARCHAR(45) PRIMARY KEY NOT NULL,"+
				"password VARCHAR(45),"+
				"role VARCHAR(45));";

        String productTable = "CREATE TABLE Product("+
                "productId INTEGER PRIMARY KEY AUTOINCREMENT,"+
				"name VARCHAR(65),"+
				"range REAL NULL,"+
				"viscosity VARCHAR(45) NULL,"+
				"price REAL,"+
				"expiryDate VARCHAR(12),"+
				"units INTEGER,"+
				"vendor VARCHAR(45),"+
				"marketPrice REAL);";

        String invoicesTable = "CREATE TABLE Invoices("+
                "invoiceId INTEGER PRIMARY KEY Autoincrement,"+
				"customerId INTEGER,"+
				"username VARCHAR(45),"+
				"totalAmount REAL,"+
				"date VARCHAR(12),"+
				"FOREIGN KEY(username) REFERENCES Users(username));";

        String invoiceProductTable = "CREATE TABLE InvoiceProduct("+
                "invoiceId INTEGER,"+
				"productId INTEGER,"+
				"FOREIGN KEY(invoiceId) REFERENCES Invoices(invoiceId),"+
				"FOREIGN KEY(productId) REFERENCES Product(productId)),"+
                "PRIMARY KEY(invoiceId, productId);";

        String shelfProductTable = "CREATE TABLE ShelfProduct("+
                "productId INTEGER,"+
				"shelfNumber VARCHAR(45) PRIMARY KEY,"+
				"units INTEGER,"+
				"expiryDate VARCHAR(12) NULL,"+
				"FOREIGN KEY(productId) REFERENCES Product(productId));";

        String reportsTable = "CREATE TABLE Reports("+
                "date VARCHAR(12),"+
				"username VARCAHR(45),"+
				"reportId INTEGER,"+
                "PRIMARY KEY(username,date),"+
				"FOREIGN KEY(username) REFERENCES Users(username));";


        String reportInvoicesTable = "CREATE TABLE ReportInvoices("+
                "reportId INTEGER,"+
				"invoiceId INTEGER,"+
                "PRIMARY KEY(reportId, invoiceId),"+
				"FOREIGN KEY(invoiceId) REFERENCES Invoices(invoiceId));";
        try {
            statement.execute(customersTable);
            statement.execute(usersTable);
            statement.execute(productTable);
            statement.execute(invoicesTable);
            statement.execute(invoiceProductTable);
            statement.execute(shelfProductTable);
            statement.execute(reportsTable);
            statement.execute(reportInvoicesTable);
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
        String getUser = "SELECT * FROM Users WHERE username = ?";
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
    public Invoice addInvoice(Invoice invoice) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Invoice removeInvoice(int Id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Invoice updatInvoice(Invoice invoice) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Invoice getInvoice(int id) {
        // TODO Auto-generated method stub
        return null;
    }
}

class Main{
    public static void main(String[] args) {
        SQLiteService sqLiteService = new SQLiteService();
    }
}
