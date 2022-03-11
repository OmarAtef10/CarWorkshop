package Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.ResourceBundle;

import Context.Context;
import Model.Oil;
import Model.Product;
import Model.ServicePart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

public class ProductWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelBtn;

    @FXML
    private DatePicker expiryField;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField marketPriceField;

    @FXML
    private TextField milageField;

    @FXML
    private TextField nameField;

    @FXML
    private Button saveBtn;

    @FXML
    private ComboBox<String> typeCombobox;

    @FXML
    private TextField unitPriceField;

    @FXML
    private TextField unitsField;

    @FXML
    private TextField vendorField;

    @FXML
    private TextField viscField;

    private Product product;
    private boolean edit_mode = false;
    private ProductDao productDao = new ProductDao();

    @FXML
    void cancelBtnPressed(ActionEvent event) {
        ((Stage) cancelBtn.getScene().getWindow()).close();
    }

    public void setProduct(Product product) {
        this.product = product;
        if (product instanceof Oil) {
            typeCombobox.setValue("Oil");
        } else {
            typeCombobox.setValue("Service Part");
        }
        nameField.setText(product.getProductName());
        unitsField.setText(String.valueOf(product.getUnits()));
        unitPriceField.setText(String.valueOf(product.getPricePerUnit()));
        vendorField.setText(String.valueOf(product.getVendor()));
        marketPriceField.setText(String.valueOf(product.getMarketPrice()));


        if (product instanceof Oil) {
            milageField.setText(String.valueOf(((Oil) product).getMileage()));
            viscField.setText(((Oil) product).getViscosity());
            expiryField.setValue(LocalDate.parse(((Oil) product).getExpiryDate()));
        }
    }

    public Product getProduct() {
        return this.product;
    }

    public void setEditMode(boolean mode) {
        this.edit_mode = mode;
    }

    @FXML
    void saveBtnPressed(ActionEvent event) {


        boolean success = false;

        if (!edit_mode) {
            createProduct();
            cancelBtnPressed(new ActionEvent());
            return;
        }

        if (typeCombobox.getValue().equals("Oil")) {
            if (!vendorField.getText().equals("") || unitsField.getText().equals("")
                    || !unitPriceField.getText().equals("")
                    || !marketPriceField.getText().equals("") || viscField.getText().equals("")
                    || !milageField.getText().equals("")
                    || expiryField.getValue() != null) {


                this.product.setVendor(vendorField.getText());
                this.product.setUnits(Integer.parseInt(unitsField.getText()));
                this.product.setPricePerUnit(Double.parseDouble(unitPriceField.getText()));
                this.product.setMarketPrice(Double.parseDouble(marketPriceField.getText()));
                ((Oil) this.product).setViscosity(viscField.getText());
                ((Oil) this.product).setMileage(Integer.parseInt(milageField.getText()));
                ((Oil) this.product).setExpiryDate(expiryField.getValue().toString());
                productDao.updateProduct(product);
                success = true;
                cancelBtnPressed(new ActionEvent());
            }

        } else if (typeCombobox.getValue().equals("Service Part")) {
            if (!vendorField.getText().equals("") || !unitsField.getText().equals("")
                    || !unitPriceField.getText().equals("") || !marketPriceField.getText().equals("")
                    || !nameField.getText().equals("")) {


                this.product.setProductName(nameField.getText());
                this.product.setVendor(vendorField.getText());
                this.product.setUnits(Integer.parseInt(unitsField.getText()));
                this.product.setPricePerUnit(Double.parseDouble(unitPriceField.getText()));
                this.product.setMarketPrice(Double.parseDouble(marketPriceField.getText()));
                productDao.updateProduct(product);
                success = true;
                cancelBtnPressed(new ActionEvent());
            }
        }
        if (!success) {
            Alert alert = new Alert(AlertType.ERROR, "WRONG DATA", ButtonType.OK);
            alert.getDialogPane().getScene().getStylesheets().addAll(Context.getContext().getCurrentTheme());
            alert.show();
        }
    }

    private void createProduct() {
        if (typeCombobox.getValue().equals("Oil")) {
            if (!vendorField.getText().equals("") || unitsField.getText().equals("")
                    || !unitPriceField.getText().equals("")
                    || !marketPriceField.getText().equals("") || viscField.getText().equals("")
                    || !milageField.getText().equals("")
                    || expiryField.getValue() != null) {

                this.product = new Oil(
                       vendorField.getText(),
                        Integer.parseInt(unitsField.getText()),
                        Double.parseDouble(unitPriceField.getText()),
                        Double.parseDouble(marketPriceField.getText()),
                        viscField.getText(),
                        Integer.parseInt(milageField.getText()),
                        expiryField.getValue().toString()
                );
                productDao.addProduct(product);
            }
        } else if (typeCombobox.getValue().equals("Service Part")) {
            if (!vendorField.getText().equals("") || !unitsField.getText().equals("")
                    || !unitPriceField.getText().equals("") || !marketPriceField.getText().equals("")
                    || !nameField.getText().equals("")) {

                this.product = new ServicePart(
                        nameField.getText(),
                        vendorField.getText(),
                        Integer.parseInt(unitsField.getText()),
                        Double.parseDouble(unitPriceField.getText()),
                        Double.parseDouble(marketPriceField.getText())
                );
                productDao.addProduct(product);
            }
        }
    }

    @FXML
    void initialize() {
        assert cancelBtn != null : "fx:id=\"cancelBtn\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert expiryField != null
                : "fx:id=\"expiryField\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert gridPane != null : "fx:id=\"gridPane\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert marketPriceField != null
                : "fx:id=\"marketPriceField\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert milageField != null
                : "fx:id=\"milageField\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert typeCombobox != null
                : "fx:id=\"typeCombobox\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert unitPriceField != null
                : "fx:id=\"unitPriceField\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert unitsField != null : "fx:id=\"unitsField\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert vendorField != null
                : "fx:id=\"vendorField\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert viscField != null : "fx:id=\"viscField\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        typeCombobox.getItems().addAll("Oil", "Service Part");


        typeCombobox.setOnAction((arg0) -> {
            String selected = typeCombobox.getSelectionModel().getSelectedItem();
            if (selected.equals("Oil")) {
                expiryField.setDisable(false);
                milageField.setDisable(false);
                viscField.setDisable(false);
                nameField.setDisable(true);
            } else {
                expiryField.setDisable(true);
                milageField.setDisable(true);
                viscField.setDisable(true);
                nameField.setDisable(false);
            }
        });
        typeCombobox.setValue("Oil");
        nameField.setDisable(true);

    }

}
