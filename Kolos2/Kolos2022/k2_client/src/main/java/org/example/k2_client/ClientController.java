package org.example.k2_client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This class is the controller for the client application.
 * It handles the user interface events and updates the UI based on the received words.
 */
public class ClientController {

    public Label wordCountLabel;
    public ListView wordList;
    public TextField filterField;

    /**
     * The constructor for the ClientController class.
     * It sets the controller in the ClientReceiver class to this instance.
     */
    public ClientController() {
        ClientReceiver.controller = this;
    }

    List<Word> listOfWords = new ArrayList<>();

    /**
     * This method is called when a word is received.
     * It adds the word to the list of words and updates the UI.
     *
     * @param word the received word.
     */
    public void onWordReceived(String word) {
        listOfWords.add(new Word(LocalTime.now(), word));
        Platform.runLater(() -> {
            wordCountLabel.setText(Integer.toString(listOfWords.size()));
            update();
        });
    }

    /**
     * This method updates the word list in the UI.
     * It filters the words based on the text in the filter field, sorts them, and displays them in the list view.
     */
    public void update() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss ");
        wordList.setItems(FXCollections.observableArrayList(listOfWords.stream()
                .filter((word) -> word.getWord().startsWith(filterField.getText()))
                .sorted(Comparator.comparing(word -> word.getWord()))
                .map((item) -> item.getTime().format(formatter) + item.getWord())
                .toList()
        ));
    }

    /**
     * This method is called when the enter key is pressed in the filter field.
     * It updates the word list in the UI.
     */
    public void onEnter() {
        Platform.runLater(this::update);
    }
}