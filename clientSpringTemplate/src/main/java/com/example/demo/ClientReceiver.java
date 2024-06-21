package com.example.demo;

/**
 * This class is responsible for receiving data from the server in a client-server architecture.
 * It contains a static reference to a SpringController and a ServerThread.
 * The SpringController is used to handle HTTP requests and responses.
 * The ServerThread is used to handle communication with the server.
 */
public class ClientReceiver {
    /**
     * A static reference to a SpringController.
     * This controller is used to handle HTTP requests and responses.
     */
    public static SpringController controller;

    /**
     * A static reference to a ServerThread.
     * This thread is used to handle communication with the server.
     */
    public static ServerThread thread;
}