package com.example.demo7;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private Label welcomeText;

    public void displayText(String name) {
        welcomeText.setText(name+", Welcome to JavaFx ");
    }

    @FXML
    public void onHelloButtonClick() {
        System.out.println("clicked!....");
    }
}