package Service;

import CustomizedUtilities.TimeUtility;
import Model.*;

import java.io.File;
import java.sql.*;
import java.util.*;

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
                "productId VARCHAR(45) PRIMARY KEY," + //3ayz a5aly de string yb2a zy short code keda bymasel el product mslan shelly500 mslan m3nah shell b viscosity mo3yana w range 500.......
                "name VARCHAR(65)," +
                "range INTEGER NULL," +
                "viscosity VARCHAR(45) NULL," +
                "price REAL," +
                "expiryDate VARCHAR(12)," +
                "units INTEGER," +
                "vendor VARCHAR(45)," +
                "marketPrice REAL);";

        String productHistoryTable = "CREATE TABLE ProductHistory(" +
                "productId VARCHAR(45)," +
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
                "productId VARCHAR(45)," +
                "units INTEGER," +
                "FOREIGN KEY(invoiceId) REFERENCES Invoices(invoiceId)," +
                "FOREIGN KEY(productId) REFERENCES Product(productId)," +
                "PRIMARY KEY(invoiceId, productId));";

        String shelfProductTable = "CREATE TABLE ShelfProduct(" +
                "productId VARCHAR(45)," +
                "shelfNumber VARCHAR(45) PRIMARY KEY," +
                "units INTEGER," +
                "expiryDate VARCHAR(12) NULL," +
                "FOREIGN KEY(productId) REFERENCES Product(productId));";

        String reportsTable = "CREATE TABLE Reports(" +
                "date VARCHAR(12)," +
                "username VARCAHR(45)," +
                "reportId VARCHAR(45)," +
                "PRIMARY KEY(username,date)," +
                "FOREIGN KEY(username) REFERENCES Users(username));";


        String reportInvoicesTable = "CREATE TABLE ReportInvoices(" +
                "reportId VARCHAR(45)," +
                "invoiceId VARCHAR(45)," +
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
        if (getProduct(product.getProductName()) != null) {
            return false;
        }

        String addProduct = "INSERT INTO Product (productId,name,range,viscosity,price,expiryDate,units,vendor,marketPrice)" +
                "VALUES(?,?,?,?,?,?,?,?,?);";
        try {

            PreparedStatement preparedStatement = dbConnection.prepareStatement(addProduct);
            if (product instanceof Oil) {
                preparedStatement.setDouble(3, ((Oil) product).getMileage());
                preparedStatement.setString(4, ((Oil) product).getViscosity());
                preparedStatement.setString(6, ((Oil) product).getExpiryDate());
            } else {

                preparedStatement.setDouble(3, 0);
                preparedStatement.setString(4, null);
                preparedStatement.setString(6, null);
            }
            preparedStatement.setString(1, product.getProductId());
            preparedStatement.setString(2, product.getProductName());

            preparedStatement.setDouble(5, product.getPricePerUnit());

            preparedStatement.setInt(7, product.getUnits());
            preparedStatement.setString(8, product.getVendor());
            preparedStatement.setDouble(9, product.getMarketPrice());
            preparedStatement.executeUpdate();

            ProductHistoryItem productHistoryItem = new ProductHistoryItem(product.getProductId(), product.getUnits(), null);
            addProductHistoryCreate(productHistoryItem);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public ArrayList<Product> getProduct(String productName, String vendor) {
        String getProduct = "SELECT * FROM Product where name=? OR vendor=?;";
        ArrayList<Product> res = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(getProduct);
            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, vendor);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("viscosity") != null) {
                    res.add(Oil.fromResultSet(resultSet));
                } else {
                    res.add(ServicePart.fromResultSet(resultSet));
                }
            }

            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product getProduct(String productName) {
        String getProduct = "SELECT * FROM Product where name=?;";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(getProduct);
            preparedStatement.setString(1, productName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("viscosity") != null) {
                    return (Oil.fromResultSet(resultSet));
                } else {
                    return (ServicePart.fromResultSet(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Product getOrNull(String productId) {

        String getOrNull = "SELECT * FROM Product WHERE productId = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(getOrNull);
            preparedStatement.setString(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("viscosity") != null) {
                    return Oil.fromResultSet(resultSet);
                } else {
                    return ServicePart.fromResultSet(resultSet);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<ProductHistoryItem> getHistory(String productId) {
        ArrayList<ProductHistoryItem> productHistory = new ArrayList<>();
        String getHistory = "SELECT * FROM ProductHistory WHERE productId =?;";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(getHistory);
            preparedStatement.setString(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productHistory.add(ProductHistoryItem.fromResultSet(resultSet));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productHistory;
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

                ProductHistoryItem productHistoryItem = new ProductHistoryItem(product.getProductId(), product.getUnits(), null);
                addProductHistoryEdit(productHistoryItem);
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

                ProductHistoryItem productHistoryItem = new ProductHistoryItem(product.getProductId(), product.getUnits(), null);
                addProductHistoryEdit(productHistoryItem);
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

    @Override
    public boolean addProductShelf(Product product) {
        String addProductShelf = "INSERT INTO ShelfProduct(productId, shelfNumber, units, expiryDate) VALUES(?,?,?,?);";
        Hashtable<String, Integer> locations = product.getLocations();
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(addProductShelf);
            for (String loc : locations.keySet()) {
                preparedStatement.setString(1, product.getProductId());
                preparedStatement.setString(2, loc);
                preparedStatement.setInt(3, locations.get(loc));
                if (product instanceof Oil) {
                    Oil prod = (Oil) product;
                    preparedStatement.setString(4, prod.getExpiryDate());
                } else {
                    preparedStatement.setString(4, null);
                }
                preparedStatement.executeUpdate();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Hashtable<String, Integer> getProductShelf(String productName, String vendor) {
        Product product = getProduct(productName);
        String expDate = "";

        String getProductShelf = "SELECT * FROM ShelfProduct WHERE productId = ?;";

        Hashtable<String, Integer> locations = new Hashtable<>();
        int totalUnits = 0;

        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(getProductShelf);
            preparedStatement.setString(1, product.getProductId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                totalUnits += resultSet.getInt("units");
                locations.put(resultSet.getString("shelfNumber"), resultSet.getInt("units"));
                expDate = resultSet.getString("expiryDate");
            }
            product.setLocations(locations);
            if (product instanceof Oil) {
                ((Oil) product).setExpiryDate(expDate);
            }
            return locations;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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
            preparedStatement.setString(3, report.getReportId());
            preparedStatement.executeUpdate();
            return report;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteReport(String reportId) {
        String deleteReport = "DELETE FROM Reports WHERE reportId = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteReport);
            preparedStatement.setString(1, reportId);
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
        String reformedDate = TimeUtility.getReformedDate(date);
        ArrayList<Report> reports = new ArrayList<>();
        try {
            String sqlString = "SELECT * FROM Reports WHERE date LIKE '" + reformedDate + "%';";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlString);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                reports.add(Report.fromResultSet(rs));
            }
            return reports;
        } catch (Exception e) {
            e.printStackTrace();
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
            preparedStatement.setString(4, invoice.getDate());
            preparedStatement.setString(5, invoice.getInvoiceID());
            preparedStatement.executeUpdate();
            addInvoiceProduct(invoice);
            return invoice;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteInvoice(String Id) {
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
        String updateInvoice = "UPDATE Invoices SET username = ?,phone = ?,date = ?,totalAmount = ?  WHERE invoiceId = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(updateInvoice);
            preparedStatement.setString(1, invoice.getUserName());
            preparedStatement.setString(2, invoice.getCustomerID());
            preparedStatement.setString(3, invoice.getDate());
            preparedStatement.setDouble(4, invoice.getTotalPaid());
            preparedStatement.setString(5, invoice.getInvoiceID());
            preparedStatement.executeUpdate();
            return invoice;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Invoice getInvoice(String id) {
        String getInvoice = "SELECT * FROM Invoices WHERE invoiceId = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(getInvoice);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Invoice.fromResultSet(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addInvoiceProduct(Invoice invoice) {
        String addInvoiceProduct = "INSERT INTO InvoiceProduct(invoiceId, productId,units) VALUES(?,?,?);";
        HashMap<String, Integer> products = invoice.getCart().getProducts();
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(addInvoiceProduct);
            if (products.size() > 0) {
                for (String key : products.keySet()) {
                    preparedStatement.setString(1, invoice.getInvoiceID());
                    preparedStatement.setString(2, key);
                    preparedStatement.setInt(3, products.get(key));
                    preparedStatement.executeUpdate();

                    ProductHistoryItem productHistoryItem = new ProductHistoryItem(key, products.get(key), null);
                    addProductHistorySell(productHistoryItem);
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Cart getCart(Invoice invoice) {
        String getCart = "SELECT productId,units FROM InvoiceProduct WHERE invoiceId = ?;";
        Cart cart = new Cart();
        HashMap<Integer, Integer> products = new HashMap<>();
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(getCart);
            preparedStatement.setString(1, invoice.getInvoiceID());
            ResultSet resultSet = preparedStatement.executeQuery();
            return Cart.fromResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Invoice> getDailyUserInvoices(String username, String date) {
        String reformedDate = TimeUtility.getReformedDate(date);
        ArrayList<Invoice> invoices = new ArrayList<>();
        String getDailyUserInvoices = "SELECT * FROM Invoices WHERE username = ?" +
                "AND date LIKE '" + reformedDate + "%';";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(getDailyUserInvoices);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                invoices.add(Invoice.fromResultSet(resultSet));
            }
            return invoices;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Invoice> getDailyInvoices(String date) {
        String reformedDate = date.substring(0, date.indexOf("::"));
        ArrayList<Invoice> invoices = new ArrayList<>();
        String getDailyInvoices = "SELECT * FROM Invoices WHERE date LIKE '" + reformedDate + "%';";
        try {
            ResultSet resultSet = statement.executeQuery(getDailyInvoices);
            while (resultSet.next()) {
                invoices.add(Invoice.fromResultSet(resultSet));
            }
            return invoices;
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
    public boolean addProductHistoryCreate(ProductHistoryItem productHistoryItem) {
        productHistoryItem.setAction("Create");
        String addProductActionHistory = "INSERT INTO ProductHistory (productId,timeStamp,units,action) VALUES (?,?,?,?) ";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(addProductActionHistory);
            preparedStatement.setString(1, productHistoryItem.getProductId());
            preparedStatement.setString(2, productHistoryItem.getTimestamp());
            preparedStatement.setInt(3, productHistoryItem.getUnits());
            preparedStatement.setString(4, productHistoryItem.getAction());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addProductHistoryEdit(ProductHistoryItem productHistoryItem) {
        productHistoryItem.setAction("Edit");
        String addProductActionHistory = "INSERT INTO ProductHistory (productId,timeStamp,units,action) VALUES (?,?,?,?) ";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(addProductActionHistory);
            preparedStatement.setString(1, productHistoryItem.getProductId());
            preparedStatement.setString(2, productHistoryItem.getTimestamp());
            preparedStatement.setInt(3, productHistoryItem.getUnits());
            preparedStatement.setString(4, productHistoryItem.getAction());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addProductHistorySell(ProductHistoryItem productHistoryItem) {
        productHistoryItem.setAction("Sale");
        String addProductActionHistory = "INSERT INTO ProductHistory (productId,timeStamp,units,action) VALUES (?,?,?,?) ";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(addProductActionHistory);
            preparedStatement.setString(1, productHistoryItem.getProductId());
            preparedStatement.setString(2, productHistoryItem.getTimestamp());
            preparedStatement.setInt(3, productHistoryItem.getUnits());
            preparedStatement.setString(4, productHistoryItem.getAction());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Hashtable<String, Integer> getProductShelf(String productId) {
        String getProductShelf = "SELECT * FROM ShelfProduct WHERE productId = ?;";
        String expDate = "";

        Hashtable<String, Integer> locations = new Hashtable<>();
        int totalUnits = 0;

        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(getProductShelf);
            preparedStatement.setString(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                totalUnits += resultSet.getInt("units");
                locations.put(resultSet.getString("shelfNumber"), resultSet.getInt("units"));
                expDate = resultSet.getString("expiryDate");
            }
            return locations;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        String sql = "SELECT * FROM Product";
        ArrayList<Product> products = new ArrayList<>();
        try{
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                //TODO: put from result set in products instead
                while (rs.next()) {
                    if (rs.getString("viscosity") != null) {
                        products.add(Oil.fromResultSet(rs));
                    } else {
                        products.add(ServicePart.fromResultSet(rs));
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return products;
    }
}

