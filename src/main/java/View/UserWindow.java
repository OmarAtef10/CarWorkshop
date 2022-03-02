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

    public UserWindow(){
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

    public void setUser(User user){
        this.controller.setUser(user);
    }

    public User getUser(){
        return controller.getUser();
    }
}
