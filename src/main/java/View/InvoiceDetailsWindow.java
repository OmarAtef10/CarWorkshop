package View;

import Context.Context;
import Controller.WindowLoader;
import Model.Invoice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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
    }

    public Stage show(Invoice invoice){
        stage = new Stage();
        stage.setScene(new Scene(this));
        stage.getScene().getStylesheets().addAll(Context.getContext().getCurrentTheme());
        stage.show();
        setData(invoice);
        return stage;
    }

    private void setData(Invoice invoice){
        
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
    private TableView<?> productsTable;

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

    }

}
