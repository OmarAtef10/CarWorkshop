package Model;

import CustomizedUtilities.TimeUtility;

import java.sql.ResultSet;

public class ProductHistoryItem {
    private String productId;
    private String timeStamp;
    private int units;
    private String action;


    public ProductHistoryItem(String productId, String timeStamp, int units, String action) {
        this.productId = productId;
        this.timeStamp = timeStamp;
        this.units = units;
        this.action = action;
    }

    public ProductHistoryItem(String productId, int units, String action) {
        this.productId = productId;
        this.timeStamp = TimeUtility.getCurrentDate();
        this.units = units;
        this.action = action;
    }


    public static ProductHistoryItem fromResultSet(ResultSet resultSet) {
        try {
            ProductHistoryItem productHistoryItem = new ProductHistoryItem(
                    resultSet.getString("productId"),
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
}
