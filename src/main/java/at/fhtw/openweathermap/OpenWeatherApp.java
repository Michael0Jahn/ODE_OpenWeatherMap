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
import java.util.Map;

import com.google.gson.Gson;

public class OpenWeatherApp extends Application {
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
        TextField textfieldPrecipitation = new TextField();
        TextField textfieldTemperature = new TextField();
        Label labelCity = new Label("City:");
        Label labelHumi = new Label("Humidity:");
        Label labelPreci = new Label("Precipitation:");
        Label labelTemp = new Label("Temperature:");

        outerVBox.getChildren().addAll(textareaLog, borderPane);
        borderPane.setLeft(innerVBox);
        innerVBox.getChildren().addAll(labelCity, textfieldCity, labelHumi, textfieldHumidity, labelPreci, textfieldPrecipitation, labelTemp, textfieldTemperature);

        textfieldCity.setPrefHeight(60);
        textfieldHumidity.setPrefWidth(200);
        innerVBox.setSpacing(10);
        outerVBox.setSpacing(10);
        innerVBox.setPadding(new Insets(0,10,0,10));
        outerVBox.setPadding(new Insets(10,0,0,0));
    }

    public static void main(String[] args) throws IOException {
        var city = "Vienna";
        String apiKey = "f3d7db6b1cfc8feb58ded26985994224";
        String urlString = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, apiKey);
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response);

        Gson gson = new Gson();

        Map<String, String> map = gson.fromJson(response.toString(), Map.class);
        Map result = gson.fromJson(response.toString(), Map.class);
        System.out.println(map.get("main"));

        launch();
    }
}