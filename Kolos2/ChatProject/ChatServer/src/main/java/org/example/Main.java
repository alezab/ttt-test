package org.example;

import java.io.IOException;

/**
 * This is the main class of the chat server application.
 * It creates a new Server instance and starts listening for client connections.
 */
public class Main {
    /**
     * The main method of the application.
     * It creates a new Server instance and starts it.
     * If an IOException occurs, it is wrapped in a RuntimeException and rethrown.
     *
     * @param args command-line arguments (not used)
     * @throws RuntimeException if an IOException occurs when creating the Server or starting it
     */
    public static void main(String[] args) {
        try {
            // Create a new Server that listens on port 8000
            Server server = new Server(8000);
            // Start the server
            server.listen();
        } catch (IOException e) {
            // If an IOException occurs, wrap it in a RuntimeException and rethrow it
            throw new RuntimeException(e);
        }
    }
}