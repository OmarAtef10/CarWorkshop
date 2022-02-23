package View;

import java.io.IOException;

import Context.Context;
import Controller.LoginWindowController;
import Controller.WindowLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// public class LoginWindow {
//     public final static String FXML_NAME = LoginWindow.class.getSimpleName() + ".fxml";
//     private final FXMLLoader loader;
//     private LoginWindowController controller;

//     public LoginWindow(){
//         loader = WindowLoader.getLoader(FXML_NAME);
//         try {
//             controller = loader.getController();
//         } catch (Exception e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//         }
//     }

//     public Stage view(){
//         Parent root;
//         Stage stage = new Stage();
//         try {
//             root = loader.load();
//             root.getStylesheets().addAll(Context.getContext().getCurrentTheme());
//             stage.setScene(new Scene(root));
//             stage.show();
//         } catch (IOException e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//         }

//         return stage;
//     }
// }

public class LoginWindow extends Application{
    public static final String FXML_NAME = LoginWindow.class.getSimpleName() + ".fxml";
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
