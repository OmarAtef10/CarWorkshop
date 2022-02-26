package Controller;

import Context.DBContext;
import Model.Invoice;
import Model.Report;

import java.util.ArrayList;
import java.util.Hashtable;

public class ReportDao {
    public Report addReport(Report report){
        return DBContext.getDBContext().getDbService().addReport(report);
    };

    public boolean deleteReport(String reportId){
        return DBContext.getDBContext().getDbService().deleteReport(reportId);
    }

    public Report updateReport(Report report){
        return DBContext.getDBContext().getDbService().updateReport(report);
    }

    public ArrayList<Report> getReportsByUsername(String userName){
        return DBContext.getDBContext().getDbService().getReportsByUsername(userName);
    }

    public ArrayList<Report> getReportsByDate(String date){
        return DBContext.getDBContext().getDbService().getReportsByDate(date);
    }

    public Report getReportByUsernameDate(String userName, String date){
        return DBContext.getDBContext().getDbService().getReportByUsernameDate(userName,date);
    }

    public Report getDailyMainReport(String date){
        ArrayList<Report> dailyReports = getReportsByDate(date);
        ArrayList<Invoice> dailyInvoices = new ArrayList<>();
        Hashtable<String,Integer> soldProduct = new Hashtable<>();

        for(Report report : dailyReports){
            dailyInvoices.addAll(report.getInvoices());
            Hashtable<String,Integer> reportProducts = report.getSoldProducts();
            if(reportProducts.size() == 0){
                continue;
            }
            for(int i=0; i<reportProducts.size(); i++){
                for(String key : reportProducts.keySet()){
                    if (soldProduct.containsKey(key)) {
                        int new_count = reportProducts.get(key);
                        new_count += soldProduct.get(key);
                        soldProduct.put(key,new_count);
                    }else{
                        soldProduct.put(key,reportProducts.get(key));
                    }
                }
            }
        }

        Report mainReport = new Report();
        mainReport.setInvoices(dailyInvoices);
        mainReport.setSoldProducts(soldProduct);
        mainReport.calculateTotalSales();

        return mainReport;

    }

}
