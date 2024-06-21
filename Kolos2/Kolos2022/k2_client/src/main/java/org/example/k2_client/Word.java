package org.example.k2_client;

import java.time.LocalTime;

/**
 * This class represents a Word object.
 * It contains a time and a word string.
 */
public class Word {
    private LocalTime time;
    private String word;

    /**
     * The constructor for the Word class.
     * It initializes the time and word with the provided values.
     *
     * @param time the time when the word was received.
     * @param word the received word.
     */
    public Word(LocalTime time, String word) {
        this.time = time;
        this.word = word;
    }

    /**
     * This method sets the time of the Word object.
     *
     * @param time the new time.
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * This method sets the word of the Word object.
     *
     * @param word the new word.
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * This method returns the time of the Word object.
     *
     * @return the time of the Word object.
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * This method returns the word of the Word object.
     *
     * @return the word of the Word object.
     */
    public String getWord() {
        return word;
    }
}