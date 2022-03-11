package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Context.Context;
import View.MainWindow;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    void loginBtnPressed(ActionEvent event) {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            UserManager.getInstance().login(username, password);

            if(UserManager.userLoggedIn()){
                MainWindow window = new MainWindow();
                Stage stage = ((Stage) cancelBtn.getScene().getWindow());
                window.view().setOnHidden((arg0) -> {
                    UserManager.getInstance().logout();

                    if(window.isUserPressedLogout()){
                        usernameField.clear();
                        passwordField.clear();
                        stage.show();
                    
                    }
                    else
                        Platform.exit();
                });
                stage.hide();
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.getDialogPane().getStylesheets().addAll(Context.getContext().getCurrentTheme());
            alert.show();
            e.printStackTrace();
        }
    }

    @FXML
    void cancelBtnPressed(){
        Platform.exit();
    }

    @FXML
    void initialize() {
        assert cancelBtn != null : "fx:id=\"cancelBtn\" was not injected: check your FXML file 'LoginWindow.fxml'.";
        assert loginBtn != null : "fx:id=\"loginBtn\" was not injected: check your FXML file 'LoginWindow.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'LoginWindow.fxml'.";
        assert usernameField != null : "fx:id=\"usernameField\" was not injected: check your FXML file 'LoginWindow.fxml'.";

    }

}
