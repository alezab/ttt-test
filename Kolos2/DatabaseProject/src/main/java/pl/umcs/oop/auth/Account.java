package pl.umcs.oop.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import pl.umcs.oop.database.DatabaseConnection;

import javax.naming.AuthenticationException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents an account in the system.
 */
public class Account {
    protected final int id;
    protected final String username;

    /**
     * Constructs an Account object with the given id and username.
     * @param id the id of the account
     * @param username the username of the account
     */
    public Account(int id, String username) {
        this.id = id;
        this.username = username;
    }

    /**
     * Returns the id of the account.
     * @return the id of the account
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the username of the account.
     * @return the username of the account
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns a string representation of the account.
     * @return a string representation of the account
     */
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

    /**
     * Represents the persistence layer of the Account class.
     */
    public static class Persistence {
        /**
         * Initializes the persistence layer by creating the necessary database table.
         */
        public static void init() {
            try {
                String createSQLTable = "CREATE TABLE IF NOT EXISTS account( " +
                        "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT NOT NULL," +
                        "password TEXT NOT NULL)";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(createSQLTable);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Registers a new account with the given username and password.
         * @param username the username of the new account
         * @param password the password of the new account
         * @return the id of the newly created account
         */
        public static int register(String username, String password) {
            String hashedPassword =  BCrypt.withDefaults().hashToString(12, password.toCharArray());
            try {
                String insertSQL = "INSERT INTO account(username, password) VALUES (?, ?)";
                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(insertSQL);

                statement.setString(1, username);
                statement.setString(2, hashedPassword);
                statement.executeUpdate();

                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next())
                    return resultSet.getInt(1);
                else throw new SQLException();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Authenticates a user with the given username and password.
         * @param username the username of the account
         * @param password the password of the account
         * @return the authenticated Account object
         * @throws AuthenticationException if the username or password is incorrect
         */
        public static Account authenticate(String username, String password) throws AuthenticationException {
            try {
                String sql = "SELECT id, username, password FROM account WHERE username = ?";

                PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                statement.setString(1, username);

                if (!statement.execute()) throw new RuntimeException("SELECT failed");

                ResultSet result = statement.getResultSet();

                if (!result.next()) {
                    throw new AuthenticationException("No such user");
                }
                String hashedPassword = result.getString(3);
                boolean okay = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword.toCharArray()).verified;

                if (!okay) {
                    throw new AuthenticationException("Wrong password");
                }

                return new Account(
                        result.getInt(1),
                        result.getString(2)
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}