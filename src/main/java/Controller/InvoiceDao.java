package Controller;

import Context.DBContext;
import Model.Cart;
import Model.Invoice;

import java.util.ArrayList;

public class InvoiceDao {
    public Invoice addInvoice(Invoice invoice) {
        return DBContext.getDBContext().getDbService().addInvoice(invoice);
    }

    ;

    public Invoice getInvoice(String id) {
        return DBContext.getDBContext().getDbService().getInvoice(id);
    }

    ;

    public Invoice updateInvoice(Invoice invoice) {
        return DBContext.getDBContext().getDbService().updateInvoice(invoice);
    }

    public boolean deleteInvoice(String id) {
        return DBContext.getDBContext().getDbService().deleteInvoice(id);
    }

    public boolean addInvoiceProduct(Invoice invoice) {
        return DBContext.getDBContext().getDbService().addInvoiceProduct(invoice);
    }

    public Cart getCart(Invoice invoice) {
        return DBContext.getDBContext().getDbService().getCart(invoice);
    }

    public ArrayList<Invoice> getDailyUserInvoices(String username, String date) {
        return DBContext.getDBContext().getDbService().getDailyUserInvoices(username, date);
    }

    public ArrayList<Invoice> getDailyInvoices(String date) {
        return DBContext.getDBContext().getDbService().getDailyInvoices(date);
    }

    public ArrayList<Invoice> getInvoiceByUsername(String username) {
        return DBContext.getDBContext().getDbService().getInvoiceByUsername(username);
    }

    public ArrayList<Invoice> getAll() {
        return DBContext.getDBContext().getDbService().getAll();
    }

    public ArrayList<Invoice> searchBtn(String Id, String Date, String username) {
        return DBContext.getDBContext().getDbService().searchBtnQuery(Id, Date, username);
    }
}
