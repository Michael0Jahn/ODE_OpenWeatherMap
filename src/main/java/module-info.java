module at.fhtw.openweathermap {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens at.fhtw.openweathermap to javafx.fxml;
    exports at.fhtw.openweathermap;
}