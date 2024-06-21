package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class represents a client in a client-server architecture.
 * It is responsible for starting a server thread and handling user input from the console.
 */
public class Client {

    /**
     * This method starts the client.
     * It creates a new server thread with the given address and port, and starts it.
     * Then, it enters a loop where it reads user input from the console.
     *
     * @param address The address of the server.
     * @param port The port number of the server.
     */
    public void start(String address, int port) {
        try {
            // Create and start a new server thread
            ServerThread thread = new ServerThread(address, port);
            thread.start();

            // Set up a reader to read user input from the console
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Enter a loop where user input is read from the console
            while(true) {
                String rawMessage = reader.readLine();
                // The client's operation loop from the console without implementation
            }

        } catch (IOException e) {
            // If an I/O error occurs, throw a new RuntimeException
            throw new RuntimeException(e);
        }
    }
}