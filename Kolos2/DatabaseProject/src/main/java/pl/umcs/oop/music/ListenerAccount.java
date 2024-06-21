package pl.umcs.oop.music;

import pl.umcs.oop.auth.Account;
import pl.umcs.oop.database.DatabaseConnection;

import javax.naming.AuthenticationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import pl.umcs.oop.music.ListenerAccount.Persistence.*;

import static pl.umcs.oop.music.ListenerAccount.Persistence.hasSong;

/**
 * Represents a listener account in the music system.
 * A listener account extends the basic account and has additional features like creating playlists.
 */
public class ListenerAccount extends Account {
    /**
     * Constructs a ListenerAccount object with the given id and name.
     * @param id the id of the account
     * @param name the name of the account
     */
    public ListenerAccount(int id, String name) {
        super(id, name);
    }

    /**
     * Creates a playlist with the given song IDs.
     * @param songIds the IDs of the songs to be added to the playlist
     * @return the created playlist
     * @throws SQLException if there is an error with the database operation
     */
    public Playlist createPlaylist(List<Integer> songIds) throws SQLException {
        Playlist playlist = new Playlist();
        for(var id: songIds) {
            if(hasSong(super.id, id)) {
                Persistence.addSong(super.id, id);
            }
            var optionalSong = Song.Persistence.read(id);
            if(optionalSong.isPresent())
                playlist.add(optionalSong.get());
            else
                throw new SQLException();
        }
        return playlist;
    }

    /**
     * Represents the persistence layer of the ListenerAccount class.
     */
    public static class Persistence {
        /**
         * Initializes the persistence layer by creating the necessary database tables.
         * @throws SQLException if there is an error with the database operation
         */
        public static void init() throws SQLException {
            Account.Persistence.init();
            {
                String sql = "CREATE TABLE IF NOT EXISTS listener_account( " +
                        "id_account INTEGER NOT NULL PRIMARY KEY," +
                        "credits INTEGER NOT NULL)";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.executeUpdate();
            }
            {
                String sql = "CREATE TABLE IF NOT EXISTS owned_songs( " +
                        "id_account INTEGER NOT NULL," +
                        "id_song INTEGER NOT NULL," +
                        "PRIMARY KEY (id_account, id_song))";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.executeUpdate();
            }
        }

        /**
         * Registers a new listener account with the given username and password.
         * @param username the username of the new account
         * @param password the password of the new account
         * @return the id of the newly created account
         * @throws SQLException if there is an error with the database operation
         */
        public static int register(String username, String password) throws SQLException{
            try {
                int id = Account.Persistence.register(username, password);
                String sql = "INSERT INTO listener_account(id_account, credits) VALUES (?, 0)";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.setInt(1,id);
                statement.executeUpdate();
                return id;
            } catch (SQLException | RuntimeException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Adds a song to the owned songs of the account.
         * @param accountId the id of the account
         * @param songId the id of the song
         * @throws SQLException if there is an error with the database operation
         */
        public static void addSong(int accountId, int songId) throws SQLException {
            String sql = "INSERT INTO owned_songs VALUES(?, ?)";
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1, accountId);
            statement.setInt(2, songId);
            statement.executeUpdate();
        }

        /**
         * Checks if the account owns the song with the given id.
         * @param accountId the id of the account
         * @param songId the id of the song
         * @return true if the account owns the song, false otherwise
         * @throws SQLException if there is an error with the database operation
         */
        public static boolean hasSong(int accountId, int songId) throws SQLException {
            String sql = "SELECT * FROM owned_songs WHERE id_account = ? AND id_song = ?";
            PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
            statement.setInt(1, accountId);
            statement.setInt(2, songId);
            return statement.executeQuery().next();
        }

        /**
         * Authenticates a listener account with the given username and password.
         * @param username the username of the account
         * @param password the password of the account
         * @return the authenticated ListenerAccount object
         * @throws AuthenticationException if the username or password is incorrect
         */
        static ListenerAccount authenticate(String username, String password) throws AuthenticationException {
            Account account = Account.Persistence.authenticate(username, password);
            return new ListenerAccount(account.getId(), account.getUsername());
        }

    }
}