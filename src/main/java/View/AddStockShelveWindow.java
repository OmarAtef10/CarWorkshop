package View;

import java.io.IOException;

import Context.Context;
import Controller.AddStockShelveWindowController;
import Controller.WindowLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * AddStockShelveWindow
 */
public class AddStockShelveWindow {

    public static final String FXML_NAME = AddStockShelveWindow.class.getSimpleName() + ".fxml";
    private FXMLLoader loader = null;
    private AddStockShelveWindowController controller;
    
    public AddStockShelveWindow(){
        loader = WindowLoader.getLoader(FXML_NAME);
        try {
            controller = loader.getController();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Stage view(){
        Parent root;
        Stage stage = new Stage();
        try {
            root = loader.load();
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