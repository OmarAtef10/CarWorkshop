package View;

import java.io.File;

import Context.Context;
import Controller.WindowLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application{
    public static final String FXML_NAME = MainWindow.class.getSimpleName() + ".fxml";
    private FXMLLoader loader = null;

    public void view() {
        launch();
    }

    @Override
    public void start(Stage arg0) throws Exception {
        loader = WindowLoader.getLoader(FXML_NAME);
        
        Parent root = loader.load();
        root.getStylesheets().addAll(Context.getContext().getCurrentTheme());
        Scene scene = new Scene(root);
        arg0.setScene(scene);
        arg0.show();
        
    }
    
}
