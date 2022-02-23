package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import View.LoginWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

public class MainWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addStockBtn;

    @FXML
    private Button createProdcutBtn;

    @FXML
    private TableView<?> historyTable;

    @FXML
    private TableView<?> invoicesTable;

    @FXML
    private Button newPurchaseBtn;

    @FXML
    private ListView<?> productInfoTable;

    @FXML
    private TableView<?> productsTable;

    @FXML
    private Button registerBtn;

    @FXML
    private Button removeUserBtn;

    @FXML
    private TableView<?> reportTable;

    @FXML
    private Button searchBtn;

    @FXML
    private Button searchUserBtn;

    @FXML
    private TableView<?> shelvesTable;

    @FXML
    private Button updateUserBtn;

    @FXML
    private ListView<?> userInfo;

    @FXML
    private TableView<?> usersTable;

    @FXML
    void addStockBtnPressed(ActionEvent event) {

    }

    @FXML
    void createBtnPressed(ActionEvent event) {

    }

    @FXML
    void newPurchaseBtnPressed(ActionEvent event) {

    }

    @FXML
    void registerBtnPressed(ActionEvent event) {

    }

    @FXML
    void removeUserBtnPressed(ActionEvent event) {

    }

    @FXML
    void searchBtnPressed(ActionEvent event) {

    }

    @FXML
    void searchUserBtnPressed(ActionEvent event) {

    }

    @FXML
    void updateUserBtnPressed(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert addStockBtn != null : "fx:id=\"addStockBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert createProdcutBtn != null : "fx:id=\"createProdcutBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert historyTable != null : "fx:id=\"historyTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert invoicesTable != null : "fx:id=\"invoicesTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert newPurchaseBtn != null : "fx:id=\"newPurchaseBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert productInfoTable != null : "fx:id=\"productInfoTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert productsTable != null : "fx:id=\"productsTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert registerBtn != null : "fx:id=\"registerBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert removeUserBtn != null : "fx:id=\"removeUserBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert reportTable != null : "fx:id=\"reportTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert searchBtn != null : "fx:id=\"searchBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert searchUserBtn != null : "fx:id=\"searchUserBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert shelvesTable != null : "fx:id=\"shelvesTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert updateUserBtn != null : "fx:id=\"updateUserBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert userInfo != null : "fx:id=\"userInfo\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert usersTable != null : "fx:id=\"usersTable\" was not injected: check your FXML file 'MainWindow.fxml'.";

        
    }

}
