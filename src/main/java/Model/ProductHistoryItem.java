package Model;

import java.sql.ResultSet;
import java.time.LocalDateTime;

public class ProductHistoryItem {
    private int ProductId;
    private String timeStamp;
    private int units;
    private String action;


    public ProductHistoryItem(int productId, String timeStamp, int units, String action) {
        ProductId = productId;
        this.timeStamp = timeStamp;
        this.units = units;
        this.action = action;
    }

    public ProductHistoryItem(int productId, int units, String action) {
        ProductId = productId;
        this.timeStamp = getCurrentDate();
        this.units = units;
        this.action = action;
    }


    public static ProductHistoryItem fromResultSet(ResultSet resultSet) {
        try {
            ProductHistoryItem productHistoryItem = new ProductHistoryItem(
                    resultSet.getInt("productId"),
                    resultSet.getString("timeStamp"),
                    resultSet.getInt("units"),
                    resultSet.getString("action")
            );
            return productHistoryItem;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCurrentDate(){
        LocalDateTime dateTime = LocalDateTime.now();
        //YY/MM/DD
        return dateTime.getYear()+"/"+dateTime.getMonthValue()+"/"+dateTime.getDayOfMonth()
                +" :: "+dateTime.getHour()+":"+dateTime.getMinute();
    }
}
