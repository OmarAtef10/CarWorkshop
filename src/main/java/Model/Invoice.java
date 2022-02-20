package Model;

import java.sql.ResultSet;
import java.util.Date;
import java.util.UUID;

public class Invoice {
    private String invoiceID;
    private String userName;
    private Cart cart; //TODO n5aleha hashmap of product ids w units (int/int)
    private String phone;
    private double totalPaid;
    private Date date;


    public Invoice() {
    }

    public Invoice(String userName, Cart cart, String phone, double totalPaid, Date date) {
        this.userName = userName;
        this.cart = cart;
        this.phone = phone;
        this.totalPaid = totalPaid;
        this.date = date;
        this.invoiceID = UUID.randomUUID().toString();
    }

    public static Invoice fromResultSet(ResultSet resultSet) {
        Invoice invoice = new Invoice();
        try {
            invoice.setInvoiceID(resultSet.getString("invoiceId"));
            invoice.setCustomerID(resultSet.getString("phone"));
            invoice.setUserName(resultSet.getString("username"));
            invoice.setTotalPaid(resultSet.getDouble("totalAmount"));
            invoice.setDate(resultSet.getDate("date"));

            return invoice;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
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

    public String getCustomerID() {
        return phone;
    }

    public void setCustomerID(String phone) {
        this.phone = phone;
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

