package View;

import java.io.IOException;

import Context.Context;
import Controller.UserWindowController;
import Controller.WindowLoader;
import Model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserWindow {
    public final static String FXML_NAME = UserWindow.class.getSimpleName() + ".fxml";
    private final FXMLLoader loader;
    private UserWindowController controller;
    private Parent root;

    public UserWindow(){
        loader = WindowLoader.getLoader(FXML_NAME);
        try {
            root = loader.load();
            controller = loader.getController();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Stage view(){
        Stage stage = new Stage();
        root.getStylesheets().addAll(Context.getContext().getCurrentTheme());
        stage.setScene(new Scene(root));
        stage.show();

        return stage;
    }

    public void setUser(User user){
        this.controller.setUser(user);
    }

    public User getUser(){
        return controller.getUser();
    }
}
