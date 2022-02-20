package Model;

import Controller.ProductDao;

import java.sql.ResultSet;
import java.util.ArrayList;

public abstract class Product {
    private String productName;
    private String vendor;
    private int units;
    private double pricePerUnit;
    private double marketPrice;
    private int productId;
    private ArrayList<ProductHistoryItem> productHistory;


    public Product() {
    }

    ;

    public Product(String vendor, int units, double pricePerUnit, double marketPrice) {
        this.vendor = vendor;
        this.units = units;
        this.pricePerUnit = pricePerUnit;
        this.marketPrice = marketPrice;
        this.productId = -1;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public ArrayList<ProductHistoryItem> getProductHistory() {
        return productHistory;
    }

    public void setProductHistory(ArrayList<ProductHistoryItem> productHistory) {
        this.productHistory = productHistory;
    }

    public void loadHistory() {
        ProductDao productDao = new ProductDao();
        productHistory = productDao.getHistory(this.productId);
    }
}
