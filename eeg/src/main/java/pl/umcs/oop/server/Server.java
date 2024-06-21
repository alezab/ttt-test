package pl.umcs.oop.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a server that listens for connections from clients.
 * When a client connects, it creates a new Client object and starts a new thread for it.
 */
public class Server {
    private ServerSocket ss;
    private List<Client> clients = new ArrayList<>();

    /**
     * Constructor for the Server class.
     * It initializes the ServerSocket with the port number 2137.
     * @throws IOException if an I/O error occurs when opening the socket
     */
    public Server() throws IOException {
        ss = new ServerSocket(2137);
    }

    /**
     * The main method that is the entry point of the application.
     * It creates a new Server object and starts listening for connections.
     * @param args command line arguments (not used)
     * @throws IOException if an I/O error occurs when opening the socket
     */
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.listen();
    }

    /**
     * This method listens for connections from clients.
     * When a client connects, it creates a new Client object, starts a new thread for it, and adds it to the clients list.
     * @throws IOException if an I/O error occurs when waiting for a connection
     */
    public void listen() throws IOException {
        while (true) {
            Socket socket = ss.accept();
            Client client = new Client(socket);
            new Thread(client).start();
            clients.add(client);
        }
    }
}