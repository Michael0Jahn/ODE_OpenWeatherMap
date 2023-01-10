package at.fhtw.openweathermap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    private static ServerSocket server;

    public static void main(String[] args) {
        startServer();
        runServer();
    }

    // Startup the server
    private static void startServer() {
        System.out.println("Server: starting up server");
        try {
            server = new ServerSocket(4711);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // This function implements the runtime of the server
    private static void runServer() {
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

                // Send Back Input Data to client
                bOutputStream = socketToClient.getOutputStream();
                sentMessage = inputMessage;
                bOutputStream.write(sentMessage.getBytes() );
                System.out.println("Server: Message from Client: " + sentMessage);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
