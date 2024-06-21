package pl.umcs.oop.eegweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;

// curl -X GET http://localhost:2137/image

/**
 * This is a Spring controller class for handling image-related requests.
 */
@Controller
public class ImageController {

    /**
     * This method handles GET requests to the "/image" endpoint.
     * It reads a base64 encoded image string from a file and adds it to the model.
     * The method then returns the name of the view (in this case "eegimage") to be rendered.
     *
     * @param model the model object provided by Spring MVC, used to pass attributes to the view
     * @return the name of the view to be rendered
     * @throws IOException if there's an error reading the file
     */
    @GetMapping("/image")
    public String image(Model model) throws IOException {
        String base64;
        BufferedReader reader = new BufferedReader(new FileReader("/tmp/data.txt"));
        base64 = reader.readLine();
        model.addAttribute("image", base64);
        return "eegimage";
    }
}