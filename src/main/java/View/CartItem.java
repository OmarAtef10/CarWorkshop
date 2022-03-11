package View;

import Model.Product;

public class CartItem {
    private Product product;
    private String name;
    private int units;
    private double price;
    public CartItem(String name, int units, double price) {
        this.name = name;
        this.units = units;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getUnits() {
        return units;
    }
    public void setUnits(int units) {
        this.units = units;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}