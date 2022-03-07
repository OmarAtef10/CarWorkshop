package View;

import java.io.File;
import java.io.IOException;

import Context.Context;
import Controller.MainWindowController;
import Controller.UserManager;
import Controller.WindowLoader;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// public class MainWindow extends Application{
//     public static final String FXML_NAME = MainWindow.class.getSimpleName() + ".fxml";
//     private FXMLLoader loader = null;

//     public void view() {
//         launch();
//     }

//     @Override
//     public void start(Stage arg0) throws Exception {
//         loader = WindowLoader.getLoader(FXML_NAME);

//         Parent root = loader.load();
//         root.getStylesheets().addAll(Context.getContext().getCurrentTheme());
//         Scene scene = new Scene(root);
//         arg0.setScene(scene);
//         arg0.show();
//     }
// }

public class MainWindow {
    public static final String FXML_NAME = MainWindow.class.getSimpleName() + ".fxml";
    private FXMLLoader loader = null;
    private MainWindowController controller;
    
    public MainWindow(){
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
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                UserManager.getInstance().logout();
            }
        });
        return stage;
    }

}
