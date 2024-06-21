package org.example;

/**
 * The MessageType enum represents the type of a message in the chat application.
 * It can be one of the following types:
 * - Broadcast: a message that is sent to all users.
 * - Login: a message that is sent when a user logs in.
 * - Logout: a message that is sent when a user logs out.
 * - Request: a message that is a request from a user.
 * - Whisper: a private message that is sent to a specific user.
 */
public enum MessageType {
    Broadcast,
    Login,
    Logout,
    Request,
    Whisper
}