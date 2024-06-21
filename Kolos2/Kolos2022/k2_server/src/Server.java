import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Server class extends Thread class and represents a server in a server-client architecture.
 * Each client is handled in a separate thread.
 */
public class Server extends Thread {
    private ServerSocket serverSocket;
    private List<ClientThread> clients = new ArrayList<>();
    private WordBag wordBag;

    /**
     * Constructor for the Server class.
     * @param port The port on which the server is running.
     * @param wordBag An instance of WordBag class.
     */
    public Server(int port, WordBag wordBag) {
        this.wordBag = wordBag;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts sending messages to all clients at a fixed rate.
     */
    public void startSending() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                broadcast(wordBag.get());
            }
        }, 0, 5000);
    }

    /**
     * The main execution method for the thread.
     * It accepts client connections and starts a new thread for each client.
     */
    public void run(){
        while(true){
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
                ClientThread thread = new ClientThread(clientSocket, this);
                clients.add(thread);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Removes a client from the list of clients.
     * @param client The client to be removed.
     */
    public void removeClient(ClientThread client) {
        clients.remove(client);
        System.out.println("removed");
    }

    /**
     * Sends a message to all clients.
     * @param message The message to be sent.
     */
    public void broadcast(String message){
        for(var client : clients)
            client.send(message);
    }
}