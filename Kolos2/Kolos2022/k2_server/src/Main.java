/**
 * Main class that contains the main method which is the entry point of the application.
 */
public class Main {
    /**
     * The main method of the application.
     * It creates an instance of WordBag, populates it, creates a Server instance and starts it.
     * @param args Command line arguments passed to the application. Not used in this application.
     */
    public static void main(String[] args) {
        // Create a new WordBag instance
        WordBag wordBag = new WordBag();
        // Populate the WordBag instance
        wordBag.populate();
        // Create a new Server instance
        Server server = new Server(5000, wordBag);
        // Start the Server instance
        server.start();
        // Start sending messages from the Server instance
        server.startSending();
    }
}