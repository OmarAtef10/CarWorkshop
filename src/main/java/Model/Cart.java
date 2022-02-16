package Model;

import java.sql.ResultSet;
import java.util.HashMap;


public class Cart {
    private HashMap<Integer, Integer> products;
    private double total;

    Cart(){
        products = new HashMap<>();
        setTotal(0.0);
    }

    public static Cart fromResultSet(ResultSet resultSet){
        Cart cart = new Cart();
        try {

        }catch (Exception e){
            e.printStackTrace();
        }
        return cart;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void addProduct(Product product, int units){
        products.put(product.getProductId(), units);
        setTotal(getTotal() + (product.getPricePerUnit() * units)); 
    }

    public void removeProduct(Product product){
        int units = products.remove(product.getProductId());
        setTotal(getTotal() - (units * product.getPricePerUnit()));
    }
}
