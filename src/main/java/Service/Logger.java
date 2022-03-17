package Service;

import java.io.File;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import Context.Context;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Logger {
    private final static String logPath = "logs/";
    private final static String currLogFilePath = logPath + LocalDate.now().toString() + ".txt";
    private static boolean loggingEnabled = true;

    public enum Level{
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        FATAL;
    
        @Override
        public String toString() {
            return this.name();
        }
    }

    private static String getCurrentMethod(){
        String stackTraceString = "";
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        stackTraceString += stackTrace[2].getClassName() + ".";
        stackTraceString += stackTrace[2].getMethodName();
        return stackTraceString;
    }

    private static void showFatalAlert(String msg){
        Alert alert = new Alert(AlertType.ERROR, "Please contact system admin: " + msg, ButtonType.CLOSE);
        alert.getDialogPane().getStylesheets().addAll(Context.getContext().getCurrentTheme());
        alert.showAndWait();
        if(alert.getResult() == ButtonType.CLOSE){
            System.exit(1);
        }
    }

    public static void log(Level level, String msg){
        if(!loggingEnabled)
            return;
        File logfile = new File(currLogFilePath);
        FileWriter writer;
        try {
            writer = new FileWriter(logfile, true);
            String logMsg = getCurrentInstant() + " " + 
            String.format("%-10s", level) + ": " + 
            getCurrentMethod() + " " +
            msg + "\n";
            writer.append(logMsg);
            
            writer.close();
            if(level == Level.FATAL)
                showFatalAlert(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentInstant(){
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        final SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
        return sdf3.format(stamp);
    }
}
