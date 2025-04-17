package com.example.demo7;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class userWindowController {
    @FXML
    private Label status;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    //getters
    @FXML
    public String getUsernameField() {
        return usernameField.getText();
    }

    public void loginClick() throws IOException {
        String text = "";
        if(usernameField.getText() == text || passwordField.getText() == text){
            status.setText("username or password can't be empty!...");
        }else {
            status.setText("all good!....");
            // transform into next scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            String name = getUsernameField();
            HelloController obj = loader.getController();
            obj.displayText(name);
        }



    }

}
