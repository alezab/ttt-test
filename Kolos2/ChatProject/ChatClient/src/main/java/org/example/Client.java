package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The Client class is responsible for managing the client-side operations of the application.
 * It handles user input, establishes a connection with the server, and sends messages.
 */
public class Client {
    private ConnectionThread connectionThread;

    /**
     * Starts the client application.
     * It prompts the user for a username, establishes a connection with the server, and starts listening for user input.
     *
     * @param address The server address to connect to.
     * @param port The server port to connect to.
     */
    public void start(String address, int port) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Enter your username: ");
            String username = reader.readLine();

            connectionThread = new ConnectionThread(address, port);
            connectionThread.start();
            connectionThread.login(username);

            String rawMessage;
            while ((rawMessage = reader.readLine()) != null) {
                Message message;
                if(rawMessage.equals("/online")) {
                    message = new Message(MessageType.Request, rawMessage);
                } else if(rawMessage.split(" ")[0].equals("/w")) {
                    message = new Message(MessageType.Whisper, rawMessage);
                } else {
                    message = new Message(MessageType.Broadcast, rawMessage);
                }
                connectionThread.send(message);
            }
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        } finally {
            stop();
        }
    }

    /**
     * Stops the client application.
     * It closes the connection with the server if it's still open.
     */
    public void stop() {
        if (connectionThread != null && connectionThread.isAlive()) {
            try {
                connectionThread.client.close();
            } catch (IOException e) {
                System.err.println("Error closing connection thread: " + e.getMessage());
            }
        }
    }
}