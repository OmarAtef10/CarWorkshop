package View;

import Context.Context;
import Controller.CustomerDao;
import Controller.InvoiceDao;
import Controller.ProductDao;
import Controller.ProductInvoiceManagerUtil;
import Controller.UserManager;
import Controller.WindowLoader;
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

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


public class NewPurchase extends AnchorPane{

    public class CartItem {
        private String name;
        private int units;
        private double price;
        public CartItem(String name, int units, double price) {
            this.name = name;
            this.units = units;
            this.price = price;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getUnits() {
            return units;
        }
        public void setUnits(int units) {
            this.units = units;
        }
        public double getPrice() {
            return price;
        }
        public void setPrice(double price) {
            this.price = price;
        }
    }
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
            product.setUnits(product.getUnits() - amount);
            inventoryTable.refresh();
            if(product.getUnits() <= 0)
                inventoryTable.getItems().remove(product);
            amountField.clear();

            CartItem item = new CartItem(product.getProductName(), amount, product.getPricePerUnit());
            customerCart.getItems().add(item);
            cart.addProduct(product, amount);
        }

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
        alert.getDialogPane().getScene().getStylesheets().addAll(Context.getContext().getCurrentTheme());
        alert.setTitle("Confirmation Dialog");
        //TODO show invoice details
        // alert.setGraphic(new ImageView(new Image("D:\\MyProjects\\CarWorkshop\\src\\main\\resources\\cena.gif")));
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

        initTables();
        initData();
        moveToCartBtn.setDisable(true);
        removeFromCartBtn.setDisable(true);
        amountField.setDisable(true);
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


