package Model;

import Controller.ProductDao;

import java.sql.ResultSet;
import java.util.HashMap;


public class Cart {
    private HashMap<String, Integer> products; // productId,Units
    private double total;

    public Cart(){
        products = new HashMap<>();
        setTotal(0.0);
    }

    public static Cart fromResultSet(ResultSet resultSet){
        Cart cart = new Cart();
        HashMap<String,Integer> resultProducts = new HashMap<>();
        try {
            while (resultSet.next()){
                resultProducts.put(resultSet.getString("productId"),resultSet.getInt("units"));
            }
            cart.setProducts(resultProducts);
            return cart;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Integer> products) {
        this.products = products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void addProduct(Product product, int units){
        ProductDao productDao = new ProductDao();

        products.put(product.getProductId(), units);
        setTotal(getTotal() + (product.getPricePerUnit() * units));

    }

    public void removeProduct(Product product){
        ProductDao productDao = new ProductDao();

        int units = products.remove(product.getProductId());
        setTotal(getTotal() - (units * product.getPricePerUnit()));
    }

    @Override
    public String toString() {
        return this.products.toString();
    }
}
