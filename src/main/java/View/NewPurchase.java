package View;

import Context.Context;
import Controller.WindowLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class NewPurchase extends AnchorPane{
    //IDEA: Add chechboxes in products row for the user to select before pressing new purchase
    public static String FXML_NAME = NewPurchase.class.getSimpleName() + ".fxml";
    private final FXMLLoader loader;
    private Stage stage;

    public NewPurchase() {
        loader = WindowLoader.getLoader(FXML_NAME);
        try {
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (Exception e) {
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
    private TextField amountField;

    @FXML
    private TextField carModelField;

    @FXML
    private TableView<?> customerCart;

    @FXML
    private TableView<?> inventoryTable;

    @FXML
    private Button manualAdjustTotalBtn;

    @FXML
    private Button moveToCartBtn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField productNameSearchField;

    @FXML
    private Button removeFromCartBtn;

    @FXML
    private Button shelfEntryBtn;

    @FXML
    private TextField totalField;

    @FXML
    void manualAdjustTotalBtnPressed(ActionEvent event) {

    }

    @FXML
    void moveToCartBtnPressed(ActionEvent event) {

    }

    @FXML
    void removeFromCartBtnPressed(ActionEvent event) {

    }

    @FXML
    void shelfEntryBtnPressed(ActionEvent event) {

    }

}
