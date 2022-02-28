package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Context.Context;
import Model.Oil;
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

    @FXML
    void cancelBtnPressed(ActionEvent event) {
        ((Stage) cancelBtn.getScene().getWindow()).close();
    }

    @FXML
    void saveBtnPressed(ActionEvent event) {
        ProductDao productDao = new ProductDao();
        boolean success = false;
        if (typeCombobox.getValue().equals("Oil")) {

            if (!vendorField.getText().equals("") || unitsField.getText().equals("")
                    || !unitPriceField.getText().equals("")
                    || !marketPriceField.getText().equals("") || viscField.getText().equals("")
                    || !milageField.getText().equals("")
                    || expiryField.getValue() != null) {

                Oil oil = new Oil(vendorField.getText(),
                        Integer.parseInt(unitsField.getText()),
                        Double.parseDouble(unitPriceField.getText()),
                        Double.parseDouble(marketPriceField.getText()),
                        viscField.getText(),
                        Integer.parseInt(milageField.getText()),
                        expiryField.getValue().toString());
                productDao.addProduct(oil);
                success = true;
                cancelBtnPressed(new ActionEvent());
            }

        } else if (typeCombobox.getValue().equals("Service Part")) {

            System.out.println(vendorField.getText() + "  " + unitPriceField.getText() + " " + " "
                    + unitsField.getText() + " " + marketPriceField.getText() + " " + nameField.getText());
            if (!vendorField.getText().equals("") || !unitsField.getText().equals("")
                    || !unitPriceField.getText().equals("")
                    || !marketPriceField.getText().equals("") || !nameField.getText().equals("")) {

                ServicePart servicePart = new ServicePart(nameField.getText(),
                        vendorField.getText(),
                        Integer.parseInt(unitsField.getText()),
                        Double.parseDouble(unitPriceField.getText()),
                        Double.parseDouble(marketPriceField.getText()));
                productDao.addProduct(servicePart);
                success = true;
                cancelBtnPressed(new ActionEvent());
            }
        }
        if(!success){
            Alert alert = new Alert(AlertType.ERROR, "WRONG DATA", ButtonType.OK);
            alert.getDialogPane().getScene().getStylesheets().addAll(Context.getContext().getCurrentTheme());
            alert.show();
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
    }

}
