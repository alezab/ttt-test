package client;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.*;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The ClientTest class contains unit tests for the Client class.
 * It uses the JUnit 5 framework for testing.
 */
public class ClientTest {

    /**
     * The testSendData method is a parameterized test that tests the sendData method of the Client class.
     * It uses data from a CSV file as input.
     * It sends data to the server and then checks if the expected image was saved to the database.
     *
     * @param username the username of the user
     * @param filepath the path to the CSV file
     * @param expectedImagePath the path to the expected image file
     */
    @ParameterizedTest
    @CsvFileSource(resources = "tm00.csv", numLinesToSkip = 1)
    public void testSendData(String username, String filepath, String expectedImagePath) {
        Client.sendData(username, filepath);

        String expectedBase64Image = getBase64Image(expectedImagePath);
        String actualBase64Image = getBase64ImageFromDB(username, 0); // Assuming single electrode

        assertEquals(expectedBase64Image, actualBase64Image);
    }

    /**
     * The getBase64Image method reads an image from a file, converts it to a BufferedImage,
     * and then converts the BufferedImage to a Base64-encoded string.
     *
     * @param imagePath the path to the image file
     * @return the Base64-encoded string of the image
     */
    private String getBase64Image(String imagePath) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(imagePath));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * The getBase64ImageFromDB method retrieves a Base64-encoded image from the database
     * based on the username and electrode number.
     * This method needs to be implemented.
     *
     * @param username the username of the user
     * @param electrodeNumber the electrode number
     * @return the Base64-encoded image from the database
     */
    private String getBase64ImageFromDB(String username, int electrodeNumber) {
        // Implement this method to retrieve the base64 image from the database
        // based on the username and electrodeNumber.
        return "";
    }
}