package Controller;

import Context.Context;
import Model.Cart;
import Model.Customer;
import Model.Invoice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
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
    private Button cancelBtn;

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

    private final InvoiceDao invoiceDao = new InvoiceDao();
    private final ProductDao productDao = new ProductDao();
    private final CustomerDao customerDao = new CustomerDao();
    private Cart cart;
    private Invoice invoice;
    private Customer customer;


    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Cart getCart() {
        return cart;
    }

    public void moveToCartBtnPressed(ActionEvent e) {

    }

    public void removeFromCartBtnPressed(ActionEvent e) {

    }

    public void manualAdjustTotalBtnPressed(ActionEvent e) {

    }

    public void shelfEntryBtnPressed(ActionEvent e) {

    }

    public void confirmBtnPressed(ActionEvent e) {

        this.invoice = new Invoice();
        this.invoice.setUserName(UserManager.getInstance().getCurrentUser().getUserName());

        String customerName = nameField.getText();
        String customerPhoneNumber = phoneField.getText();
        String carModel = carModelField.getText();
        if (!customerName.equals("") || !customerPhoneNumber.equals("") || !carModel.equals("")) {
            this.customer.setName(customerName);
            this.customer.setMobileNumber(customerPhoneNumber);
            this.customer.setCarModel(carModel);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong Information", ButtonType.OK);
            alert.getDialogPane().getScene().getStylesheets().addAll(Context.getContext().getCurrentTheme());
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            this.cart = customer.getCart();
            this.invoice.setCart(this.customer.getCart());

            if( customerDao.getCustomer(customerPhoneNumber) == null ){
                customerDao.addCustomer(this.customer);
            }else{
                customerDao.updateCustomer(this.customer);
            }
            invoiceDao.addInvoice(this.invoice);

            //Products units/shelfs
            ProductInvoiceManagerUtil productInvoiceManagerUtil = new ProductInvoiceManagerUtil(this.invoice);
            productInvoiceManagerUtil.updateShelves();

            cancelBtnPressed(new ActionEvent());
        } else {
            return;
        }

    }

    public void cancelBtnPressed(ActionEvent a) {
        ((Stage) cancelBtn.getScene().getWindow()).close();
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

        this.customer = new Customer();
        this.cart = this.customer.getCart();

    }

}
