package org.example.eegspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main application class for the EEG Spring Boot application.
 * This class contains the main method which serves as the entry point for the Spring Boot application.
 */
@SpringBootApplication
public class EegApplication {

    /**
     * The main method which serves as the entry point for the Spring Boot application.
     * This method delegates to Spring Boot's SpringApplication class, passing EegApplication.class as an argument
     * along with any command line arguments.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(EegApplication.class, args);
    }
}