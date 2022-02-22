package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

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

    }

    @FXML
    void saveBtnPressed(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert cancelBtn != null : "fx:id=\"cancelBtn\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert expiryField != null : "fx:id=\"expiryField\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert gridPane != null : "fx:id=\"gridPane\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert marketPriceField != null : "fx:id=\"marketPriceField\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert milageField != null : "fx:id=\"milageField\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert typeCombobox != null : "fx:id=\"typeCombobox\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert unitPriceField != null : "fx:id=\"unitPriceField\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert unitsField != null : "fx:id=\"unitsField\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert vendorField != null : "fx:id=\"vendorField\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        assert viscField != null : "fx:id=\"viscField\" was not injected: check your FXML file 'ProductWindow.fxml'.";
        typeCombobox.getItems().addAll("Oil", "Service Part");

        typeCombobox.setOnAction((arg0) -> {
            String selected = typeCombobox.getSelectionModel().getSelectedItem();
            if(selected.equals("Oil")){
                expiryField.setDisable(false);
                milageField.setDisable(false);
                viscField.setDisable(false);
            }
            else{
                expiryField.setDisable(true);
                milageField.setDisable(true);
                viscField.setDisable(true);
            }
        });
    }

}
