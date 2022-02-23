package Controller;

import Context.DBContext;
import Model.Report;

import java.util.ArrayList;

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

}
