package com.example.demo;

import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * The ImageController class is a REST controller that handles image processing requests.
 * It provides two endpoints for processing images: /changedPicture64 and /changedPicture.
 */
@RestController
public class ImageController {

    /**
     * This method handles POST requests to /changedPicture64.
     * It decodes a base64 encoded image, processes it by increasing its brightness, and then encodes it back to base64.
     *
     * @param picture64 The base64 encoded image as a string.
     * @param factor The factor by which to increase the brightness of the image.
     * @return The processed image, base64 encoded as a string.
     * @throws IOException If an error occurs during image processing.
     */
    @PostMapping("/changedPicture64")
    public String changedPicture64(@RequestBody String picture64, @RequestParam int factor) throws IOException {
        // Decode
        byte[] imageBytes = Base64.getDecoder().decode(picture64);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage img = ImageIO.read(bis);

        // Process image
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.setImage(img);
        imageProcessor.increaseBrightness(factor);

        // Encode
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imageProcessor.getImage(), "jpg", baos);
        baos.flush();
        imageBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    /**
     * This method handles POST requests to /changedPicture.
     * It decodes a base64 encoded image, processes it by increasing its brightness, and then returns the processed image.
     *
     * @param picture64 The base64 encoded image as a string.
     * @param factor The factor by which to increase the brightness of the image.
     * @return The processed image.
     * @throws IOException If an error occurs during image processing.
     */
    @PostMapping("/changedPicture")
    public BufferedImage changedPicture(@RequestBody String picture64, @RequestParam int factor) throws IOException {
        // Decode
        byte[] imageBytes = Base64.getDecoder().decode(picture64);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage img = ImageIO.read(bis);

        // Process image
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.setImage(img);
        imageProcessor.increaseBrightness(factor);

        return imageProcessor.getImage();
    }
}