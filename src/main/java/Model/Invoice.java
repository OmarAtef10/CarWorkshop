package Model;

import Controller.InvoiceDao;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.UUID;

public class Invoice {
    private String invoiceID;
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
        this.date = getCurrentDate();
        this.invoiceID = generateId();
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
        this.invoiceID = invoiceID;
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
        return invoiceID;
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

    public String getCurrentDate(){
        LocalDateTime dateTime = LocalDateTime.now();
        //YY/MM/DD
        return dateTime.getYear()+"/"+dateTime.getMonthValue()+"/"+dateTime.getDayOfMonth()
                +" :: "+dateTime.getHour()+":"+dateTime.getMinute();
    }

    public String generateId(){
        return UUID.randomUUID().toString().substring(0,8);
    }
}

