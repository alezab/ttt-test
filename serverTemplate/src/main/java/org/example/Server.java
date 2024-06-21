package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class represents a server in a client-server architecture.
 * The server listens for incoming client connections and starts a new thread for each client.
 */
public class Server {
    // The server socket
    private ServerSocket socket;
    // A thread-safe list of client threads
    private List<ClientThread> clients = new CopyOnWriteArrayList<>();

    /**
     * Constructor for the Server class.
     * @param port The port number on which the server should listen for incoming connections.
     * @throws IOException If an I/O error occurs when opening the server socket.
     */
    public Server(int port) throws IOException {
        socket = new ServerSocket(port);
    }

    /**
     * Starts the server.
     * The server continuously listens for incoming client connections and starts a new thread for each client.
     * @throws IOException If an I/O error occurs when accepting a client connection.
     */
    public void listen() throws IOException {
        while (true) {
            // Accept a client connection
            Socket client = socket.accept();
            // Start a new thread for the client
            ClientThread thread = new ClientThread(client, this);
            // Add the client thread to the list of clients
            clients.add(thread);
            // Start the client thread
            thread.start();
        }
    }

    // Example of broadcasting a message to all clients

    /**
     * Sends a message to all connected clients.
     * The message is converted to a JSON string using Jackson before being sent.
     * @param message The message to send.
     * @throws JsonProcessingException If the message cannot be converted to a JSON string.
     */
//    public void broadcast(Message message) throws JsonProcessingException {
//        for(ClientThread client : clients) {
//            client.send(message);
//        }
//    }
}