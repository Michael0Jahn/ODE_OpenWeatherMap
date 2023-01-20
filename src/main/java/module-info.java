/**
 * Open Weather package
 */
module at.fhtw.openweathermap {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.json;


    opens at.fhtw.openweathermap to javafx.fxml;
    exports at.fhtw.openweathermap;
}