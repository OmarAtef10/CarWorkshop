package Model;

import java.sql.ResultSet;

import Controller.ProductDao;


public class Oil extends Product {
    private int mileage;
    private String viscosity;
    private String expiryDate;


    public Oil(String vendor, int units, double pricePerUnit,
               double marketPrice, String viscosity, int mileage, String expiryDate) {
        super(vendor, units, pricePerUnit, marketPrice);
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
            oil.setProductId( resultSet.getString("productId") );
            ProductDao dao = new ProductDao();
            oil.setProductHistory(dao.getHistory(oil.getProductId()));
            oil.setLocations(dao.getProductShelf(oil.getProductId()));

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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setNameUpdate(){
        String productName = this.getVendor() + " " + mileage + " " + viscosity;
        this.setProductName(productName);
    }
    @Override
    public String toString() {
        return "Oil{" +
                "productId="+ getProductId() +
                ", mileage=" + mileage +
                ", viscosity='" + viscosity + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
}
