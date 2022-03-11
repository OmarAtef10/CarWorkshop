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
    Parent root;

    public ProductWindow(){
        loader = WindowLoader.getLoader(FXML_NAME);
        try {
            root = loader.load();
            controller = loader.getController();
            root.getStylesheets().addAll(Context.getContext().getCurrentTheme());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setProduct(Product product){
        controller.setProduct(product);
    }

    public void setEditMode(boolean mode){
        controller.setEditMode(mode);
    }

    public Stage view(){
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        return stage;
    }

    public Product getProduct(){
        return controller.getProduct();
    }
}
