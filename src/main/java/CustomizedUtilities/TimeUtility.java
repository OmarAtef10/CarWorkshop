package CustomizedUtilities;

import java.time.LocalDateTime;

public class TimeUtility {
    public static String getCurrentDate(){
        LocalDateTime dateTime = LocalDateTime.now();
        //YY/MM/DD
        return dateTime.getYear()+"/"+dateTime.getMonthValue()+"/"+dateTime.getDayOfMonth()
                +" :: "+dateTime.getHour()+":"+dateTime.getMinute();
    }
    public static String getReformedDate(String date){
        return date.substring(0,date.indexOf("::"));
    }
}
