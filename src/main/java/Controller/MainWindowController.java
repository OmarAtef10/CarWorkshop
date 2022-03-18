package Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import Context.Context;
import Model.*;
import Service.CSV_Handler;
import Service.ICSVService;
import View.*;
import View.ProductWindow;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

//TODO User data / shelf data/ stock updates need to be shown on UI tables after each update!
public class MainWindowController {

    private InvoiceDetailsWindow invoiceDetailsWindow = new InvoiceDetailsWindow();

    private boolean userPressedLogout = false;

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
    private TableView<Invoice> invoicesTable;

    @FXML
    private Button newPurchaseBtn;

    @FXML
    private ListView<String> productInfoTable;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private Button registerBtn;

    @FXML
    private Button removeUserBtn;

    @FXML
    private TableView<Report> reportTable;

    @FXML
    private Button searchBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button editProductBtn;

    @FXML
    private Button shelfBtn;

    @FXML
    private Button stockProductBtn;

    @FXML
    private Button searchUserBtn;

    @FXML
    private TableView<HashMap.Entry<String, Integer>> shelvesTable;

    @FXML
    private Button updateUserBtn;

    @FXML
    private ListView<?> userInfo;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private Tab usersTab;

    @FXML
    private Button removeBtn;

    @FXML
    private TextField invoiceIdField;

    @FXML
    private TextField soldByField;

    @FXML
    private DatePicker soldInDate;

    @FXML
    private Button exportDailyBtn;

    @FXML
    private Button exportMonthlyBtn;

    @FXML
    private TableView<Invoice> allInvoicesTable;

    @FXML
    void logoutBtnPressed(ActionEvent event) {
        userPressedLogout = true;
        ((Stage) logoutBtn.getScene().getWindow()).close();
    }

    public boolean isUserPressedLogout() {
        return userPressedLogout;
    }

    @FXML
    void removeBtnPressed(ActionEvent event) {
        ProductDao productDao = new ProductDao();
        Product product = productsTable.getSelectionModel().getSelectedItem();
        int prodInd = productsTable.getSelectionModel().getSelectedIndex();
        productDao.deleteProduct(product.getProductName(), product.getVendor());
        productsTable.getItems().remove(prodInd);
    }

    void update() {
        initData();
    }

