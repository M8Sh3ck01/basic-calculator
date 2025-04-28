package com.example.demo7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalculatorLauncher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent loader = FXMLLoader.load(getClass().getResource("calculator.fxml"));
        stage.setScene(new Scene(loader));
        stage.setTitle("Basic Calculator");


       // Disable maximize and resizing
        stage.setResizable(false);
        // Set the app window icon
        stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("icons8-calculator-96.png")));

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
