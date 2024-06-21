package pl.umcs.oop.server;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Base64;

/**
 * This class represents a client that receives data from a server, processes it and generates an image.
 * It implements the Runnable interface, so it can be used to create a thread.
 */
public class Client implements Runnable{
    private List<List<Float>> data = new ArrayList<>();
    private BufferedReader reader;

    /**
     * Constructor for the Client class.
     * It initializes the reader with the input stream of the socket.
     * @param socket the socket connected to the server
     * @throws IOException if an I/O error occurs when creating the input stream
     */
    public Client(Socket socket) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * This method parses a message received from the server.
     * It splits the message by commas, converts each part to a float and adds it to the data list.
     * @param message the message received from the server
     */
    private void parseMessage(String message)
    {
        List<Float> lineData = Arrays.stream(message.split(",")).map(Float::parseFloat).toList();
        data.add(lineData);
    }

    /**
     * This method generates an image based on the data received from the server.
     * It creates a BufferedImage and sets the RGB value of each pixel based on the data.
     * The image is then written to a file in Base64 format.
     * @param index the index of the data line to be used for generating the image
     * @throws IOException if an I/O error occurs when writing the image to the file
     */
    public void generate(int index) throws IOException {
        List<Float> dataLine = data.get(index);
        BufferedImage image = new BufferedImage(dataLine.size(), 140, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < dataLine.size(); i++) {
            int y0 = image.getHeight() / 2;
            int y = (int) (-dataLine.get(i) + y0);
            image.setRGB(i, y, 0xffff0000);
        }
        File file = new File("/tmp/data.txt");
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println(encodeBase64(image));
        writer.close();
    }

    /**
     * This method encodes a BufferedImage to a Base64 string.
     * @param image the image to be encoded
     * @return the Base64 string representation of the image
     * @throws IOException if an I/O error occurs when writing the image to the ByteArrayOutputStream
     */
    private static String encodeBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        String base64Image = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        return base64Image;
    }

    /**
     * The run method of the Runnable interface.
     * It continuously reads messages from the server, parses them and generates images until no more messages are received.
     */
    @Override
    public void run() {
        String message;
        try {
            while ((message = reader.readLine())!= null) {
                parseMessage(message);
                generate(data.size() - 1);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}