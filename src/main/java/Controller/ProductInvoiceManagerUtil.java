package Controller;

import Context.DBContext;
import Model.Cart;
import Model.Invoice;
import Model.Oil;
import Model.Product;
import Service.SQLiteService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class ProductInvoiceManagerUtil {
    private HashMap<String, Integer> products; //invoice products ids / units per product
    private Hashtable<String,Integer> shelf_units_sold;
    private Hashtable<String,Integer> returnShelves;
    private Invoice invoice;
    private final ProductDao productDao = new ProductDao();

    public ProductInvoiceManagerUtil(Invoice invoice) {
        this.invoice = invoice;
        products = invoice.getCart().getProducts();
        this.shelf_units_sold = new Hashtable<>();
        this.returnShelves = new Hashtable<>();
    }

    public void updateShelves(){
        for(String productId : products.keySet()){
            Product product = productDao.getOrNull(productId);
            try {
                int req_units = products.get(productId);
                int currentProductShelfedUnits = new ProductDao().getProductShelfedUnits(product);
                if(currentProductShelfedUnits >= req_units){
                    updateProduct(product,req_units);
                }else{
                    throw new Exception("Insufficient units available");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
//TODO
    private void updateProduct(Product product,int req_units){
        Hashtable<String,Integer> shelves = new Hashtable<>(productDao.getProductShelf(product.getProductId()));

        int totalRemoved = 0;
        for(String shelf : shelves.keySet()){
            int currentRemove = 0;
            int current_units = shelves.get(shelf);
            if(current_units == req_units){
                totalRemoved += current_units;
                currentRemove  = current_units;
                current_units = 0;
                this.shelf_units_sold.put(shelf,current_units);
                this.returnShelves.put(shelf,currentRemove);
            }else if(current_units > req_units){
                totalRemoved += req_units;
                currentRemove = req_units;
                current_units = current_units - req_units;

                this.shelf_units_sold.put(shelf,req_units);
                this.returnShelves.put(shelf,currentRemove);
            }else if(current_units < req_units){
                totalRemoved += current_units;
                currentRemove = current_units;
                current_units = 0;

                this.shelf_units_sold.put(shelf,current_units);
                this.returnShelves.put(shelf,currentRemove);
            }
            shelves.replace(shelf,current_units);

            req_units -= currentRemove;
            if( req_units <= 0){
                break;
            }
        }
        product.setUnits( product.getUnits() - totalRemoved );
        productDao.updateProduct(product);
        for(String shelf : shelves.keySet()){
            if(shelves.get(shelf) == 0){
                productDao.removeProductShelf(shelf);
            }else{
                productDao.updateShelf(shelf,shelves.get(shelf));
            }

        }

    }

    public Hashtable<String, Integer> getShelf_units_sold() {
        return shelf_units_sold;
    }

    public Hashtable<String, Integer> getReturnShelves() {
        return returnShelves;
    }

    public static void main(String[] args) {
        DBContext.getDBContext().setDbService(new SQLiteService());
        ProductDao d = new ProductDao();
        Product product = d.getOrNull("b7bdddc7");
        Invoice invoice = new Invoice();
        invoice.setCart(new Cart());
        invoice.getCart().addProduct(product,8);

        ProductInvoiceManagerUtil productInvoiceManagerUtil = new ProductInvoiceManagerUtil(invoice);
        productInvoiceManagerUtil.updateShelves();

    }
}
