package at.fhtw.openweathermap;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

/**
 * TCP Server
 */
public class TCPServer {
    public static ServerSocket server;

    /**
     * Main method
     * @param args (not used)
     */
    public static void main(String[] args) {
        startServer();
        runServer();
    }

    /**
     * Startup the TCP Server
     */
    public static void startServer() {
        System.out.println("Server: starting up server");
        try {
            server = new ServerSocket(4711);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Run the TCP Server.
     * Read data from external source via API Key and send it to the TCP Client.
     *
     */
    public static void runServer() {
        try {

            OutputStream bOutputStream;
            InputStream bInputStream;

            String inputMessage;
            String sentMessage;

            System.out.println("Server: waiting for connection");
            Socket socketToClient = server.accept();

            /* Send welcoming Message */
            System.out.println("Server: connected to Client " + socketToClient.getInetAddress());
            bOutputStream = socketToClient.getOutputStream();
            sentMessage = "\n";
            bOutputStream.write(sentMessage.getBytes());
            String exitMessage = "";


            while (!exitMessage.equals("exit")) {
                // process incoming data
                bInputStream = socketToClient.getInputStream();
                byte[] b = new byte[12];
                int bytes = bInputStream.read(b);
                System.out.println("Server: received " + bytes + " Bytes from Client");
                inputMessage = new String(b);
                exitMessage = inputMessage;

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

                // Send Back Input Data to client
                bOutputStream = socketToClient.getOutputStream();
                sentMessage = response.toString();
                bOutputStream.write(sentMessage.getBytes() );
                System.out.println("Server: Message from Client: " + sentMessage);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
