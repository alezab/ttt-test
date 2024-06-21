package org.example;

/**
 * The Message class represents a message that can be sent or received in the chat application.
 * It contains a type and content.
 */
public class Message {
    /**
     * The type of the message. It can be a request, a whisper, a broadcast, or a login message.
     */
    public MessageType type;

    /**
     * The content of the message. It can be a text message, a command, or a username.
     */
    public String content;

    /**
     * The default constructor for the Message class.
     * It is required for the Jackson library to deserialize JSON strings into Message objects.
     */
    public Message() {}

    /**
     * The constructor for the Message class.
     * It initializes a new instance of the Message class with the specified type and content.
     *
     * @param type The type of the message.
     * @param content The content of the message.
     */
    public Message(MessageType type, String content) {
        this.type = type;
        this.content = content;
    }
}