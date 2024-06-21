package org.example.k2_client;

import java.io.*;
import java.net.Socket;

/**
 * This class represents a connection thread for the client application.
 * It extends the Thread class and overrides the run method.
 * It is responsible for establishing a connection to the server and receiving words from the server.
 */
public class ConnectionThread extends Thread {
    /**
     * The socket for the connection to the server.
     */
    Socket socket;

    /**
     * The writer for sending messages to the server.
     */
    PrintWriter writer;

    /**
     * The constructor for the ConnectionThread class.
     * It creates a new socket with the specified address and port.
     *
     * @param address the address of the server.
     * @param port the port of the server.
     * @throws IOException if an I/O error occurs when creating the socket.
     */
    public ConnectionThread(String address, int port) throws IOException {
        socket = new Socket(address, port);
    }

    /**
     * The run method is the entry point for the thread.
     * It establishes the input and output streams for the socket, and continuously reads words from the server.
     * When a word is received, it is passed to the ClientReceiver class.
     */
    @Override
    public void run() {
        try (InputStream input = socket.getInputStream();
             OutputStream output = socket.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            writer = new PrintWriter(output, true);

            String rawMessage;

            // Continuously read words from the server until the connection is closed.
            while ((rawMessage = reader.readLine()) != null) {
                // Pass the received word to the ClientReceiver class.
                ClientReceiver.receiveWord(rawMessage);
            }

        } catch (IOException e) {
            // Print any connection errors to the standard error stream.
            System.err.println("Connection error: " + e.getMessage());
        }
    }
}