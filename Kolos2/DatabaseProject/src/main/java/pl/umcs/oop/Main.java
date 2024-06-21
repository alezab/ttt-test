package pl.umcs.oop;

import pl.umcs.oop.auth.Account;
import pl.umcs.oop.database.DatabaseConnection;

import javax.naming.AuthenticationException;

/**
 * Main class of the application.
 */
public class Main {
    /**
     * The main method of the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Connect to the database named "my.db"
        DatabaseConnection.connect("my.db");

        // Initialize the persistence layer of the Account class
        Account.Persistence.init();

        // The following line is commented out, but it would register a new account with username "notch" and password "verysecurepassword"
        // Account.Persistence.register("notch", "verysecurepassword");

        try {
            // Try to authenticate the user "notch" with password "verysecurepassword"
            // If successful, the authenticated Account object is stored in the variable "notch"
            Account notch = Account.Persistence.authenticate("notch", "verysecurepassword");
            // Print the authenticated Account object to the console
            System.out.println(notch);
        } catch (AuthenticationException e) {
            // If authentication fails, throw a new RuntimeException
            throw new RuntimeException(e);
        }
        // Disconnect from the database
        DatabaseConnection.disconnect();
    }
}