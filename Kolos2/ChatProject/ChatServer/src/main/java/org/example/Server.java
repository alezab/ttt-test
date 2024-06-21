package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class represents a chat server.
 * It listens for incoming client connections and handles them in separate threads.
 * It also provides methods for broadcasting messages to all clients, removing a client, and sending a message to a specific client.
 */
public class Server {
    private ServerSocket socket;
    private List<ClientThread> clients = new CopyOnWriteArrayList<>();

    /**
     * Constructs a new Server that listens on the specified port.
     *
     * @param port the port to listen on
     * @throws IOException if an I/O error occurs when opening the socket
     */
    public Server(int port) throws IOException {
        socket = new ServerSocket(port);
    }

    /**
     * Starts the server.
     * This method blocks and continuously listens for incoming client connections.
     * When a client connects, a new ClientThread is created and started for that client.
     *
     * @throws IOException if an I/O error occurs when waiting for a connection
     */
    public void listen() throws IOException {
        while (true) {
            Socket client = socket.accept();
            ClientThread thread = new ClientThread(client, this);
            clients.add(thread);
            thread.start();
        }
    }

    /**
     * Sends a message to all connected clients.
     *
     * @param message the message to send
     * @throws JsonProcessingException if the message cannot be converted to JSON
     */
    public synchronized void broadcast(Message message) throws JsonProcessingException {
        for (ClientThread client : clients) {
            client.send(message);
        }
    }

    /**
     * Removes a client from the list of connected clients.
     *
     * @param client the client to remove
     */
    public void removeClient(ClientThread client) {
        clients.remove(client);
    }

    /**
     * Sends a list of all connected clients to the specified client.
     *
     * @param client the client to send the list to
     * @throws JsonProcessingException if the message cannot be converted to JSON
     */
    public synchronized void online(ClientThread client) throws JsonProcessingException {
        StringBuilder builder = new StringBuilder();
        for(ClientThread c : clients) {
            builder.append(c.username).append("\n");
        }
        client.send(new Message(MessageType.Request, builder.toString()));
    }

    /**
     * Sends a private message to a specific client.
     *
     * @param message the message to send
     * @param destUsername the username of the client to send the message to
     * @throws JsonProcessingException if the message cannot be converted to JSON
     */
    public synchronized void whisper(Message message, String destUsername) throws JsonProcessingException {
        for(ClientThread c : clients) {
            if(c.username.equals(destUsername)) {
                c.send(message);
            }
        }
    }
}