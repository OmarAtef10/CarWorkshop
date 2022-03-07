package View;

import Context.Context;
import Controller.NewPurchaseController;
import Controller.WindowLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NewPurchase {
    public static String FXML_NAME = NewPurchase.class.getSimpleName() + ".fxml";
    private final FXMLLoader loader;
    private NewPurchaseController controller;

    public NewPurchase() {
        loader = WindowLoader.getLoader(FXML_NAME);
        try {
            controller = loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Stage view() {
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
