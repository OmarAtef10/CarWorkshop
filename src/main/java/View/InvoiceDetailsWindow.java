package View;

import java.util.HashMap;

import Context.Context;
import Controller.CustomerDao;
import Controller.ProductDao;
import Controller.WindowLoader;
import CustomizedUtilities.TimeUtility;
import Model.Customer;
import Model.Invoice;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InvoiceDetailsWindow extends VBox{

    public static final String FXML_NAME = InvoiceDetailsWindow.class.getSimpleName() + ".fxml";
    private FXMLLoader loader;
    private Stage stage;
    private Invoice invoice;

    public InvoiceDetailsWindow() {
        loader = WindowLoader.getLoader(FXML_NAME);
        try {
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage = new Stage();
        stage.setScene(new Scene(this));
        stage.getScene().getStylesheets().addAll(Context.getContext().getCurrentTheme());
    }

    public Stage show(Invoice invoice){
        clearData();
        setData(invoice);
        stage.show();
        return stage;
    }

    private void clearData(){
        carLbl.setText("");
        customerNameLbl.setText("");
        customerPhoneLbl.setText("");

        dateLbl.setText("");
        totalLbl.setText("");
        invoiceLbl.setText("");
        usernameLbl.setText("");
        productsTable.getItems().clear();
    }

    private void setData(Invoice invoice){
        CustomerDao dao = new CustomerDao();
        ProductDao dao2 = new ProductDao();
        Customer customer = dao.getCustomer(invoice.getCustomerId());
        
        carLbl.setText(customer.getCarModel());
        customerNameLbl.setText(customer.getName());
        customerPhoneLbl.setText(customer.getMobileNumber());

        dateLbl.setText(TimeUtility.getReformedDate(invoice.getDate()));
        totalLbl.setText(String.valueOf(invoice.getTotalPaid()));
        invoiceLbl.setText(invoice.getInvoiceId());
        usernameLbl.setText(invoice.getUserName());


        HashMap<String, Integer> products =  invoice.getCart().getProducts();
        for(String id: products.keySet()){
            Product product = dao2.getOrNull(id);
            CartItem item = new CartItem(product.getProductName(), products.get(id), product.getPricePerUnit());
            productsTable.getItems().add(item);
        }
    }

    @FXML
    void initialize(){
        TableColumn<CartItem, String> cartNameCol = new TableColumn<>("Product");
        cartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    
        TableColumn<CartItem, Integer> cartUnitsCol = new TableColumn<>("Units");
        cartUnitsCol.setCellValueFactory(new PropertyValueFactory<>("units"));

        TableColumn<CartItem, Double> cartPriceCol = new TableColumn<>("Price");
        cartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.getColumns().addAll(cartNameCol, cartUnitsCol, cartPriceCol);
        productsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    @FXML
    private Label carLbl;
    

    @FXML
    private Label customerNameLbl;

    @FXML
    private Label customerPhoneLbl;

    @FXML
    private Label dateLbl;

    @FXML
    private Label invoiceLbl;

    @FXML
    private TableView<CartItem> productsTable;

    @FXML
    private Label totalLbl;

    @FXML
    private Label usernameLbl;

    @FXML
    void closeBtnPressed(ActionEvent event) {
        stage.close();
    }

    @FXML
    void printBtnPressed(ActionEvent event) {
        //TODO: PRINT HERE
    }

}
