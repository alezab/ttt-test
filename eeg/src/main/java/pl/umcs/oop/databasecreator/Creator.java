package pl.umcs.oop.databasecreator;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is responsible for creating and deleting a SQLite database.
 */
public class Creator {

    /**
     * The main method that is the entry point of the application.
     * It creates a SQLite database at the specified URL.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        String url = "jdbc:sqlite:/tmp/usereeg.db";
        Creator creator = new Creator();
        creator.create(url);
    }

    /**
     * This method creates a SQLite database at the specified URL.
     * It creates a table named 'user_eeg' if it does not exist.
     * @param url the URL where the database will be created
     */
    public void create(String url){
        String createTableSQL = "CREATE TABLE IF NOT EXISTS user_eeg ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT NOT NULL,"
                + "electrode_number INTEGER NOT NULL,"
                + "image TEXT NOT NULL"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Ok");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method deletes the SQLite database at the specified URL.
     * @param url the URL where the database will be deleted
     */
    public void delete(String url) {
        String filepath = url.substring(url.indexOf("\\"));
        File dbFile = new File(filepath);
        if (dbFile.exists()) {
            if (!dbFile.delete()){
                System.out.println("Error during delete database");
            }
        }else{
            System.out.println("Error database dosent exist");
        }
    }
}