package Model;

import java.sql.ResultSet;

public class Oil extends Product {
    private int mileage;
    private String viscosity;
    private int containerPrice;
    private String expiryDate;


    public Oil(String vendor, int units, double pricePerContainer, double marketPrice, String viscosity, int mileage, String expiryDate) {
        super(vendor, units, pricePerContainer, marketPrice);
        String productName = vendor + " " + mileage + " " + viscosity;
        this.setProductName(productName);
        this.mileage = mileage;
        this.viscosity = viscosity;
        this.expiryDate = expiryDate;
    }

    public static Oil fromResultSet(ResultSet resultSet){
        Oil oil = null;
        try {
            String vendor = resultSet.getString("vendor");
            String viscosity = resultSet.getString("viscosity");
            String expiryDate = resultSet.getString("expiryDate");
            oil = new Oil(
                    vendor == null? "": vendor,
                    resultSet.getInt("units"),
                    resultSet.getDouble("price"),
                    resultSet.getDouble("marketPrice"),
                    viscosity == null? "": viscosity,
                    resultSet.getInt("range"),
                    expiryDate == null? "": expiryDate
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
                ", containerPrice=" + containerPrice +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
}
