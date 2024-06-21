package org.example;

import java.io.IOException;

/**
 * The Main class is the entry point of the application.
 * It creates a Client instance and starts it with a specified server address and port.
 */
public class Main {
    /**
     * The main method is the entry point of the Java application.
     * It creates a Client instance and starts it with a specified server address and port.
     *
     * @param args The command-line arguments. This parameter is not used.
     */
    public static void main(String[] args) {
        Client client = new Client();
        client.start("localhost", 8000);
    }
}