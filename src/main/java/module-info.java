module com.example.demo7 {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;
    //requires org.controlsfx.controls;

    opens com.example.demo7 to javafx.fxml;
    exports com.example.demo7;
}