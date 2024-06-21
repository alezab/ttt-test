package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

/**
 * This class represents a client thread in a server-client architecture.
 * Each client thread is associated with a specific client socket and a server.
 */
public class ClientThread extends Thread {
    private Socket client; // The client's socket
    private Server server; // The server instance
    private PrintWriter writer; // Used for client-server communication

    /**
     * Constructor for the ClientThread class.
     * @param socket The client's socket.
     * @param server The server instance.
     */
    public ClientThread(Socket socket, Server server) {
        this.client = socket;
        this.server = server;
    }

    /**
     * The main execution method for the thread.
     * It sets up the input and output streams and reads incoming messages from the client.
     */
    @Override
    public void run() {
        try {
            InputStream input = client.getInputStream();
            OutputStream output = client.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            writer = new PrintWriter(output,true);

            String rawMessage;
            // Continuously read messages from the client and process them
            while((rawMessage = reader.readLine()) != null) {
                // Server-side call handling goes here
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Example of sending a Jackson/JSON message

    /**
     * Sends a message to the client.
     * The message is converted to a JSON string using Jackson before being sent.
     * @param message The message to send.
     * @throws JsonProcessingException If the message cannot be converted to a JSON string.
     */
//    public void send(Message message) throws JsonProcessingException {
//        String rawMessage = new ObjectMapper().writeValueAsString(message);
//        writer.println(rawMessage);
//    }
}