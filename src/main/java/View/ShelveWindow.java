package View;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;

import Context.Context;
import Controller.ProductDao;
import Controller.WindowLoader;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * ShelveWindow
 */
public class ShelveWindow extends VBox{

    public static final String FXML_NAME = ShelveWindow.class.getSimpleName() + ".fxml";
    Stage stage;

    
    @FXML
    private Button cancelBtn;

    @FXML
    private Button saveBtnPressed;

    @FXML
    private TextField shelfNumField;

    @FXML
    private TextField unitsField;

    private Product product;
    private ProductDao productDao = new ProductDao();


    public ShelveWindow(){
        try {
            FXMLLoader loader = WindowLoader.getLoader(FXML_NAME);
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
            
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Stage show(){
        stage = new Stage();
        stage.setScene(new Scene(this));
        stage.getScene().getStylesheets().addAll(Context.getContext().getCurrentTheme());
        stage.show();
        return stage;
    }

    @FXML
    void cancelBtnPressed(ActionEvent event) {
        stage.close();
    }

    @FXML
    void saveBtnPressed(ActionEvent event) {
        if(this.product == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product.", ButtonType.OK);
            alert.getDialogPane().getScene().getStylesheets().addAll(Context.getContext().getCurrentTheme());
            alert.show();
            return;
        }
        if(checkUnitsAvailibilty()){
            this.product.addLocation(shelfNumField.getText(),Integer.parseInt(unitsField.getText()));
            productDao.addProductShelf(this.product);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Insufficient units", ButtonType.OK);
            alert.getDialogPane().getScene().getStylesheets().addAll(Context.getContext().getCurrentTheme());
            alert.show();
        }
        cancelBtnPressed(new ActionEvent());
    }

    public boolean checkUnitsAvailibilty(){
        Hashtable<String,Integer> shelfed_products = productDao.getProductShelf(this.product.getProductId());
        int shelfed_products_count = 0;
        for(String p : shelfed_products.keySet()){
            shelfed_products_count += shelfed_products.get(p);
        }

        int unshelfed_prods = product.getUnits()- shelfed_products_count;
        if(!unitsField.getText().equals("")){
            int new_shelfed = Integer.parseInt(unitsField.getText());
            if(unshelfed_prods>=new_shelfed){
                return true;
            }
        }
        return false;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}