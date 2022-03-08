package Model;

import java.sql.ResultSet;

public class Customer {

    private String name;
    private String mobileNumber;
    private String carModel;
    private Cart cart;

    public Customer(){
        this.cart = new Cart();
    }

    public Customer( String name, String mobileNumber, String carModel){
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.carModel=carModel;
        this.cart = new Cart();
    }

    public static Customer fromResultSet(ResultSet resultSet){
        Customer customer = new Customer();

        try {
            customer.name = resultSet.getString("name");
            customer.mobileNumber = resultSet.getString("phone");
            customer.carModel =  resultSet.getString("carModel");
            customer.cart = new Cart();
        }catch (Exception e){
            e.printStackTrace();
        }

        return customer;
    }

    //TODO
    public void checkOut(){

    }


    public void setName(String name) {
        this.name = name;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public Cart getCart() {
        return cart;
    }
}
