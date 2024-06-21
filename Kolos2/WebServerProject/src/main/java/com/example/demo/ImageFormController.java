package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * The ImageFormController class is a Spring MVC Controller that handles image processing requests from a form.
 * It provides two endpoints: /imageform and /imageform/upload.
 */
@Controller
public class ImageFormController {

    /**
     * This method handles GET requests to /imageform.
     * It returns the name of the view (in this case, "index") to be rendered.
     *
     * @return The name of the view to be rendered.
     */
    @GetMapping("/imageform")
    public String imageForm() {
        return "index";
    }

    /**
     * This method handles POST requests to /imageform/upload.
     * It receives an image and a brightness factor from the form, processes the image by increasing its brightness,
     * encodes the processed image to Base64, and adds it to the model to be displayed in the view.
     *
     * @param file The image file received from the form.
     * @param brightness The brightness factor received from the form.
     * @param model The Model object to which the processed image is added.
     * @return The name of the view (in this case, "image") to be rendered.
     * @throws IOException If an error occurs during image processing.
     */
    @PostMapping("/imageform/upload")
    public String uploadImage(@RequestParam("image") MultipartFile file,
                              @RequestParam("brightness") int brightness,
                              Model model) throws IOException {
        byte[] imageBytes = file.getBytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage img = ImageIO.read(bis);

        // Process image to increase brightness
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.setImage(img);
        imageProcessor.increaseBrightness(brightness);

        // Convert processed image to Base64
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imageProcessor.getImage(), "jpg", baos);
        baos.flush();
        byte[] processedImageBytes = baos.toByteArray();
        String encodedImage = Base64.getEncoder().encodeToString(processedImageBytes);

        model.addAttribute("image", encodedImage);
        return "image";
    }
}