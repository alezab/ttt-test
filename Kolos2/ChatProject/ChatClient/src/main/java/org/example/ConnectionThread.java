package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

/**
 * The ConnectionThread class extends the Thread class and is responsible for managing the connection with the server.
 * It handles sending and receiving messages.
 */
public class ConnectionThread extends Thread {
    Socket client;
    PrintWriter writer;

    /**
     * The constructor for the ConnectionThread class.
     * It establishes a connection with the server.
     *
     * @param address The server address to connect to.
     * @param port The server port to connect to.
     * @throws IOException If an input or output exception occurred
     */
    public ConnectionThread(String address, int port) throws IOException {
        client = new Socket(address, port);
    }

    /**
     * The run method is called when the thread is started.
     * It listens for incoming messages from the server and prints them to the console.
     */
    @Override
    public void run() {
        try (InputStream input = client.getInputStream();
             OutputStream output = client.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            writer = new PrintWriter(output, true);

            String rawMessage;

            while ((rawMessage = reader.readLine()) != null) {
                Message message = new ObjectMapper().readValue(rawMessage, Message.class);
                System.out.println(message.content);
            }

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    /**
     * The send method sends a message to the server.
     *
     * @param message The message to send.
     * @throws JsonProcessingException If a JSON processing error occurred
     */
    public void send(Message message) throws JsonProcessingException {
        String rawMessage = new ObjectMapper().writeValueAsString(message);
        writer.println(rawMessage);
    }

    /**
     * The login method sends a login message to the server.
     *
     * @param login The login message to send.
     * @throws JsonProcessingException If a JSON processing error occurred
     */
    public void login(String login) throws JsonProcessingException {
        Message message = new Message(MessageType.Login, login);
        send(message);
    }
}