package Controller;

import Context.DBContext;
import Model.Product;
import Model.ProductHistoryItem;

import java.util.ArrayList;

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

    public boolean deleteProduct(String productName, String manufacturer){
        return DBContext.getDBContext().getDbService().deleteProduct(productName,manufacturer);
    }

    public ArrayList<ProductHistoryItem> getHistory(int productId){
        return DBContext.getDBContext().getDbService().getHistory(productId);
    }

    public Product shelveProduct(int productId, String shelfId, int units){
        return null;
    }
}
