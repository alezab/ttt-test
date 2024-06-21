package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The TestController class is a REST controller that handles requests to the /hello endpoint.
 */
@RestController
public class TestController {

    /**
     * This method handles GET requests to /hello.
     * It returns a simple "Hello World" message.
     *
     * @return A string containing "Hello World".
     */
    @GetMapping("hello")
    public String test() {
        return "Hello World";
    }
}