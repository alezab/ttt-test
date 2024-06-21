package org.example.k2_client;

/**
 * This class is responsible for receiving words from the server and passing them to the controller.
 * It contains a reference to the controller and the connection thread.
 */
public class ClientReceiver {
    /**
     * The controller for the client application.
     * This is set in the constructor of the ClientController class.
     */
    public static ClientController controller;

    /**
     * The connection thread for the client application.
     * This is set in the start method of the ClientApplication class.
     */
    public static ConnectionThread thread;

    /**
     * This method is called when a word is received from the server.
     * It passes the word to the controller.
     *
     * @param word the received word.
     */
    public static void receiveWord(String word) {
        controller.onWordReceived(word);
    }
}