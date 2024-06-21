package pl.umcs.oop.database;

import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class handles the database connections for the application.
 */
public class DatabaseConnection {

    /**
     * A map to store the database connections.
     */
    static private final Map<String, Connection> connections = new HashMap<>();

    /**
     * Returns the default database connection.
     * @return the default database connection
     */
    static public Connection getConnection() {
        return getConnection("");
    }

    /**
     * Returns the database connection with the given name.
     * @param name the name of the database connection
     * @return the database connection with the given name
     */
    static public Connection getConnection(String name) {
        return connections.get(name);
    }

    /**
     * Connects to the database at the given file path.
     * @param filePath the file path of the database
     */
    static public void connect(String filePath) {
        connect(filePath, "");
    }

    /**
     * Connects to the database at the given file path and stores the connection with the given name.
     * @param filePath the file path of the database
     * @param connectionName the name of the database connection
     */
    static public void connect(String filePath, String connectionName){
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            connections.put(connectionName, connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Disconnects the default database connection.
     */
    static public void disconnect() {
        disconnect("");
    }

    /**
     * Disconnects the database connection with the given name.
     * @param connectionName the name of the database connection
     */
    static public void disconnect(String connectionName){
        try {
            Connection connection = connections.get(connectionName);
            connection.close();
            connections.remove(connectionName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}