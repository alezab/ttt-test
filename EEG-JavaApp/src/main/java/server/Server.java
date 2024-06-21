package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * The Server class represents a server in a client-server architecture.
 * It is responsible for listening for connections from clients and processing the data received from them.
 */
public class Server {

    /**
     * The main method is the entry point of the program.
     * It creates a ServerSocket that listens on port 12345 and continuously accepts new connections from clients.
     * For each connection, it creates a new ClientHandler thread to handle the communication with the client.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is listening on port 12345");

            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The ClientHandler class is a thread that handles communication with a client.
     * It reads data from the client, processes it, and saves it to a database.
     */
    private static class ClientHandler extends Thread {
        private Socket socket;
        private String username;
        private Connection conn;

        /**
         * The ClientHandler constructor initializes the socket.
         *
         * @param socket the socket connected to the client
         */
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        /**
         * The run method is called when the thread is started.
         * It reads data from the client, processes it, and saves it to a database.
         */
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                username = in.readLine();

                String url = "jdbc:sqlite:usereeg.db";
                conn = DriverManager.getConnection(url);

                String line;
                int electrodeNumber = 0;
                while ((line = in.readLine()) != null && !line.equals("bye")) {
                    String[] parts = line.split(",", 2);
                    electrodeNumber = Integer.parseInt(parts[0]);
                    String data = parts[1];

                    String base64Image = createGraphImage(data);
                    saveToDatabase(username, electrodeNumber, base64Image);

                    System.out.println("Received and processed data for electrode number " + electrodeNumber); // print progress
                    electrodeNumber++;
                }

            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }

        /**
         * The createGraphImage method creates a graph image from a string of data.
         * It converts the data to integers, creates a BufferedImage, draws the graph on the image, and returns the image as a Base64-encoded string.
         *
         * @param data the data to be converted into a graph image
         * @return the graph image as a Base64-encoded string
         */
        private String createGraphImage(String data) {
            String[] values = data.split(",");
            int[] intValues = new int[values.length];
            for (int i = 0; i < values.length; i++) {
                intValues[i] = (int) (Double.parseDouble(values[i]) * 10 + 50);
            }

            BufferedImage image = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, 200, 100);
            g2d.setColor(Color.RED);
            for (int i = 0; i < intValues.length; i++) {
                g2d.fillRect(i, 50 - intValues[i], 1, 1);
            }
            g2d.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(image, "png", baos);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return Base64.getEncoder().encodeToString(baos.toByteArray());
        }

        /**
         * The saveToDatabase method saves a Base64-encoded image to a database.
         * It creates a PreparedStatement, sets the parameters, and executes the update.
         *
         * @param username the username of the user
         * @param electrodeNumber the electrode number
         * @param base64Image the Base64-encoded image
         */
        private void saveToDatabase(String username, int electrodeNumber, String base64Image) {
            String sql = "INSERT INTO user_eeg(username, electrode_number, image) VALUES(?,?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setInt(2, electrodeNumber);
                pstmt.setString(3, base64Image);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}