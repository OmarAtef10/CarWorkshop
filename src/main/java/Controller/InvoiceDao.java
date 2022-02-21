package Controller;

import Context.DBContext;
import Model.Cart;
import Model.Invoice;

public class InvoiceDao {
    public Invoice addInvoice(Invoice invoice){
        return DBContext.getDBContext().getDbService().addInvoice(invoice);
    };
    
    public Invoice getInvoice(String id){
        return DBContext.getDBContext().getDbService().getInvoice(id);
    };

    public Invoice updateInvoice(Invoice invoice){
        return DBContext.getDBContext().getDbService().updateInvoice(invoice);
    }

    public boolean deleteInvoice(String id){
        return DBContext.getDBContext().getDbService().deleteInvoice(id);
    }

    public boolean addInvoiceProduct(Invoice invoice){
        return DBContext.getDBContext().getDbService().addInvoiceProduct(invoice);
    }
}
