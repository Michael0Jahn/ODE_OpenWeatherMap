package at.fhtw.openweathermap;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

/**
 * TCP Client
 */
public class TCPClient extends Application {
    private Socket client;
    private String inputMessage;
    private final Alert alert = new Alert(Alert.AlertType.WARNING, "Error. That didn't work!");
    private final String FILENAME = "log.txt";


    @FXML
    private TextField textfieldPressure;
    @FXML
    private TextField textfieldCity;
    @FXML
    private TextField textfieldHumidity;
    @FXML
    private TextField textfieldTemperature;
    @FXML
    private TextArea textareaLog;

    /**
     * Loads the GUI from the defined fxml file
     * @param stage sets the stage for the GUI
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TCPClient.class.getResource("OpenWeather-GUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 650, 310);
            stage.setTitle("OpenWeatherMap");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e ) {
            e.printStackTrace();
            alert.show();
        }
    }

    /**
     * Connect the TCP Client to the TCP Server
     */
    @FXML
    public void connectClient() {
        if(client == null) {
            try {
                client = new Socket("localhost", 4711);
                log("Client: connected to " + client.getInetAddress());

                //Receive The Echo
                InputStream bInputStream = client.getInputStream();
                byte[] b = new byte[12];
                int bytes = bInputStream.read(b);
                log("Client: received " + bytes + " Bytes from Server");
                inputMessage = new String(b);
            } catch (IOException e) {
                e.printStackTrace();
                alert.show();
            }
        }
    }

    /**
     * Send request to the TCP Server
     */
    public void sendRequest() {
        try {
            OutputStream bOutputStream = client.getOutputStream();
            String sentMessage = "send";
            bOutputStream.write(sentMessage.getBytes());
            log("Client: sent some Bytes to Server");

            //Receive The Echo
            InputStream bInputStream = client.getInputStream();
            byte[] b = new byte[500];
            int bytes = bInputStream.read(b);
            log("Client: received " + bytes + " Bytes from Server");
            inputMessage = new String(b);
            System.out.println(inputMessage);
            JSONObject map = new JSONObject(inputMessage);
            double temp = map.getJSONObject("main").getDouble("temp");
            double humidity = map.getJSONObject("main").getDouble("humidity");
            double pressure = map.getJSONObject("main").getDouble("pressure");
            printWeatherData(textfieldCity, "Vienna");
            printWeatherData(textfieldTemperature, "" + temp + " °C");
            printWeatherData(textfieldHumidity, "" + humidity + " %");
            printWeatherData(textfieldPressure, "" + pressure + " hPa");

        } catch (IOException e) {
            e.printStackTrace();
            alert.show();
        }
    }

    /**
     * Print the received Weatherdata in the textfields of the GUI.
     * @param t Textfield of the GUI
     * @param d Weatherdata
     */
    public void printWeatherData(TextField t, String d) {
        t.setText(d);
    }

    /**
     * Print the received data in the text log of the GUI.
     * Print the received data in a new log file
     * @param s data
     */
    public void log(String s) {
        textareaLog.appendText(s + "\n");
        new Thread(() -> {
            try {
                FileWriter fw = new FileWriter(FILENAME, true);
                fw.write(s + "\n");
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}