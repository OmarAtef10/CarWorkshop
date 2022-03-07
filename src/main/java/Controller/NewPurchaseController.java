package Controller;

import Model.Cart;
import Model.Invoice;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class NewPurchaseController {


    @FXML
    private ResourceBundle resourceBundle;

    @FXML
    private URL location;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button manualAdjustTotalBtn;

    @FXML
    private Button moveToCartBtn;

    @FXML
    private Button removeFromCartBtn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField carModelField;

    @FXML
    private TextField totalField;

    @FXML
    private TextField amountField;

    @FXML
    private TextField productNameSearchField;


    private Cart cart;

    private Invoice invoice;


    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Cart getCart() {
        return cart;
    }



    @FXML
    void initialize() {
        assert manualAdjustTotalBtn != null : "fx:id=\"manualAdjustTotalBtn\" was not injected: check your FXML file 'NewPurchase.fxml'.";
        assert moveToCartBtn != null : "fx:id=\"moveToCartBtn\" was not injected: check your FXML file 'NewPurchase.fxml'.";
        assert removeFromCartBtn != null : "fx:id=\"removeFromCartBtn\" was not injected: check your FXML file 'NewPurchase.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'NewPurchase.fxml'.";
        assert phoneField != null : "fx:id=\"phoneField\" was not injected: check your FXML file 'NewPurchase.fxml'.";
        assert carModelField != null : "fx:id=\"carModelField\" was not injected: check your FXML file 'NewPurchase.fxml'.";
        assert totalField != null : "fx:id=\"totalField\" was not injected: check your FXML file 'NewPurchase.fxml'.";
        assert amountField != null : "fx:id=\"amountField\" was not injected: check your FXML file 'NewPurchase.fxml'.";
        assert productNameSearchField != null : "fx:id=\"productNameSearchField\" was not injected: check your FXML file 'NewPurchase.fxml'.";
        assert gridPane != null : "fx:id=\"gridPane\" was not injected: check your FXML file 'ProductWindow.fxml'.";

        invoice  = new Invoice();
        cart=new Cart();

    }

}
