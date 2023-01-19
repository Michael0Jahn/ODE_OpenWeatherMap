package at.fhtw.openweathermap;

import javafx.application.Application;
import javafx.event.ActionEvent;
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

public class TCPClient extends Application {

    private Socket client;
    private OutputStream bOutputStream;
    private String inputMessage;
    private String sentMessage;
    private Alert alert = new Alert(Alert.AlertType.WARNING, "Error. That didn't work!");

    @FXML
    private TextArea txt_conversation;
    @FXML
    private TextField txt_enterMessage;
    @FXML
    private TextArea textareaLog;
    @FXML
    private TextArea textfieldCity;
    @FXML
    private TextArea textfieldHumidity;
    @FXML
    private TextArea textfieldTemperature;


    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TCPClient.class.getResource("OpenWeather-GUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 300);
            stage.setTitle("OpenWeatherMap");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e ) {
            e.printStackTrace();
            alert.show();
        }
    }

    @FXML
    private void connectClient(ActionEvent event) {
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
                //log(txt_conversation, "Antwort: " + inputMessage);
            } catch (IOException e) {
                e.printStackTrace();
                alert.show();
            }
        }
    }

/*    @FXML
    private void sendMessage(ActionEvent event) {
        try {
            bOutputStream = client.getOutputStream();
            sentMessage = "send";
            bOutputStream.write(sentMessage.getBytes());
            log("Client: sent some Bytes to Server");
            log(txt_conversation, "Client: Message sent: " + sentMessage);

            //Receive The Echo
            InputStream bInputStream = client.getInputStream();
            byte[] b = new byte[12];
            int bytes = bInputStream.read(b);
            log("Client: received " + bytes + " Bytes from Server");
            inputMessage = new String(b);
            log(txt_conversation, "Server: Message received: " + inputMessage);

        } catch (IOException e) {
            e.printStackTrace();
            alert.show();
        }
    }*/

    public void log(TextArea t, String s) {
        t.appendText(s + "\n");
    }
    public void log(String s) {
        textareaLog.appendText(s + "\n");
    }

    public void sendRequest(ActionEvent actionEvent) {
        try {
            bOutputStream = client.getOutputStream();
            sentMessage = "send";
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
            log(textfieldCity, "Vienna");
            log(textfieldTemperature, "" + temp + "C");
            log(textfieldHumidity, "" + humidity + "%");

        } catch (IOException e) {
            e.printStackTrace();
            alert.show();
        }
    }
}