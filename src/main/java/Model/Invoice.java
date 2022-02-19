package Model;

import java.sql.ResultSet;
import java.util.Date;

public class Invoice {
    private int invoiceID;
    private String userName;
    private Cart cart; //TODO n5aleha hashmap of product ids w units (int/int)
    private int customerID;
    private double totalPaid;
    private Date date;


    public Invoice() {
    }

    public Invoice(String userName, Cart cart, int customerID, double totalPaid, Date date) {
        this.userName = userName;
        this.cart = cart;
        this.customerID = customerID;
        this.totalPaid = totalPaid;
        this.date = date;
    }

    public static Invoice fromResultSet(ResultSet resultSet) {
        Invoice invoice = new Invoice();
        try {
            invoice.setInvoiceID(resultSet.getInt("invoiceId"));
            invoice.setCustomerID(resultSet.getInt("customerId"));
            invoice.setUserName(resultSet.getString("username"));
            invoice.setTotalPaid(resultSet.getDouble("totalAmount"));
            invoice.setDate(resultSet.getDate("date"));

            return invoice;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}

