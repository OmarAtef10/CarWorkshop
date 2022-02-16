package Model;

public class Customer {
    private int userId;
    private String name;
    private String mobileNumber;
    private String carModel;
    private Cart cart;

    public Customer(int userId,String name,String mobileNumber,String carModel){
        this.userId = userId;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.carModel=carModel;
        this.cart = new Cart();
    }

    public Customer fromResultSet(){
        return null;
    }

    //TODO
    public void checkOut(){

    }
}
