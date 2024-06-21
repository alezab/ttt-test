package pl.umcs.oop.music;

import pl.umcs.oop.database.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Represents a song with an artist, title, and duration.
 */
public record Song(
        String artist,
        String title,
        int duration) {

    /**
     * Represents the persistence layer of the Song class.
     */
    public static class Persistence {
        /**
         * Reads a song from the database with the given index.
         * @param index the index of the song in the database
         * @return an Optional containing the Song if it exists, or an empty Optional if it does not
         * @throws SQLException if there is an error with the database operation
         */
        public static Optional<Song> read(int index) throws SQLException {
            PreparedStatement statement = DatabaseConnection.
                    getConnection().prepareStatement(
                            "SELECT * FROM song WHERE id = ?"
                    );
            statement.setInt(1, index);
            statement.execute();

            ResultSet result = statement.getResultSet();
            Optional<Song> song = Optional.empty();

            if(result.next()) {
                String artist = result.getString("artist");
                String title = result.getString("title");
                int duration = result.getInt("length");
                song = Optional.of(new Song(artist, title, duration));
            }
            return song;
        }
    }
}