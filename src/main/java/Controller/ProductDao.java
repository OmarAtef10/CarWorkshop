package Controller;

import Context.DBContext;
import Model.Product;

public class ProductDao {
    //TODO: Implment methods
    public boolean addProduct(Product product){
        return DBContext.getDBContext().getDbService().addProduct(product);
    };
    
    public Product getProduct(String name , String vendor){
        return DBContext.getDBContext().getDbService().getProduct(name, vendor);
    };

    public boolean updateProduct(Product product){
        return DBContext.getDBContext().getDbService().updateProduct(product);
    }

    public Product deleteProduct(int id){
        return null;
    }

    public Product shelveProduct(int productId, String shelfId, int units){
        return null;
    }
}
