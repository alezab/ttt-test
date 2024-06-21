package org.example;

/**
 * This enum represents the type of a message in a chat application.
 * The type of a message can be one of the following:
 * - Broadcast: a message that is sent to all clients
 * - Login: a message that is sent when a client logs in
 * - Logout: a message that is sent when a client logs out
 * - Request: a message that is sent when a client makes a request
 * - Whisper: a private message that is sent from one client to another
 */
public enum MessageType {
    Broadcast,
    Login,
    Logout,
    Request,
    Whisper
}