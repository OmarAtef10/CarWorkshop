package CustomizedUtilities;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class TimeUtility {
    public static String getCurrentDate() {
        LocalDateTime dateTime = LocalDateTime.now();

        //YY/MM/DD
//        return dateTime.getYear()+"-"+dateTime.getMonthValue()+"-"+dateTime.getDayOfMonth()
//                +" :: "+dateTime.getHour()+":"+dateTime.getMinute()+":"+dateTime.getSecond()+":"+dateTime.getNano();
//    }
        return dateTime.toString();
    }
    public static String getReformedDate(String date){
        return date.substring(0,date.indexOf("T"));
    }

}
