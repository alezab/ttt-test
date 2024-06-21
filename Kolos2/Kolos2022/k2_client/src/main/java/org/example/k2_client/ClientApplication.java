package org.example.k2_client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the main class for the client application.
 * It extends the JavaFX Application class and overrides the start method.
 */
public class ClientApplication extends Application {
    /**
     * The start method is the main entry point for all JavaFX applications.
     * It is called after the init method has returned, and after the system is ready for the application to begin running.
     *
     * @param stage the primary stage for this application, onto which the application scene can be set.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML document (view.fxml) to configure the user interface.
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("view.fxml"));

        // Create a new scene with the loaded FXML document and a size of 320x240 pixels.
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        // Set the title of the stage (window).
        stage.setTitle("Hello!");

        // Set the scene for the stage.
        stage.setScene(scene);

        // Show the stage (window).
        stage.show();

        // Create a new connection thread to the server at localhost:5000.
        ClientReceiver.thread = new ConnectionThread("localhost", 5000);

        // Start the connection thread.
        ClientReceiver.thread.start();
    }

    /**
     * The main method is the entry point for all Java applications.
     * For this application, it just calls the launch method inherited from the Application class.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        // Launch the JavaFX application.
        launch();
    }
}