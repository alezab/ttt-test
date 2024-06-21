package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

/**
 * This class extends Thread and represents a server thread in a client-server architecture.
 * It is responsible for handling communication with the server.
 */
public class ServerThread extends Thread {
    Socket socket;
    PrintWriter writer;

    /**
     * Constructor for the ServerThread class.
     * It initializes a new socket with the given address and port.
     *
     * @param address The address of the server.
     * @param port The port number of the server.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public ServerThread(String address, int port) throws IOException {
        socket = new Socket(address, port);
    }

    /**
     * The run method is called when the thread is started.
     * It sets up the input and output streams, and then enters a loop where it reads messages from the server.
     */
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output,true);

            String rawMessage;

            // The loop where messages from the server are read.
            while((rawMessage = reader.readLine()) != null) {
                // Message message = new ObjectMapper().readValue(rawMessage, Message.class); -> message to klasa własna na potrzeby jsona

                // Otrzymywanie wiadomości z serwera odbywa sie w tej petli

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // The send method is commented out, but it would be used to send a message to the server.
    // public void send(Message message) throws JsonProcessingException {
    //     String rawMessage = new ObjectMapper()
    //             .writeValueAsString(message);
    //     writer.println(rawMessage);
    // }
}