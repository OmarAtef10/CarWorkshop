package Model;

import Controller.InvoiceDao;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Report {
    private int reportId;
    private double totalSales;
    private ArrayList<Invoice> invoices;
    private String date;
    private String userName;

    public Report(){ }

    public Report(String userName,String date){
        this.userName = userName;
        this.date = date;
    }

    public Report(int reportId,String userName,String date){
        this.reportId = reportId;
        this.userName = userName;
        this.date = date;
    }

    public Report(int reportId, Double totalSales, ArrayList<Invoice> invoices, String date, String userName) {
        this.reportId = reportId;
        this.totalSales = totalSales;
        this.invoices = invoices;
        this.date = date;
        this.userName = userName;
    }

    public static Report fromResultSet(ResultSet resultSet){
        Report report = null;
        InvoiceDao invoiceDao = new InvoiceDao();
        try {
            report = new Report(
                    resultSet.getInt("reportId"),
                    resultSet.getString("username"),
                    resultSet.getString("date")
            );
            report.setInvoices( invoiceDao.getDailyUserInvoices( report.userName, report.date) );
        }catch (Exception e){
            e.printStackTrace();
        }
        return report;
    }

    public void setReportId(int reportId) {
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

    public int getReportId() {
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

    public double calculateTotalSales(){
        for(int i=0; i<invoices.size(); i++){
            this.totalSales += invoices.get(i).getTotalPaid();
        }
        return this.totalSales;
    }
}