    @FXML
    void createBtnPressed(ActionEvent event) {
        ProductWindow productWindow = new ProductWindow(false);
        productWindow.view().setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Product product = productWindow.getProduct();
                if (product != null)
                    productsTable.getItems().add(product);
                productsTable.refresh();
                productsTable.getSelectionModel().clearSelection();
            }
        });
        productWindow.setEditMode(false);
    }

    @FXML
    void editProductBtnPressed(ActionEvent event) {
        Product product = productsTable.getSelectionModel().getSelectedItem();
        int productInd = productsTable.getSelectionModel().getSelectedIndex();
        ProductWindow window = new ProductWindow(true);
        window.setProduct(product);
        window.view().setOnHidden((arg0) -> {
            Product newProduct = window.getProduct();
            productsTable.getItems().set(productInd, newProduct);
            productsTable.refresh();
            updateRelatedProductInfo(newProduct);
        });
        ;
    }

    @FXML
    void shelfBtnPressed(ActionEvent event) {
        ShelveWindow window = new ShelveWindow();
        window.show();
        Product product = productsTable.getSelectionModel().getSelectedItem();
        int productInd = productsTable.getSelectionModel().getSelectedIndex();
        window.setProduct(product);
        window.show().setOnHidden((arg0) -> {
            Product newProduct = window.getProduct();
            productsTable.getItems().set(productInd, newProduct);
            updateRelatedProductInfo(newProduct);
        });
    }

    @FXML
    void stockProductBtnPressed(ActionEvent event) {
        AddStockWindow window = new AddStockWindow();
        Product product = productsTable.getSelectionModel().getSelectedItem();
        int productInd = productsTable.getSelectionModel().getSelectedIndex();

        window.setProduct(product);
        window.show().setOnHidden((arg0) -> {
            Product newProduct = window.getProduct();
            productsTable.getItems().set(productInd, newProduct);
            updateRelatedProductInfo(newProduct);
        });
    }

    @FXML
    void newPurchaseBtnPressed(ActionEvent event) {
        NewPurchase purchaseWindow = new NewPurchase();
        purchaseWindow.show().setOnHidden((arg0) -> {
            update();
        });
    }

    @FXML
    void registerBtnPressed(ActionEvent event) {
        if (UserManager.getInstance().hasPermission(Action.ADD_USERS)) {
            UserWindow userWindow = new UserWindow();
            userWindow.view().setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    usersTable.getItems().add(userWindow.getUser());
                }
            });

        } else {
            registerBtn.setDisable(true);
        }
    }

    @FXML
    void removeUserBtnPressed(ActionEvent event) {
        User user = usersTable.getSelectionModel().getSelectedItem();
        if (!UserManager.getInstance().getCurrentUser().getUserName().equals(user.getUserName())) {
            UserManager.getInstance().deleteUser(user.getUserName());
            usersTable.getItems().remove(user);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You can't delete currently logged in user!", ButtonType.OK);
            alert.getDialogPane().getStylesheets().addAll(Context.getContext().getCurrentTheme());
            alert.show();
        }
    }

    //TODOS new Funcations
    @FXML
    void getDailyReport(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        chooser.getExtensionFilters().add(extFilter);
        chooser.setInitialFileName(LocalDate.now().toString());
        File selectedDir = chooser.showSaveDialog(exportMonthlyBtn.getScene().getWindow());
        if(selectedDir!=null){
            String path = selectedDir.getAbsolutePath().toString();
            ICSVService icsvService = new CSV_Handler(path);
            icsvService.getDailyReportCSV();
        }
    }

    @FXML
    void getMonthlyReport(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        chooser.getExtensionFilters().add(extFilter);
        chooser.setInitialFileName(LocalDate.now().toString());
        File selectedDir = chooser.showSaveDialog(exportMonthlyBtn.getScene().getWindow());
        if(selectedDir!=null){
            String path = selectedDir.getAbsolutePath().toString();
            ICSVService icsvService = new CSV_Handler(path);
            icsvService.getMonthlyReportCSV();
        }
    }

    @FXML
    void excelExportBtnPressed() {

    }

    @FXML
    void searchInvoicesBtnPressed(ActionEvent event) {
        InvoiceDao invoiceDao = new InvoiceDao();
        ArrayList<Invoice> invoices = new ArrayList<>();
        String id = invoiceIdField.getText();
        String date = "";
        if (soldInDate.getValue() != null) {
            date = soldInDate.getValue().toString();
        }
        String username = soldByField.getText();
        invoices = invoiceDao.searchBtn(id, date, username);
        allInvoicesTable.getItems().clear();
        allInvoicesTable.getItems().addAll(invoices);

        soldInDate.setValue(null);
        soldByField.clear();
        invoiceIdField.clear();
    }

    @FXML
    void revertInvoicesBtnPressed(ActionEvent event) {
        initData();
    }

    @FXML
    void searchBtnPressed(ActionEvent event) {
        SearchWindow window = new SearchWindow();
        window.show().setOnHidden((arg0) -> {
            productsTable.getItems().clear();
            productsTable.getItems().addAll(window.getSearchResults());
        });
    }

    @FXML
    void searchUserBtnPressed(ActionEvent event) {
        throw new UnsupportedOperationException("search user method isn't implemented");
    }

    @FXML
    void updateUserBtnPressed(ActionEvent event) {
        User user = usersTable.getSelectionModel().getSelectedItem();
        UserWindow userWindow = new UserWindow();
        userWindow.setUser(user);
        userWindow.view().setOnHidden((arg0) -> {
            update();
        });
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
        editProductBtn.setDisable(true);
        stockProductBtn.setDisable(true);
        shelfBtn.setDisable(true);
        removeBtn.setDisable(true);
        if (UserManager.getInstance().getCurrentUser().getRole() == Role.EMPLOYEE) {
            createProdcutBtn.setDisable(true);
        }

    }

    @FXML
    void revertBtnPressed(ActionEvent event) {
        editProductBtn.setDisable(true);
        stockProductBtn.setDisable(true);
        shelfBtn.setDisable(true);
        removeBtn.setDisable(true);
        initData();
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

        // UsersMenu

        TableColumn<User, String> nameColumn = new TableColumn<User, String>("UserName");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("UserName"));

        TableColumn<User, String> passwordColumn = new TableColumn<User, String>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("Password"));

        TableColumn<User, String> roleColumn = new TableColumn<User, String>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("Role"));

        usersTable.getColumns().addAll(nameColumn, passwordColumn, roleColumn);
        usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        usersTable.setRowFactory((arg0) -> {
            TableRow<User> selectedRow = new TableRow<>();
            selectedRow.setOnMouseClicked((event) -> {
                User user = selectedRow.getItem();
                if (event.getClickCount() == 1 && !selectedRow.isEmpty()) {
                    updateRelatedUserInfo(user);
                }
            });
            return selectedRow;
        });

        // Reports Table

        TableColumn<Report, String> reportIdColumn = new TableColumn<Report, String>("Report ID");
        reportIdColumn.setCellValueFactory(new PropertyValueFactory<>("reportId"));

        TableColumn<Report, String> reportDateColumn = new TableColumn<>("Report Date");
        reportDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        reportTable.getColumns().addAll(reportIdColumn, reportDateColumn);
        reportTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        reportTable.setRowFactory((arg0) -> {
            TableRow<Report> selectedRow = new TableRow<>();
            selectedRow.setOnMouseClicked((event) -> {
                Report report = selectedRow.getItem();
                if (event.getClickCount() == 1 && !selectedRow.isEmpty()) {
                    ReportInvoicesGetter(report);
                }
            });
            return selectedRow;
        });

        invoicesTable.setRowFactory((w) -> {
            TableRow<Invoice> selectedRow = new TableRow<>();
            selectedRow.setOnMouseClicked((click) -> {
                Invoice invoice = selectedRow.getItem();
                if (click.getClickCount() == 2 && !selectedRow.isEmpty()) {
                    invoiceDetailsWindow.show(invoice);
                }
            });
            return selectedRow;
        });

        // Invoices Table
        TableColumn<Invoice, String> invoiceIdCol = new TableColumn<Invoice, String>("Invoice ID");
        invoiceIdCol.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));

        TableColumn<Invoice, Double> totalCol = new TableColumn<Invoice, Double>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalPaid"));

        invoicesTable.getColumns().addAll(invoiceIdCol, totalCol);
        invoicesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Check buttons
        if (UserManager.getInstance().getCurrentUser().getRole() != Role.ADMIN) {
            usersTab.setDisable(true);
        }

        updateUserBtn.setDisable(true);
        removeUserBtn.setDisable(true);


        //Invoices menu
        TableColumn<Invoice, String> invoiceWindowIdCol = new TableColumn<Invoice, String>("Invoice ID");
        invoiceWindowIdCol.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));

        TableColumn<Invoice, String> invoiceUsername = new TableColumn<>("UserName");
        invoiceUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<Invoice, Double> totalWindowCol = new TableColumn<Invoice, Double>("Total");
        totalWindowCol.setCellValueFactory(new PropertyValueFactory<>("totalPaid"));

        TableColumn<Invoice, String> invoiceCustomerPhoneCol = new TableColumn<>("Customer Phone");
        invoiceCustomerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Invoice, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));


        allInvoicesTable.getColumns().addAll(invoiceWindowIdCol, invoiceUsername, totalWindowCol, invoiceCustomerPhoneCol, dateCol);
        allInvoicesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        allInvoicesTable.setRowFactory((w) -> {
            TableRow<Invoice> selectedRow = new TableRow<>();
            selectedRow.setOnMouseClicked((click) -> {
                Invoice invoice = selectedRow.getItem();
                if (click.getClickCount() == 2 && !selectedRow.isEmpty()) {
                    invoiceDetailsWindow.show(invoice);
                }
            });
            return selectedRow;
        });

        soldInDate.setEditable(false);


    }

    // TODO
    private void updateRelatedProductInfo(Product product) {

        // history table
        historyTable.getItems().clear();
        historyTable.getItems().addAll(product.getProductHistory());
        // locations table
        shelvesTable.getItems().clear();
        shelvesTable.getItems().addAll(product.getLocations().entrySet());

        productInfoTable.getItems().clear();
        productInfoTable.getItems().add("Name: " + product.getProductName());
        productInfoTable.getItems().add("Vendor: " + product.getVendor());
        productInfoTable.getItems().add("Units: " + product.getUnits());
        productInfoTable.getItems().add("Price Per Unit: " + product.getPricePerUnit());
        productInfoTable.getItems().add("Market Price: " + product.getMarketPrice());
        if (product instanceof Oil) {
            Oil oilProduct = (Oil) product;
            productInfoTable.getItems().add("Mileage: " + oilProduct.getViscosity());
            productInfoTable.getItems().add("Viscosity: " + oilProduct.getViscosity());
        }

        // buttons
        if (UserManager.getInstance().getCurrentUser().getRole().equals(Role.ADMIN)) {
            editProductBtn.setDisable(false);
            removeBtn.setDisable(false);
        }
        stockProductBtn.setDisable(false);
        shelfBtn.setDisable(false);

    }

    private void updateRelatedUserInfo(User user) { // TODO TODO TODO
        // reports table
        ReportDao reportDao = new ReportDao();
        reportTable.getItems().clear();
        reportTable.getItems().addAll(reportDao.getReportsByUsername(user.getUserName()));
        // reportTable.getItems().addAll(user.getSessionReport());
        // invoices table
        invoicesTable.getItems().clear();
        // invoicesTable.getItems().addAll(user.getLocations().entrySet());

        // reports table
        removeUserBtn.setDisable(false);
        updateUserBtn.setDisable(false);

    }

    private void ReportInvoicesGetter(Report report) {
        invoicesTable.getItems().clear();
        invoicesTable.getItems().addAll(report.getInvoices());

    }

    private void initInvoiceTab() {
        InvoiceDao invoiceDao = new InvoiceDao();

        allInvoicesTable.getItems().clear();
        allInvoicesTable.getItems().addAll(invoiceDao.getAll());
    }

    void initData() {
        Thread thread = new Thread(new Runnable() {
            private ArrayList<Product> products;
            private ArrayList<User> users;
            private ArrayList<Invoice> invoices;
            ProductDao dao = new ProductDao();
            UserDao userDao = new UserDao();
            InvoiceDao invoiceDao = new InvoiceDao();

            @Override
            public void run() {
                products = dao.getAll();
                users = userDao.getAll();
                invoices = invoiceDao.getAll();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        productsTable.getItems().clear();
                        usersTable.getItems().clear();

                        allInvoicesTable.getItems().clear();
                        allInvoicesTable.getItems().addAll(invoices);

                        productsTable.getItems().addAll(products);
                        usersTable.getItems().addAll(users);

                    }
                });
            }
        });
        thread.start();
    }

}
