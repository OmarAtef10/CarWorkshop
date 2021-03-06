package View;

import java.io.IOException;
import java.net.MalformedURLException;

import Context.Context;
import Controller.ProductDao;
import Controller.WindowLoader;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddStockWindow extends VBox{

    public static final String FXML_NAME = AddStockWindow.class.getSimpleName() + ".fxml";
    Stage stage;

    private Product product;

    public AddStockWindow(){
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
    private Button cancelBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField unitsField;

    @FXML
    void cancelBtnPressed(ActionEvent event) {
        stage.close();
    }

    @FXML
    void saveBtnPressed(ActionEvent event) {
        ProductDao productDao = new ProductDao();
        int newStockCount = product.getUnits();
        newStockCount += Integer.parseInt(unitsField.getText());
        product.setUnits( newStockCount );
        productDao.updateProduct(product);
        cancelBtnPressed(new ActionEvent());
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
