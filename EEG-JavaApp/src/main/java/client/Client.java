package client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * The Client class represents a client in a client-server architecture.
 * It is responsible for sending data to the server.
 */
public class Client {

    /**
     * The main method is the entry point of the program.
     * It prompts the user for a username and a CSV file path, and then sends the data from the CSV file to the server.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter CSV file path: "); // src/main/resources/tm00.csv
        //String filepath = scanner.nextLine();
        String filepath = "src/main/resources/tm00.csv";
        sendData(username, filepath);
    }

    /**
     * The sendData method sends data from a CSV file to the server.
     * It first sends the username, and then sends each line of the CSV file as a separate message.
     * Each message consists of the electrode number and the data from the corresponding line in the CSV file.
     * After all data has been sent, it sends a "bye" message to indicate the end of the transmission.
     *
     * @param username the username of the user
     * @param filepath the path to the CSV file
     */
    public static void sendData(String username, String filepath) {
        try (Socket socket = new Socket("localhost", 12345);
             OutputStream out = socket.getOutputStream();
             BufferedReader reader = new BufferedReader(new FileReader(filepath))) {

            out.write((username + "\n").getBytes());

            String line;
            int electrodeNumber = 0;
            while ((line = reader.readLine()) != null) {
                out.write((electrodeNumber + "," + line + "\n").getBytes());
                System.out.println("Sent data for electrode number " + electrodeNumber); // print progress
                Thread.sleep(2000);
                electrodeNumber++;
            }
            out.write("bye\n".getBytes());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}