package View;

import Context.Context;
import Controller.*;
import CustomizedUtilities.TimeUtility;
import Model.Cart;
import Model.Customer;
import Model.Invoice;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;


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
    private ResourceBundle resourceBundle;

    @FXML
    private URL location;

    @FXML
    private TableView<Product> inventoryTable;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button manualAdjustTotalBtn;

    @FXML
    private Button moveToCartBtn;

    @FXML
    private Button removeFromCartBtn;

    @FXML
    private TableView<CartItem> customerCart;

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
        String am = amountField.getText();
        if(!am.equals("")){
            int amount = Integer.parseInt(am);
            Product product = inventoryTable.getSelectionModel().getSelectedItem();
            if(product.getUnits() >= amount) {

                product.setUnits(product.getUnits() - amount);
                inventoryTable.refresh();
                if (product.getUnits() <= 0) {
                    inventoryTable.getItems().remove(product);
                }
                amountField.clear();

                //Check if item already in cart
                if(this.cart.getProducts().containsKey(product.getProductId())){
                    for(CartItem cartItem : customerCart.getItems()){
                        if(cartItem.getProduct().getProductId().equals(product.getProductId())){
                            customerCart.getItems().remove(cartItem);
                            int old_units = cartItem.getUnits();
                            int total_units = old_units + amount;
                            CartItem item = new CartItem(product.getProductName(), total_units, product.getPricePerUnit());
                            item.setProduct(product);
                            customerCart.getItems().add(item);
                        }
                    }
                }else {
                    CartItem item = new CartItem(product.getProductName(), amount, product.getPricePerUnit());
                    item.setProduct(product);
                    customerCart.getItems().add(item);
                }

                this.cart.addProduct(product,amount);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Insufficient units.", ButtonType.OK);
                alert.getDialogPane().getScene().getStylesheets().addAll(Context.getContext().getCurrentTheme());
                alert.show();
            }
        }
        totalCalculator();
    }
    public void removeFromCartBtnPressed(ActionEvent e) {
        CartItem cartItem = customerCart.getSelectionModel().getSelectedItem();
        Product product = cartItem.getProduct();
        String amtField = amountField.getText();
        if(amtField.equals("")) {
            if (product.getUnits() == 0) {
                inventoryTable.getItems().add(product);
            }
            product.setUnits(product.getUnits() + cartItem.getUnits());

            int index = customerCart.getSelectionModel().getSelectedIndex();
            customerCart.getItems().remove(index);
            cart.removeProduct(product);
        }else{
            int removed_amt = Integer.parseInt(amtField);
            int remaining_amt = cartItem.getUnits() - removed_amt;
            cartItem.setUnits(remaining_amt);
            if (product.getUnits() == 0) {
                inventoryTable.getItems().add(product);
            }
            product.setUnits(product.getUnits() + removed_amt);
            cart.removeProduct(product);
            cart.addProduct(product,remaining_amt);
            int index = customerCart.getSelectionModel().getSelectedIndex();
           if(cartItem.getUnits() == 0){
               customerCart.getItems().remove(index);
           }
           totalCalculator();
        }

        inventoryTable.refresh();
        customerCart.refresh();

    }

    public void manualAdjustTotalBtnPressed(ActionEvent e) {
        totalField.setEditable(true);
    }

    public void totalCalculator(){
        totalField.setEditable(false);
        totalField.setDisable(false);
        totalField.setText(new String(String.valueOf(cart.getTotal())));
    }

    @FXML
    void revertBtnPressed(ActionEvent event) {
        inventoryTable.getItems().clear();
        initData();
    }

    public void searchBtnPressed(ActionEvent e) {
        SearchWindow searchWindow = new SearchWindow();
        searchWindow.show().setOnHidden((arg0) -> {
            inventoryTable.getItems().clear();
            inventoryTable.getItems().addAll(searchWindow.getSearchResults());
        });

    }

    public void confirmBtnPressed(ActionEvent e) {

        this.invoice = new Invoice();
        this.invoice.setUserName(UserManager.getInstance().getCurrentUser().getUserName());

        String customerName = nameField.getText();
        String customerPhoneNumber = phoneField.getText();
        String carModel = carModelField.getText();
        cart.setTotal(Double.parseDouble(totalField.getText()));
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
        alert.getDialogPane().getScene().getStylesheets().addAll(Context.getContext().getCurrentTheme());
        alert.setTitle("Confirmation Dialog");
        //TODO show invoice details
        alert.setGraphic(new ImageView(new Image("D:\\jetbrains\\java projects\\CarWorkshop\\src\\main\\resources\\cena.gif")));
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            this.cart = customer.getCart();
            this.invoice.setCart(this.cart);

            if( customerDao.getCustomer(customerPhoneNumber) == null ){
                customerDao.addCustomer(this.customer);
            }else{
                customerDao.updateCustomer(this.customer);
            }
            this.invoice.setCustomerId( this.customer.getMobileNumber());
            this.invoice.setTotalPaid( this.cart.getTotal());
            this.invoice.setDate(TimeUtility.getCurrentDate());
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

        initTables();
        initData();
        moveToCartBtn.setDisable(true);
        removeFromCartBtn.setDisable(true);
        amountField.setDisable(true);
        totalField.setText("0.0");
    }

    private void initData() {
        inventoryTable.getItems().clear();
        ProductDao dao = new ProductDao();
        ArrayList<Product> products = dao.getAll();
        System.out.println(products.size());
        inventoryTable.getItems().addAll(products);
    }

    private void initTables() {
        TableColumn<Product, String> nameCol = new TableColumn<>("Product");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
    
        TableColumn<Product, Integer> unitsCol = new TableColumn<>("Units");
        unitsCol.setCellValueFactory(new PropertyValueFactory<>("units"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));
        
        TableColumn<CartItem, String> cartNameCol = new TableColumn<>("Product");
        cartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    
        TableColumn<CartItem, Integer> cartUnitsCol = new TableColumn<>("Units");
        cartUnitsCol.setCellValueFactory(new PropertyValueFactory<>("units"));

        TableColumn<CartItem, Double> cartPriceCol = new TableColumn<>("Price");
        cartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        inventoryTable.getColumns().addAll(nameCol, unitsCol, priceCol);
        customerCart.getColumns().addAll(cartNameCol, cartUnitsCol, cartPriceCol);

        inventoryTable.setRowFactory((arg0) -> {
            TableRow<Product> selectedRow = new TableRow<>();
            selectedRow.setOnMouseClicked((event) -> {
                moveToCartBtn.setDisable(false);
                removeFromCartBtn.setDisable(true);
                amountField.setDisable(false);
            });
            return selectedRow;
        });

        customerCart.setRowFactory((arg0) -> {
            TableRow<CartItem> selectedRow = new TableRow<>();
            selectedRow.setOnMouseClicked((event) -> {
                removeFromCartBtn.setDisable(false);
                moveToCartBtn.setDisable(true);
                amountField.setDisable(false);
            });
            return selectedRow;
        });

        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        customerCart.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

}


