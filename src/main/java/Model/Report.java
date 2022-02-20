package Model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
//TODO DATEEEEEEEEES
public class Report {
    private int reportId;
    private Double totalSales;
    private ArrayList<Invoice> invoices;
    private Date date;
    private String userName;
    private Date sessionStart;
    //2olna hn3mlha String 3shan al SQLite we7na bnrg3 objects wkda whnst5dm al Date class ngeb
    //mno al date wn3mlo toString wn7to fel variable bs keda
    private Date sessionEnd;

    public Report(){ }
    public Report(int reportId,String userName,String date){ }

    public Report(int reportId, Double totalSales, ArrayList<Invoice> invoices, Date date, String userName, Date sessionStart, Date sessionEnd) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(Date sessionStart) {
        this.sessionStart = sessionStart;
    }

    public Date getSessionEnd() {
        return sessionEnd;
    }

    public void setSessionEnd(Date sessionEnd) {
        this.sessionEnd = sessionEnd;
    }
}
