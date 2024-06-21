package pl.umcs.oop.client;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class represents a simple client that connects to a server and sends lines of text from a file.
 */
public class Client {
    /**
     * The main method that is the entry point of the application.
     * It establishes a connection to the server, reads lines from a file and sends them to the server.
     * Each line is followed by a delay of 2 seconds.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            // Establish a connection to the server at localhost on port 2137
            Socket socket = new Socket("localhost",2137);

            // Create a PrintWriter to send data to the server
            PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);

            // Read lines from the file "tm00.csv" and send each line to the server
            Files.lines(Path.of("tm00.csv")).forEach(line -> {
                printWriter.println(line);
                try {
                    // Delay for 2 seconds after sending each line
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("LINE");
            });

            // Close the connection to the server
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}