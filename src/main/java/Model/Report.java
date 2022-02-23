package Model;

import Controller.InvoiceDao;
import CustomizedUtilities.UUID_Utility;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;

public class Report {
    private String reportId;
    private double totalSales;
    private ArrayList<Invoice> invoices;
    private Hashtable<String, Integer> soldProducts;
    private String date;
    private String userName;

    public Report() {
    }

    public Report(String userName, String date) {
        this.userName = userName;
        this.date = date;
        this.reportId = UUID_Utility.generateId();
        InvoiceDao invoiceDao = new InvoiceDao();
        invoices = invoiceDao.getDailyUserInvoices(userName, date);
        soldProducts = new Hashtable<>();
        for (int i = 0; i < invoices.size(); i++) {
            totalSales += invoices.get(i).getTotalPaid();
            Cart cart = invoiceDao.getCart(invoices.get(i));
            for (String id : cart.getProducts().keySet()) {
                if (soldProducts.containsKey(id)) {
                    int add = cart.getProducts().get(id);
                    add += soldProducts.get(id);
                    soldProducts.put(id, add);
                } else {
                    soldProducts.put(id, cart.getProducts().get(id));
                }
            }
        }
    }

    public Report(String reportId, String userName, String date) {
        this.reportId = reportId;
        this.userName = userName;
        this.date = date;
    }

    public Report(String reportId, Double totalSales, ArrayList<Invoice> invoices, String date, String userName) {
        this.reportId = reportId;
        this.totalSales = totalSales;
        this.invoices = invoices;
        this.date = date;
        this.userName = userName;
    }

    public static Report fromResultSet(ResultSet resultSet) {
        Report report = null;
        InvoiceDao invoiceDao = new InvoiceDao();
        try {
            report = new Report(
                    resultSet.getString("reportId"),
                    resultSet.getString("username"),
                    resultSet.getString("date")
            );
            report.setInvoices(invoiceDao.getDailyUserInvoices(report.userName, report.date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public void setTotalSales(Double totalSales) {
        this.totalSales = totalSales;
    }

    public void setInvoices(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReportId() {
        return reportId;
    }

    public Double getTotalSales() {
        return totalSales;
    }

    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }

    public String getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public double calculateTotalSales() {
        for (int i = 0; i < invoices.size(); i++) {
            this.totalSales += invoices.get(i).getTotalPaid();
        }
        return this.totalSales;
    }
}
