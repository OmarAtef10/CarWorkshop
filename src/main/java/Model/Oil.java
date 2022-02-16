package Model;

public class Oil extends Product {
    private int mileage;
    private String viscosity;
    private double pricePerContainer;
    private int containerPrice;
    private String expiryDate;

    public Oil(String manufacturer, int units, double pricePerContainer, double marketPrice, String viscosity, int mileage, String expiryDate) {
        super(manufacturer, units, pricePerContainer, marketPrice);
        String productName = manufacturer + " " + mileage + " " + viscosity;
        this.setProductName(productName);
        this.setMileage(mileage);
        this.setViscosity(viscosity);
        this.setPricePerContainer(pricePerContainer);
        this.expiryDate = expiryDate;

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
                "mileage=" + mileage +
                ", viscosity='" + viscosity + '\'' +
                ", pricePerContainer=" + pricePerContainer +
                ", containerPrice=" + containerPrice +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
}
