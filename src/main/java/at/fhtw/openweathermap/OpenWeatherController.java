package at.fhtw.openweathermap;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class OpenWeatherController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextArea textareaLog;

    @FXML
    private TextField textfieldCity;

    @FXML
    private TextField textfieldHumidity;

    @FXML
    private TextField textfieldPrecipitation;

    @FXML
    private TextField textfieldTemperature;

    @FXML
    private Label labelCity;

    @FXML
    private Label labelHumi;

    @FXML
    private Label labelPreci;

    @FXML
    private Label labelTemp;

}
