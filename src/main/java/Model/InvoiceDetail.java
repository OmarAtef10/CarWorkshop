package Model;

import Controller.CustomerDao;
import Controller.ProductDao;
import View.CartItem;

import java.util.ArrayList;
import java.util.HashMap;

public class InvoiceDetail {
    String invoiceId;
    String customerName;
    String customerPhone;
    String customerCarModel;
    String cashierName;
    String invoiceDate;
    double invoiceTotal;
    ArrayList<CartItem> items;

    public InvoiceDetail(Invoice invoice) {
        CustomerDao dao = new CustomerDao();
        ProductDao dao2 = new ProductDao();
        Customer customer = dao.getCustomer(invoice.getCustomerId());
        items = new ArrayList<>();

        setCashierName(invoice.getUserName());
        setCustomerCarModel(customer.getCarModel());
        setCustomerName(customer.getName());
        setCustomerPhone(customer.getMobileNumber());
        setInvoiceDate(invoice.getDate());
        setInvoiceId(invoice.getInvoiceId());
        setInvoiceTotal(invoice.getTotalPaid());

        HashMap<String, Integer> products = invoice.getCart().getProducts();
        for (String id : products.keySet()) {
            Product product = dao2.getOrNull(id);
            CartItem item = new CartItem(product.getProductName(), products.get(id), product.getPricePerUnit());
            items.add(item);
        }
    }

    public InvoiceDetail(String invoiceId, String customerName, String customerPhone, String customerCarModel,
                         String cashierName, String invoiceDate, double invoiceTotal) {
        this.invoiceId = invoiceId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerCarModel = customerCarModel;
        this.cashierName = cashierName;
        this.invoiceDate = invoiceDate;
        this.invoiceTotal = invoiceTotal;
    }

    public InvoiceDetail() {
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerCarModel() {
        return customerCarModel;
    }

    public void setCustomerCarModel(String customerCarModel) {
        this.customerCarModel = customerCarModel;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public double getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartItem> items) {
        this.items = items;
    }
}
