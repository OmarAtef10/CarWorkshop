package Model;

import Controller.ProductDao;
import CustomizedUtilities.UUID_Utility;

import java.util.ArrayList;
import java.util.Hashtable;

public abstract class Product {
    private String productName;
    private String vendor;
    private int units;
    private double pricePerUnit;
    private double marketPrice;
    private String productId;  //TODO n5aleh UUID
    protected Hashtable<String,Integer> locations; // R# S# - units
    private ArrayList<ProductHistoryItem> productHistory;

    public Product() {}

    public Product(String vendor, int units, double pricePerUnit, double marketPrice) {
        this.vendor = vendor;
        this.units = units;
        this.pricePerUnit = pricePerUnit;
        this.marketPrice = marketPrice;
        this.productId = UUID_Utility.generateId();
        this.locations = new Hashtable<>();
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Hashtable<String, Integer> getLocations() {
        return locations;
    }

    public void setLocations(Hashtable<String, Integer> locations) {
        this.locations = locations;
    }

    public void addLocation(String shelfNumber,int units){
        this.locations.put(shelfNumber,units);
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
