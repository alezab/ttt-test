package org.example;

/**
 * This class represents a message that can be sent or received in a chat application.
 * A message has a type and content.
 */
public class Message {
    /**
     * The type of the message. This can be one of several predefined types.
     */
    public MessageType type;

    /**
     * The content of the message. This is the actual text that the user typed.
     */
    public String content;

    /**
     * Default constructor. Required for Jackson's deserialization.
     */
    public Message() {}

    /**
     * Constructs a new Message with the given type and content.
     *
     * @param type the type of the message
     * @param content the content of the message
     */
    public Message(MessageType type, String content) {
        this.type = type;
        this.content = content;
    }
}