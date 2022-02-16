package Model;

import java.util.Hashtable;

public abstract class Product {

    private String productName;
    private String manufacturer;
    private int units;
    private double pricePerUnit;
    private double marketPrice;
    private int productID;


    public Product(String manufacturer, int units, double pricePerUnit, double marketPrice) {
        this.manufacturer = manufacturer;
        this.units = units;
        this.pricePerUnit = pricePerUnit;
        this.marketPrice = marketPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
}
