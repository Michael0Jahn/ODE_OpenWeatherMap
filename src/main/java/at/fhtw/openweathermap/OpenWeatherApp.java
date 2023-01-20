package at.fhtw.openweathermap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/**
 * Open Weather Application
 */
public class OpenWeatherApp extends Application {
    /**
     * Define several parameters for GUI
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OpenWeatherApp.class.getResource("OpenWeather-GUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 300);
        stage.setTitle("OpenWeatherMap");
        stage.setScene(scene);
        stage.show();

        VBox outerVBox = new VBox();
        BorderPane borderPane = new BorderPane();
        VBox innerVBox = new VBox();
        TextArea textareaLog = new TextArea();
        TextField textfieldCity = new TextField();
        TextField textfieldHumidity = new TextField();
        TextField textfieldPressure = new TextField();
        TextField textfieldTemperature = new TextField();
        Label labelCity = new Label("City:");
        Label labelHumi = new Label("Humidity:");
        Label labelPress = new Label("Pressure:");
        Label labelTemp = new Label("Temperature:");

        outerVBox.getChildren().addAll(textareaLog, borderPane);
        borderPane.setLeft(innerVBox);
        innerVBox.getChildren().addAll(labelCity, textfieldCity, labelHumi, textfieldHumidity, labelPress, textfieldPressure, labelTemp, textfieldTemperature);

        textfieldCity.setPrefHeight(60);
        textfieldHumidity.setPrefWidth(200);
        innerVBox.setSpacing(10);
        outerVBox.setSpacing(10);
        innerVBox.setPadding(new Insets(0,10,0,10));
        outerVBox.setPadding(new Insets(10,0,0,0));
    }

    /**
     * Start Application.
     * Read data from external source via API Key and print it.
     * @param args (not used)
     * @throws IOException throws IO Exception
     *
     */
    public static void main(String[] args) throws IOException {
        // requests the data from an external source via API Key
        String city = "Vienna";
        String apiKey = "f3d7db6b1cfc8feb58ded26985994224";
        String urlString = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=%s", city, apiKey);
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // reads the input from the API Key
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // prints the data from the defined keys in the command line
        JSONObject map = new JSONObject(response.toString());
        System.out.println("Temperature: " + map.getJSONObject("main").getDouble("temp") + " °C");
        System.out.println("Humidity: " + map.getJSONObject("main").getDouble("humidity")+" %");
        System.out.println("Pressure: " + map.getJSONObject("main").getDouble("pressure")+" hPa");
        //if we ever need data out of the array this is the way
        //System.out.println("humidity: " + map.getJSONArray("weather").getJSONObject(0).getString("main"));
        launch();
    }
}