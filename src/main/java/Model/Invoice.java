package Model;

import Controller.InvoiceDao;
import CustomizedUtilities.TimeUtility;
import CustomizedUtilities.UUID_Utility;

import java.sql.ResultSet;

public class Invoice {
    private String invoiceId;
    private String userName;
    private Cart cart;
    private String phone;
    private double totalPaid;
    private String date;

    public Invoice() {}

    public Invoice(String userName, Cart cart, String phone, double totalPaid) {
        this.userName = userName;
        this.cart = cart;
        this.phone = phone;
        this.totalPaid = totalPaid;
        this.date = TimeUtility.getCurrentDate();
        this.invoiceId = UUID_Utility.generateId();
    }

    public static Invoice fromResultSet(ResultSet resultSet) {
        InvoiceDao invoiceDao = new InvoiceDao();
        try {
            Invoice invoice = new Invoice();
            invoice.setInvoiceID(resultSet.getString("invoiceId"));
            invoice.setCustomerID(resultSet.getString("phone"));
            invoice.setUserName(resultSet.getString("username"));
            invoice.setTotalPaid(resultSet.getDouble("totalAmount"));
            invoice.setDate(resultSet.getString("date"));
            invoice.setCart( invoiceDao.getCart(invoice) );
            return invoice;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceId = invoiceID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setCustomerID(String phone) {
        this.phone = phone;
    }


    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInvoiceID() {
        return invoiceId;
    }

    public String getUserName() {
        return userName;
    }

    public Cart getCart() {
        return cart;
    }

    public String getCustomerID() {
        return phone;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public String getDate() {
        return date;
    }

}

