package Model;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

//TODO DATEEEEEEEEES
public class Report {
    private int reportId;
    private Double totalSales;
    private ArrayList<Invoice> invoices;
    private LocalDateTime date;
    private String userName;
    private LocalDateTime sessionStart;
    //2olna hn3mlha String 3shan al SQLite we7na bnrg3 objects wkda whnst5dm al Date class ngeb
    //mno al date wn3mlo toString wn7to fel variable bs keda
    private LocalDateTime sessionEnd;

    public Report(){ }
    public Report(int reportId,String userName,String date){ }

    public Report(int reportId, Double totalSales, ArrayList<Invoice> invoices, LocalDateTime date, String userName, LocalDateTime sessionStart, LocalDateTime sessionEnd) {
        this.reportId = reportId;
        this.totalSales = totalSales;
        this.invoices = invoices;
        this.date = date;
        this.userName = userName;
        this.sessionStart = sessionStart;
        this.sessionEnd = sessionEnd;
    }

    public Report fromResultSet(ResultSet resultSet){
        Report report = null;
        try {
            report = new Report(
                    resultSet.getInt("reportId"),
                    resultSet.getString("username"),
                    resultSet.getString("date")
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return report;
    }

    public void addInvoice(Invoice invoice){
        invoices.add(invoice);
    }

    public void removeInvoice(Invoice invoice){
        invoices.remove(invoice);
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public Double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Double totalSales) {
        this.totalSales = totalSales;
    }

    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(LocalDateTime sessionStart) {
        this.sessionStart = sessionStart;
    }

    public LocalDateTime getSessionEnd() {
        return sessionEnd;
    }

    public void setSessionEnd(LocalDateTime sessionEnd) {
        this.sessionEnd = sessionEnd;
    }
}
