package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Role;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import Context.Context;

public class UserWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelBtn;


    @FXML
    private GridPane gridPane;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passwordField;


    @FXML
    private ComboBox<String> typeCombobox;
    private User user = null;

    public void setUser(User user){
        this.user = user;
        userNameField.setDisable(true);
        typeCombobox.setValue(user.getRole().toString());
        userNameField.setText(user.getUserName());
        passwordField.setText(user.getPassword());
    }

    public User getUser(){
        return this.user;
    }

    @FXML
    void cancelBtnPressed(ActionEvent event) {
        ((Stage) cancelBtn.getScene().getWindow()).close();
    }

    @FXML
    void saveBtnPressed(ActionEvent event) {
        UserDao userDao = new UserDao();

        if(this.user == null){
            boolean success = false;
            if (typeCombobox.getValue().equals("Admin")) {
                if (!userNameField.getText().equals("") || !passwordField.getText().equals("")) {
                    User user = UserManager.getInstance().register(userNameField.getText(),
                            passwordField.getText(), Role.ADMIN);
                    userDao.addUser(user);
                    success = true;
                    this.user = user;
                    cancelBtnPressed(new ActionEvent());
                }
            } else if (typeCombobox.getValue().equals("Employee")) {
                if (!userNameField.getText().equals("") || !passwordField.getText().equals("")) {
                    User user = UserManager.getInstance().register(userNameField.getText(),
                            passwordField.getText(), Role.EMPLOYEE);
                    userDao.addUser(user);
                    success = true;
                    this.user = user;
                    cancelBtnPressed(new ActionEvent());
                }
                if (!success) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "WRONG DATA", ButtonType.OK);
                    alert.getDialogPane().getScene().getStylesheets().addAll(Context.getContext().getCurrentTheme());
                    alert.show();
                }
            }
        }else{
            this.user.setRole( Role.valueOf(typeCombobox.getValue().toUpperCase()));
            this.user.setPassword(passwordField.getText());
            userDao.updateUser(this.user);

            cancelBtnPressed(new ActionEvent());
        }

    }

    @FXML
    void initialize() {
        assert cancelBtn != null : "fx:id=\"cancelBtn\" was not injected: check your FXML file 'UserWindow.fxml'.";
        assert userNameField != null : "fx:id=\"userNameField\" was not injected: check your FXML file 'UserWindow.fxml'.";
        assert gridPane != null : "fx:id=\"gridPane\" was not injected: check your FXML file 'UserWindow.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'UserWindow.fxml'.";
        assert typeCombobox != null : "fx:id=\"typeCombobox\" was not injected: check your FXML file 'UserWindow.fxml'.";
        typeCombobox.getItems().addAll("Admin", "Employee");
    }

}
