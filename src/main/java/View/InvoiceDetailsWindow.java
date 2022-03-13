package View;

import java.util.Optional;

import Context.Context;
import Controller.WindowLoader;
import Model.Invoice;
import Model.InvoiceDetail;
import Service.PDF_Invoice;
import Service.PrinterHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.Printer;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
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
    private InvoiceDetail details;

    public InvoiceDetailsWindow() {
        loader = WindowLoader.getLoader(FXML_NAME);
        details = new InvoiceDetail();
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
        details = new InvoiceDetail(invoice);
        
        carLbl.setText(details.getCustomerCarModel());
        customerNameLbl.setText(details.getCustomerName());
        customerPhoneLbl.setText(details.getCustomerPhone());

        dateLbl.setText(details.getInvoiceDate());
        totalLbl.setText(String.valueOf(details.getInvoiceTotal()));
        invoiceLbl.setText(details.getInvoiceId());
        usernameLbl.setText(details.getCashierName());


        productsTable.getItems().addAll(details.getItems());
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

        ChoiceDialog<Printer> printerPrompt = new ChoiceDialog<Printer>(Printer.getDefaultPrinter() ,Printer.getAllPrinters());
        printerPrompt.setTitle("Print Invoice");
        printerPrompt.setHeaderText(null);
        printerPrompt.setGraphic(null);
        printerPrompt.setContentText("Choose printer: ");
        printerPrompt.getDialogPane().getStylesheets().addAll(Context.getContext().getCurrentTheme());

        Optional<Printer> chosenPrinter = printerPrompt.showAndWait();
        chosenPrinter.ifPresent(printer -> {
            PDF_Invoice pdf_invoice = new PDF_Invoice(details);
            String invoice_path = pdf_invoice.getInvoicePDF();
            //TODO: PRINT HERE
            PrinterHelper printerHelper = new PrinterHelper(invoice_path,printer.getName());
            printerHelper.start();
        });
        closeBtnPressed(new ActionEvent());
    }

    public InvoiceDetail getDetails() {
        return details;
    }

    public void setDetails(InvoiceDetail details) {
        this.details = details;
    }

}


