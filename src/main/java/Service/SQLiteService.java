package Service;

import Model.*;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class SQLiteService implements IDataBaseService {
    private static Connection dbConnection;
    private final String url = "jdbc:sqlite:";
    private final String dbPath = "db/CarWorkshop.db";
    private static Statement statement;

    public SQLiteService() {
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
     * and 2 EXTRA
     */
    public void CreateDB() {
        String customersTable = "CREATE TABLE Customers (" +
                "name VARCHAR(45)," +
                "phone VARCHAR(45) PRIMARY KEY," +
                "carModel VARCHAR(45)" +
                //"invoiceId INTEGER," +
                // "FOREIGN KEY(invoiceId) REFERENCES Invoices(invoiceId) ON DELETE CASCADE" +
                ");";

        String usersTable = "CREATE TABLE Users(" +
                "username VARCHAR(45) PRIMARY KEY NOT NULL," +
                "password VARCHAR(45)," +
                "role VARCHAR(45));";

        String productTable = "CREATE TABLE Product(" +
                "productId INTEGER PRIMARY KEY AUTOINCREMENT," + //3ayz a5aly de string yb2a zy short code keda bymasel el product mslan shelly500 mslan m3nah shell b viscosity mo3yana w range 500.......
                "name VARCHAR(65)," +
                "range INTEGER NULL," +
                "viscosity VARCHAR(45) NULL," +
                "price REAL," +
                "expiryDate VARCHAR(12)," +
                "units INTEGER," +
                "vendor VARCHAR(45)," +
                "marketPrice REAL);";

        String productHistoryTable = "CREATE TABLE ProductHistory(" +
                "productId INTEGER," +
                "timeStamp VARCHAR(45)," +
                "units INTEGER," +
                "action VARCHAR(45)," +
                "PRIMARY KEY (productId,timeStamp)," +
                "FOREIGN KEY (productId) REFERENCES Product (productId)" +
                ");";

        String invoicesTable = "CREATE TABLE Invoices(" +
                "invoiceId VARCHAR(45) PRIMARY KEY ," +
                "phone VARCHAR(45)," +
                "username VARCHAR(45)," +
                "totalAmount REAL," +
                "date VARCHAR(45)," +
                "FOREIGN KEY(username) REFERENCES Users(username)," +
                "FOREIGN KEY (phone) REFERENCES  Customers(phone));";

        String invoiceProductTable = "CREATE TABLE InvoiceProduct(" + //insert -> for loop !?
                "invoiceId INTEGER," +
                "productId INTEGER," +
                "units INTEGER," +
                "FOREIGN KEY(invoiceId) REFERENCES Invoices(invoiceId)," +
                "FOREIGN KEY(productId) REFERENCES Product(productId)," +
                "PRIMARY KEY(invoiceId, productId));";

        String shelfProductTable = "CREATE TABLE ShelfProduct(" +
                "productId INTEGER," +
                "shelfNumber VARCHAR(45) PRIMARY KEY," +
                "units INTEGER," +
                "expiryDate VARCHAR(12) NULL," +
                "FOREIGN KEY(productId) REFERENCES Product(productId));";

        String reportsTable = "CREATE TABLE Reports(" +
                "date VARCHAR(12)," +
                "username VARCAHR(45)," +
                "reportId INTEGER," +
                "PRIMARY KEY(username,date)," +
                "FOREIGN KEY(username) REFERENCES Users(username));";


        String reportInvoicesTable = "CREATE TABLE ReportInvoices(" +
                "reportId INTEGER," +
                "invoiceId INTEGER," +
                "PRIMARY KEY(reportId, invoiceId)," +
                "FOREIGN KEY(invoiceId) REFERENCES Invoices(invoiceId));";
        try {
            statement.execute(customersTable);
            statement.execute(usersTable);
            statement.execute(productTable);
            statement.execute(productHistoryTable);
            statement.execute(invoicesTable);
            statement.execute(invoiceProductTable);
            statement.execute(shelfProductTable);
            statement.execute(reportsTable);
            statement.execute(reportInvoicesTable);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public boolean addProduct(Product product) {
        if (getProduct(product.getProductName(), product.getVendor()) != null) {

            return false;
        }

        String addProduct = "INSERT INTO Product (name,range,viscosity,price,expiryDate,units,vendor,marketPrice)" +
                "VALUES(?,?,?,?,?,?,?,?);";
        try {

            PreparedStatement preparedStatement = dbConnection.prepareStatement(addProduct);
            if (product instanceof Oil) {
                preparedStatement.setString(1, product.getProductName());
                preparedStatement.setDouble(2, ((Oil) product).getMileage());
                preparedStatement.setString(3, ((Oil) product).getViscosity());
                preparedStatement.setDouble(4, product.getPricePerUnit());
                preparedStatement.setString(5, ((Oil) product).getExpiryDate());
                preparedStatement.setInt(6, product.getUnits());
                preparedStatement.setString(7, product.getVendor());
                preparedStatement.setDouble(8, product.getMarketPrice());
                preparedStatement.executeUpdate();
                return true;
            } else {
                preparedStatement.setString(1, product.getProductName());
                preparedStatement.setDouble(2, 0);
                preparedStatement.setString(3, null);
                preparedStatement.setDouble(4, product.getPricePerUnit());
                preparedStatement.setString(5, null);
                preparedStatement.setInt(6, product.getUnits());
                preparedStatement.setString(7, product.getVendor());
                preparedStatement.setDouble(8, product.getMarketPrice());
                preparedStatement.executeUpdate();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override //TODO traga3 arraylist!!!!!!!
    public Product getProduct(String productName, String manufacturer) { //Edtaret a8yarha 7alyn y3ny ekmn el lw el name w el manifacturer dupliactes yb2a GG w azon homa yb2o el ID..... w 3ashan mkrarsh mwdo3 on dirver ta tany fl rides w keda

        String getProduct = "SELECT * FROM Product where name=? OR vendor=?;";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(getProduct);
            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, manufacturer);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("viscosity") != null) {

                    Oil oil = Oil.fromResultSet(resultSet);
                    System.out.println(oil.toString());
                    return oil;
                } else {
                    ServicePart servicePart = ServicePart.fromResultSet(resultSet);
                    System.out.println(servicePart.toString());
                    return servicePart;

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product getOrNull(int productId) {

        String getOrNull = "SELECT * FROM Product WHERE productId = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(getOrNull);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("viscosity") != null) {

                    Oil oil = Oil.fromResultSet(resultSet);
                    System.out.println(oil.toString());
                    return oil;
                } else {
                    ServicePart servicePart = ServicePart.fromResultSet(resultSet);
                    System.out.println(servicePart.toString());
                    return servicePart;

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<ProductHistoryItem> getHistory(int productId) {
        ArrayList<ProductHistoryItem> productHistory = new ArrayList<>();
        String getHistory = "SELECT * FROM ProductHistory WHERE productId = " + productId;
        try {
            ResultSet resultSet = statement.executeQuery(getHistory);
            while (resultSet.next()) {
                productHistory.add(ProductHistoryItem.fromResultSet(resultSet));
            }
            return productHistory;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean updateProduct(Product product) {
        if (product instanceof Oil) {
            String updateProduct = "UPDATE Product SET price = ?, marketPrice = ? ,units = ?, expiryDate = ?" +
                    "WHERE name  = ? AND vendor = ?;";

            try {
                PreparedStatement preparedStatement = dbConnection.prepareStatement(updateProduct);
                preparedStatement.setDouble(1, product.getPricePerUnit());
                preparedStatement.setDouble(2, product.getMarketPrice());
                preparedStatement.setInt(3, product.getUnits());
                preparedStatement.setString(4, ((Oil) product).getExpiryDate());
                preparedStatement.setString(5, product.getProductName());
                preparedStatement.setString(6, product.getVendor());
                preparedStatement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } else {
            String updateProduct = "UPDATE Product SET price = ?, marketPrice = ? ,units = ? " +
                    "WHERE name  = ? AND vendor = ?;";

            try {
                PreparedStatement preparedStatement = dbConnection.prepareStatement(updateProduct);
                preparedStatement.setDouble(1, product.getPricePerUnit());
                preparedStatement.setDouble(2, product.getMarketPrice());
                preparedStatement.setInt(3, product.getUnits());
                preparedStatement.setString(4, product.getProductName());
                preparedStatement.setString(5, product.getVendor());
                preparedStatement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public boolean deleteProduct(String productName, String manufacturer) {
        String deleteProduct = "DELETE FROM Product WHERE name = ? AND vendor = ?; ";

        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteProduct);
            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, manufacturer);
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean addUser(User user) {
        String addUser = "INSERT INTO Users (username,password,role)" +
                "VALUES(?,?,?);";

        try {
            if (getUser(user.getUserName()) != null) {
                //Username or User itself already exists.
                return false;
            }
            PreparedStatement preparedStatement = dbConnection.prepareStatement(addUser);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUser(String username) {
        String getUser = "SELECT * FROM Users WHERE username = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(getUser);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return User.fromResultSet(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        String updateUser = "UPDATE Users SET password = ?,role = ?" +
                "WHERE username = ?;";

        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(updateUser);
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getRole().toString());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.executeUpdate();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteUser(String username) {
        String deleteUser = "DELETE FROM Users WHERE username= ?; ";

        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteUser);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }

    ////////////////////////////////////////////////////////////////////////////
    @Override
    public Report addReport(Report report) {
        String addReport = "INSERT INTO Reports (date, username, reportId) VALUES(?,?,?)";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(addReport);
            preparedStatement.setString(1, report.getDate().toString());
            preparedStatement.setString(2, report.getUserName());
            preparedStatement.setInt(3, report.getReportId());
            return report;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteReport(int reportId) {
        String deleteReport = "DELETE FROM Reports WHERE reportId = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteReport);
            preparedStatement.setInt(1, reportId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Report updateReport(Report report) {
//        String updateReport = "UPDATE Reports SET  WHERE reportId = ?";
//        try{
//            PreparedStatement preparedStatement = dbConnection.prepareStatement(updateReport);
//            preparedStatement.setInt(1,report.getReportId());
//            ResultSet resultSet = preparedStatement.executeQuery();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return null;
    }

    @Override
    public ArrayList<Report> getReportsByUsername(String userName) {
        ArrayList<Report> reports = new ArrayList<>();
        String getReportsByUsername = "SELECT * FROM Reports WHERE username = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(getReportsByUsername);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reports.add(Report.fromResultSet(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return reports;
    }



    @Override
    public ArrayList<Report> getReportsByDate(String date) {
        ArrayList<Report> reports = new ArrayList<Report>();
        try {
            String sqlString = "SELECT * FROM Reports where date = " + date + ";";
            ResultSet rs = statement.executeQuery(sqlString);
            while (rs.next())
                reports.add(Report.fromResultSet(rs));
        } catch (Exception e) {
            e.printStackTrace();
            return reports;
        }
        return reports;
    }

    @Override
    public Report getReportByUsernameDate(String userName, String date) {
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    @Override
    public Invoice addInvoice(Invoice invoice) {
        String addInvoice = "INSERT INTO Invoices (phone, username,totalAmount,date,invoiceId) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(addInvoice);
            preparedStatement.setString(1, invoice.getCustomerID());
            preparedStatement.setString(2, invoice.getUserName());
            preparedStatement.setDouble(3, invoice.getTotalPaid());
            preparedStatement.setString(4, invoice.getDate().toString());
            preparedStatement.setString(5, invoice.getInvoiceID());
            preparedStatement.executeUpdate();
            return invoice;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeInvoice(String Id) {
        String removeInvoice = "DELETE FROM Invoices WHERE invoiceId = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(removeInvoice);
            preparedStatement.setString(1, Id);
            preparedStatement.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        String updateInvoice = "UPDATE Invoices SET totalAmount = ?  WHERE invoiceId = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(updateInvoice);
            preparedStatement.setDouble(1, invoice.getTotalPaid());
            preparedStatement.setString(2, invoice.getInvoiceID());
            preparedStatement.executeUpdate();
            return invoice;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Invoice getInvoice(String id) {
        String getInvoice = "SELECT * FROM Invoices WHERE invoiceId = "+id;
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(getInvoice);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Invoice invoice = Invoice.fromResultSet(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /////////////////////////////////////////////////////////////////////////

    @Override
    public boolean addCustomer(Customer customer) {
        String addCustomer = "INSERT INTO Customers (name,phone,carModel) VALUES (?,?,?);";

        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(addCustomer);
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getMobileNumber());
            preparedStatement.setString(3, customer.getCarModel());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Customer getCustomer(String phone) {
        String getCustomer = "SELECT name,phone,carModel FROM Customers WHERE phone = ?;";

        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(getCustomer);
            preparedStatement.setString(1, phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            return Customer.fromResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteCustomer(String phone) {
        String deleteCustomer = "DELETE FROM Customers WHERE phone = ?;";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteCustomer);
            preparedStatement.setString(1, phone);
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override  //TODO
    public boolean addProductHistory(ProductHistoryItem productHistoryItem) {
        return false;
    }

    @Override  //TODO
    public boolean addInvoiceProduct(Invoice invoice, Cart cart) {
        return false;
    }
}

class Main {
    public static void main(String[] args) {
        SQLiteService sqLiteService = new SQLiteService();
        Oil oil = new Oil("shell", 69, 50, 10, "50/20W", 2000, "Bokra");
        ServicePart servicePart = new ServicePart("some Filters", "Tifa", 100, 55, 60);
        sqLiteService.addProduct(oil);
        sqLiteService.addProduct(servicePart);
        sqLiteService.getProduct("some Filters", "Tifa");
        sqLiteService.getProduct("shell 2000 50/20W", "shell");
        Oil Oil = new Oil("shell", 420, 69, 42, "50/20W", 2000, "b3d Bokra");
        sqLiteService.updateProduct(Oil);
        ServicePart ServicePart = new ServicePart("some Filters", "Tifa", 110, 5, 6);
        sqLiteService.updateProduct(ServicePart);
        sqLiteService.deleteProduct("some Filters", "Tifa");

        User user = new User("omar", "1010abab");
        sqLiteService.addUser(user);

        sqLiteService.getOrNull(6);

//        String date = "12/12";
//        Invoice invoice = new Invoice("omar", null, "012", 100, null);
//        sqLiteService.addInvoice(invoice);


    }
}
