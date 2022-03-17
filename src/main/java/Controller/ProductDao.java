package Controller;

import Context.DBContext;
import Model.Product;
import Model.ProductHistoryItem;
import org.sqlite.core.DB;
// import org.sqlite.core.DB;

import java.util.ArrayList;
import java.util.Hashtable;

public class ProductDao {
    //TODO: Implment methods
    public boolean addProduct(Product product){
        return DBContext.getDBContext().getDbService().addProduct(product);
    };
    
    public ArrayList<Product> getProduct(String name , String vendor){
        return DBContext.getDBContext().getDbService().getProduct(name, vendor);
    }

    public Product getProduct(String name){
        Product oil = DBContext.getDBContext().getDbService().getProduct(name);
        oil.setLocations( getProductShelf(name, oil.getVendor()) );
        return oil;
    }

    public boolean updateProduct(Product product){
        return DBContext.getDBContext().getDbService().updateProduct(product);
    }

    public int getProductShelfedUnits(Product product){
        return DBContext.getDBContext().getDbService().getProductShelfedUnits(product);
    }

    public Product getOrNull(String productId){
        return DBContext.getDBContext().getDbService().getOrNull(productId);
    }

    public boolean deleteProduct(String productName, String manufacturer){
        return DBContext.getDBContext().getDbService().deleteProduct(productName,manufacturer);
    }

    public ArrayList<ProductHistoryItem> getHistory(String productId){
        return DBContext.getDBContext().getDbService().getHistory(productId);
    }

    public Product shelveProduct(String productId, String shelfId, int units){
        return null;
    }

    public boolean addProductHistoryCreate(ProductHistoryItem productHistoryItem){
        return DBContext.getDBContext().getDbService().addProductHistoryCreate(productHistoryItem);
    }
    public boolean addProductHistoryEdit(ProductHistoryItem productHistoryItem){
        return DBContext.getDBContext().getDbService().addProductHistoryEdit(productHistoryItem);
    }
    public boolean addProductHistorySell(ProductHistoryItem productHistoryItem){
        return DBContext.getDBContext().getDbService().addProductHistoryEdit(productHistoryItem);
    }

    public boolean addProductShelf(Product product){
        return DBContext.getDBContext().getDbService().addProductShelf(product);
    }

    public Hashtable<String, Integer> getProductShelf(String productName , String vendor){
        return DBContext.getDBContext().getDbService().getProductShelf(productName,vendor);
    }

    public Hashtable<String, Integer> getProductShelf(String productId){
        return DBContext.getDBContext().getDbService().getProductShelf(productId);
    }

    public boolean addUniqueProductShelf(String productId, String shelfNumber,int units,String expiryDate){
        return DBContext.getDBContext().getDbService().addUniqueProductShelf(productId,shelfNumber,units,expiryDate);
    }

    public boolean removeProductShelf(String shelfName){
        return DBContext.getDBContext().getDbService().removeProductShelf(shelfName);
    }

    public boolean updateShelf(String shelfName,int units){
        return DBContext.getDBContext().getDbService().updateShelf(shelfName,units);
    }

    public ArrayList<Product> getAll(){
        return DBContext.getDBContext().getDbService().getAllProducts();
    }

    public ArrayList<Product> searchByQuery(String productName, String milage, String type, String vendor){
        return DBContext.getDBContext().getDbService().searchByQuery(productName,milage, type, vendor);
    }
}   
