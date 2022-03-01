package View;

import java.io.IOException;

import Context.Context;
import Controller.ProductWindowController;
import Controller.WindowLoader;
import Model.Product;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProductWindow {
    public final static String FXML_NAME = ProductWindow.class.getSimpleName() + ".fxml";
    private final FXMLLoader loader;
    private ProductWindowController controller;

    public ProductWindow(){
        loader = WindowLoader.getLoader(FXML_NAME);
    }

    public void setProduct(Product product){
        controller.setProduct(product);
    }

    public Stage view(){
        Parent root;
        Stage stage = new Stage();
        try {
            root = loader.load();
            controller = loader.getController();
            root.getStylesheets().addAll(Context.getContext().getCurrentTheme());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return stage;
    }
}
