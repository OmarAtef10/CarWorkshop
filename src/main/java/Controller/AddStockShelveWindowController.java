package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddStockShelveWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private VBox root;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> actionComboBox;

    @FXML
    private TextField productIdField;

    @FXML
    private Button searchBtn;

    //add stock fields
    private TextField unitsField = new TextField();
    private Label unitsLabel = new Label("Units");
    
    //shelve fields
    private TextField shelfField = new TextField();
    private Label shelfLabel = new Label("Shelf Number");

    @FXML
    void searchBtnPressed(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert actionComboBox != null : "fx:id=\"actionComboBox\" was not injected: check your FXML file 'AddStockShelveWindow.fxml'.";
        assert productIdField != null : "fx:id=\"productIdField\" was not injected: check your FXML file 'AddStockShelveWindow.fxml'.";
        assert searchBtn != null : "fx:id=\"searchBtn\" was not injected: check your FXML file 'AddStockShelveWindow.fxml'.";

        actionComboBox.getItems().addAll("Shelve Product", "Add Stock");
        actionComboBox.setValue(actionComboBox.getItems().get(0));

        Insets insets = new Insets(10, 10, 10, 10);
        HBox addStockbox = new HBox(unitsLabel, unitsField);
        HBox.setMargin(unitsField, insets);
        HBox.setMargin(unitsLabel, insets);
        addStockbox.setAlignment(Pos.CENTER_LEFT);

        HBox shelfHbox = new HBox(shelfLabel, shelfField, unitsLabel, unitsField);
        HBox.setMargin(unitsField, insets);
        HBox.setMargin(unitsLabel, insets);
        HBox.setMargin(shelfLabel, insets);
        HBox.setMargin(shelfField, insets);
        shelfHbox.setAlignment(Pos.CENTER_LEFT);        
        //TODO: fixme ffs
        root.getChildren().add(shelfHbox);
        addStockbox.setVisible(true);
        actionComboBox.setOnAction((arg0) -> {
            String current = actionComboBox.getSelectionModel().getSelectedItem();
            if(current.equals(actionComboBox.getItems().get(0))){
                // root.getChildren().remove(addStockbox);
                root.getChildren().set(1, shelfHbox);
            }
            else{
                // root.getChildren().remove(shelfHbox);
                root.getChildren().set(1, addStockbox);
            }
        });
    }

}
