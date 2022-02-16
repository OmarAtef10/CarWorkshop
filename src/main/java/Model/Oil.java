package Model;

import java.sql.ResultSet;

public class Oil extends Product {
    private int mileage;
    private String viscosity;
    private double pricePerContainer;
    private int containerPrice;
    private String expiryDate;


    public Oil(String vendor, int units, double pricePerContainer, double marketPrice, String viscosity, int mileage, String expiryDate) {
        super(vendor, units, pricePerContainer, marketPrice);
        String productName = vendor + " " + mileage + " " + viscosity;
        this.setProductName(productName);
        this.mileage = mileage;
        this.viscosity = viscosity;
        this.pricePerContainer = pricePerContainer;
        this.expiryDate = expiryDate;

    }

    public static Oil fromResultSet(ResultSet resultSet){
        Oil oil = null;
        try {
            oil = new Oil(
                    resultSet.getString("vendor"),
                    resultSet.getInt("units"),
                    resultSet.getDouble("price"),
                    resultSet.getDouble("marketPrice"),
                    resultSet.getString("viscosity"),
                    resultSet.getInt("range"),
                    resultSet.getString("expiryDate")
            );
            oil.setProductName(oil.getVendor()+" "+oil.getMileage()+" "+oil.getViscosity());
            oil.setProductId( resultSet.getInt("productId") );
        }catch (Exception e){
            e.printStackTrace();
        }
        return oil;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getViscosity() {
        return viscosity;
    }

    public void setViscosity(String viscosity) {
        this.viscosity = viscosity;
    }

    public double getPricePerContainer() {
        return pricePerContainer;
    }

    public void setPricePerContainer(double pricePerContainer) {
        this.pricePerContainer = pricePerContainer;
    }

    public int getContainerPrice() {
        return containerPrice;
    }

    public void setContainerPrice(int containerPrice) {
        this.containerPrice = containerPrice;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Oil{" +
                "productId="+ getProductId() +
                ", mileage=" + mileage +
                ", viscosity='" + viscosity + '\'' +
                ", pricePerContainer=" + pricePerContainer +
                ", containerPrice=" + containerPrice +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
}
