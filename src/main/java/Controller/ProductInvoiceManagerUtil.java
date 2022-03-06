package Controller;

import Model.Cart;
import Model.Invoice;
import Model.Oil;
import Model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class ProductInvoiceManagerUtil {
    private HashMap<String, Integer> products; //invoice products ids / units per product
    private Invoice invoice;
    private final ProductDao productDao = new ProductDao();

    public ProductInvoiceManagerUtil(Invoice invoice) {
        this.invoice = invoice;
        products = invoice.getCart().getProducts();
    }

    private void updateShelves(){
        for(String productId : products.keySet()){
            Product product = productDao.getOrNull(productId);
            try {
                int req_units = products.get(productId);
                if(product.getUnits() >= req_units){
                    updateProduct(product,req_units);
                }else{
                    throw new Exception("Insufficient units available");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateProduct(Product product,int req_units){
        Hashtable<String,Integer> shelves = new Hashtable<>(productDao.getProductShelf(product.getProductId()));
        while (req_units!=0) {
            for (String shelve : shelves.keySet()) {
                if (shelves.get(shelve) == req_units) {
                    product.setUnits(product.getUnits() - req_units);

                    productDao.removeProductShelf(shelve);
                    product.getLocations().remove(shelve);

                    productDao.updateProduct(product);
                    shelves.replace(shelve,0);
                    req_units=0;
                } else if (shelves.get(shelve) > req_units) {
                    product.setUnits(product.getUnits() - req_units);
                    product.getLocations().replace(shelve, product.getUnits());

                    productDao.updateProduct(product);
                    productDao.removeProductShelf(shelve);
                    productDao.addUniqueProductShelf(product.getProductId(),
                            shelve, product.getUnits(), ((Oil) product).getExpiryDate());

                    req_units=0;
                } else if (shelves.get(shelve) < req_units) {
                    req_units -= shelves.get(shelve);
                    productDao.removeProductShelf(shelve);
                    shelves.replace(shelve,0);
                }
            }
        }
    }

    public static void main(String[] args) {
        new ProductDao().getOrNull("b7bdddc7");
        //
//        ProductDao d = new ProductDao();
//        Product product = d.getOrNull("b7bdddc7");
//        Invoice invoice = new Invoice();
//        invoice.setCart(new Cart());
//        invoice.getCart().addProduct(product,8);
//
//        ProductInvoiceManagerUtil productInvoiceManagerUtil = new ProductInvoiceManagerUtil(invoice);
//        productInvoiceManagerUtil.updateShelves();

    }
}
