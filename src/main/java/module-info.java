module at.fhtw.openweathermap {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.fhtw.openweathermap to javafx.fxml;
    exports at.fhtw.openweathermap;
}