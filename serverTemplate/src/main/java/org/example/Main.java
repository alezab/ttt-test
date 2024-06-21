package org.example;

import java.io.IOException;

/**
 * The Main class is the entry point of the application.
 * It is responsible for starting the server.
 */
public class Main {
    /**
     * The main method starts the server at port 8000.
     * @param args The command line arguments. This application does not use command line arguments.
     * @throws RuntimeException If an I/O error occurs when starting the server.
     */
    public static void main(String[] args) {
        try {
            // Create a new server instance listening on port 8000
            Server server = new Server(8000);
            // Start the server
            server.listen();
        } catch (IOException e) {
            // If an I/O error occurs, wrap it in a RuntimeException and throw it
            throw new RuntimeException(e);
        }
    }
}