package Controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;

import Model.Product;
import Model.ProductHistoryItem;
import View.AddStockShelveWindow;
import View.ProductWindow;
import View.UserWindow;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.text.Text;

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
	private TableView<ProductHistoryItem> historyTable;

	@FXML
	private TableView<?> invoicesTable;

	@FXML
	private Button newPurchaseBtn;

	@FXML
	private ListView<?> productInfoTable;

	@FXML
	private TableView<Product> productsTable;

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
	private TableView<HashMap.Entry<String, Integer>> shelvesTable;

	@FXML
	private Button updateUserBtn;

	@FXML
	private ListView<?> userInfo;

	@FXML
	private TableView<?> usersTable;

	@FXML
	void addStockBtnPressed(ActionEvent event) {
		AddStockShelveWindow window = new AddStockShelveWindow();
		window.view(); 
	}

	@FXML
	void createBtnPressed(ActionEvent event) {
		ProductWindow productWindow = new ProductWindow();
		productWindow.view();
	}

	@FXML
	void newPurchaseBtnPressed(ActionEvent event) {

	}

	@FXML
	void registerBtnPressed(ActionEvent event) {

		UserWindow userWindow = new UserWindow();
		userWindow.view();
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
		assert createProdcutBtn != null
				: "fx:id=\"createProdcutBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert historyTable != null
				: "fx:id=\"historyTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert invoicesTable != null
				: "fx:id=\"invoicesTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert newPurchaseBtn != null
				: "fx:id=\"newPurchaseBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert productInfoTable != null
				: "fx:id=\"productInfoTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert productsTable != null
				: "fx:id=\"productsTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert registerBtn != null : "fx:id=\"registerBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert removeUserBtn != null
				: "fx:id=\"removeUserBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert reportTable != null : "fx:id=\"reportTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert searchBtn != null : "fx:id=\"searchBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert searchUserBtn != null
				: "fx:id=\"searchUserBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert shelvesTable != null
				: "fx:id=\"shelvesTable\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert updateUserBtn != null
				: "fx:id=\"updateUserBtn\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert userInfo != null : "fx:id=\"userInfo\" was not injected: check your FXML file 'MainWindow.fxml'.";
		assert usersTable != null : "fx:id=\"usersTable\" was not injected: check your FXML file 'MainWindow.fxml'.";

		initializeTables();
		initData();

		historyTable.setPlaceholder(new Text("Select a product"));
		shelvesTable.setPlaceholder(new Text("Select a product"));
	}

	void initializeTables() {
		// init products table
		TableColumn<Product, String> idColumn = new TableColumn<Product, String>("id");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));

		TableColumn<Product, String> productNameColumn = new TableColumn<Product, String>("Product Name");
		productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

		TableColumn<Product, Integer> unitsColumn = new TableColumn<Product, Integer>("Units");
		unitsColumn.setCellValueFactory(new PropertyValueFactory<>("units"));

		TableColumn<Product, Double> priceColumn = new TableColumn<Product, Double>("Unit Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));

		TableColumn<Product, Double> marketPriceColumn = new TableColumn<Product, Double>("Market Price");
		marketPriceColumn.setCellValueFactory(new PropertyValueFactory<>("marketPrice"));

		productsTable.getColumns().addAll(idColumn, productNameColumn, unitsColumn, priceColumn, marketPriceColumn);
		productsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		productsTable.setRowFactory((arg0) -> {
			TableRow<Product> selectedRow = new TableRow<>();
			selectedRow.setOnMouseClicked((event) -> {
				Product product = selectedRow.getItem();
				if (event.getClickCount() == 1 && !selectedRow.isEmpty()) {
					updateRelatedProductInfo(product);
				}
			});
			return selectedRow;
		});

		// init history table

		TableColumn<ProductHistoryItem, String> actionCol = new TableColumn<>("Action");
		actionCol.setCellValueFactory(new PropertyValueFactory<>("action"));

		TableColumn<ProductHistoryItem, Integer> unitsCol = new TableColumn<>("Units");
		unitsCol.setCellValueFactory(new PropertyValueFactory<>("units"));

		TableColumn<ProductHistoryItem, String> timeStampCol = new TableColumn<>("TimeStamp");
		timeStampCol.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

		historyTable.getColumns().addAll(actionCol, timeStampCol, unitsCol);
		historyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<HashMap.Entry<String, Integer>, String> shelfCol = new TableColumn<>("Shelf Number");
		shelfCol.setCellValueFactory((arg0) -> {
			return new SimpleStringProperty(arg0.getValue().getKey());
		});


		TableColumn<HashMap.Entry<String, Integer>, String> shelfUnitCol = new TableColumn<>("Units");
		shelfUnitCol.setCellValueFactory((arg0) -> {
			return new SimpleStringProperty(arg0.getValue().getValue().toString());
		});

		shelvesTable.getColumns().addAll(shelfCol, shelfUnitCol);
		shelvesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	private void updateRelatedProductInfo(Product product) {
        //history table
        historyTable.getItems().clear();
        historyTable.getItems().addAll(product.getProductHistory());
        //locations table
        shelvesTable.getItems().clear();
		shelvesTable.getItems().addAll(product.getLocations().entrySet());
    }

	void initData() {
		ProductDao dao = new ProductDao();
		// TODO: remove hardcode
		productsTable.getItems().addAll(dao.getAll());
	}

}
