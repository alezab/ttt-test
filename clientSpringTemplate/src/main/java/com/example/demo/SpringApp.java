package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the main class for the Spring Boot application.
 * It is annotated with @SpringBootApplication, which is a convenience annotation that adds all of the following:
 * - @Configuration: Tags the class as a source of bean definitions for the application context.
 * - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
 * - @ComponentScan: Tells Spring to look for other components, configurations, and services in the 'com.example.demo' package.
 */
@SpringBootApplication
public class SpringApp {

	/**
	 * The main method is the entry point for the Spring Boot application.
	 * It runs the SpringApplication, which starts the application.
	 * After that, it creates a new Client and starts it with the specified host and port.
	 *
	 * @param args Command line arguments passed to the application. Not used in this method.
	 */
	public static void main(String[] args) {
		// Run the Spring Boot application
		SpringApplication.run(SpringApp.class, args);

		// Create a new Client
		Client client = new Client();

		// Start the client with the specified host and port
		client.start("localhost", 5001);
	}

}