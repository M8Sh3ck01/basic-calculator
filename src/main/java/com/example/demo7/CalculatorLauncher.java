package com.example.demo7;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class CalculatorLauncher extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Parent loader = FXMLLoader.load(getClass().getResource("calculator.fxml"));
        stage.setScene(new Scene(loader));
        stage.setTitle("Basic Calculator");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
