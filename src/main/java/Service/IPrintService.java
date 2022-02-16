package Service;

import Model.Invoice;

public interface IPrintService {
    public void printCustomerInvoice(Invoice invoice);
    public void printReport(int reportId);
    public void printEmployeeReport(String userName);
}
