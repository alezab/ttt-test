package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

/**
 * This class represents a client thread in a chat server application.
 * Each client is handled in a separate thread.
 */
public class ClientThread extends Thread {
    private Socket client;
    private Server server;
    private PrintWriter writer;
    String username;

    /**
     * Constructs a new ClientThread.
     *
     * @param socket the socket connection to the client
     * @param server the server that handles this client
     */
    public ClientThread(Socket socket, Server server) {
        this.client = socket;
        this.server = server;
    }

    /**
     * The main execution method for the thread.
     * It handles the incoming messages from the client and broadcasts them to other clients.
     */
    @Override
    public void run() {
        try (InputStream input = client.getInputStream();
             OutputStream output = client.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            writer = new PrintWriter(output, true);

            String rawMessage;

            // Continuously read messages from the client
            while ((rawMessage = reader.readLine()) != null) {
                Message message = new ObjectMapper().readValue(rawMessage, Message.class);

                // Handle the different types of messages
                switch (message.type) {
                    case Login -> {
                        username = message.content;
                        server.broadcast(new Message(MessageType.Broadcast, username + " has joined the chat"));
                    }
                    case Broadcast -> {
                        String formattedMessage = "[" + username + "] " + message.content;
                        server.broadcast(new Message(MessageType.Broadcast, formattedMessage));
                    }
                    case Logout -> {
                        server.broadcast(new Message(MessageType.Broadcast, username + " has left the chat"));
                        server.removeClient(this);
                        client.close();
                        return; // Exit the thread
                    }
                    case Request -> server.online(this);
                    case Whisper -> {
                        String destUsername = rawMessage.split(" ")[1];
                        String actualMessage = rawMessage.split(" ", 3)[2];
                        String formattedMessage = "[" + username + "] " + actualMessage;
                        server.whisper(new Message(MessageType.Whisper, formattedMessage), destUsername);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.removeClient(this);
            if (username != null) {
                try {
                    server.broadcast(new Message(MessageType.Broadcast, username + " has left the chat"));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends a message to the client.
     *
     * @param message the message to send
     * @throws JsonProcessingException if the message cannot be converted to JSON
     */
    public void send(Message message) throws JsonProcessingException {
        String rawMessage = new ObjectMapper().writeValueAsString(message);
        writer.println(rawMessage);
    }
}