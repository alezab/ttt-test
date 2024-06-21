import java.io.*;
import java.net.Socket;

/**
 * ClientThread class extends Thread class and represents a client in a server-client architecture.
 * Each client is handled in a separate thread.
 */
public class ClientThread extends Thread {

    private Socket socket;
    private PrintWriter writer;
    private Server server;

    /**
     * Constructor for the ClientThread class.
     * @param socket The socket associated with the client.
     * @param server The server that the client is connected to.
     */
    public ClientThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * Returns the socket associated with the client.
     * @return The socket associated with the client.
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * The main execution method for the thread.
     * It handles the input and output streams of the socket, and removes the client from the server when the client disconnects.
     */
    public void run(){
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
            while (reader.readLine() != null) { }
            System.out.println("closed");
            server.removeClient(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to the client.
     * @param message The message to be sent to the client.
     */
    public void send(String message){
        writer.println(message);
    }
}