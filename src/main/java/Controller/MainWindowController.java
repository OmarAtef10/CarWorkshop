package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import View.ProductWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createProdcutBtn;

    @FXML
    void createBtnPressed(ActionEvent event) {
        ProductWindow window = new ProductWindow();
        window.view();
    }

    @FXML
    void initialize() {
        assert createProdcutBtn != null : "fx:id=\"createProdcutBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";

    }

}
