package View;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Context.Context;
import Controller.ProductDao;
import Controller.WindowLoader;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SearchWindow extends VBox{
    public static final String FXML_NAME = SearchWindow.class.getSimpleName() + ".fxml";
    private Stage stage;
    private ArrayList<Product> searchResults = new ArrayList<>();


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField productMilageField;

    @FXML
    private ComboBox<String> productTypeField;

    @FXML
    private Button searchBtn;

    @FXML
    private TextField vendorField;

    @FXML
    private TextField productNameField;

    @FXML
    void cancelBtnPressed(ActionEvent event) {
        stage.close();
    }

    public ArrayList<Product> getSearchResults() {
        return searchResults;
    }

    @FXML
    void searchBtnPressed(ActionEvent event) {
        ProductDao dao = new ProductDao();
        String productName = productNameField.getText();
        String milage = productMilageField.getText();
        String type = productTypeField.getValue();
        String vendor = vendorField.getText();

        searchResults = dao.searchByQuery(productName,milage, type, vendor);
        stage.close();
    }

    @FXML
    void initialize() {
        assert cancelBtn != null : "fx:id=\"cancelBtn\" was not injected: check your FXML file 'SearchWindow.fxml'.";
        assert productMilageField != null : "fx:id=\"productMilageField\" was not injected: check your FXML file 'SearchWindow.fxml'.";
        assert productTypeField != null : "fx:id=\"productTypeField\" was not injected: check your FXML file 'SearchWindow.fxml'.";
        assert searchBtn != null : "fx:id=\"searchBtn\" was not injected: check your FXML file 'SearchWindow.fxml'.";
        assert vendorField != null : "fx:id=\"vendorField\" was not injected: check your FXML file 'SearchWindow.fxml'.";
        productTypeField.getItems().addAll("", "Oil", "Service Part");
        productTypeField.setValue(productTypeField.getItems().get(0));
    }
    
    public SearchWindow(){
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
}
