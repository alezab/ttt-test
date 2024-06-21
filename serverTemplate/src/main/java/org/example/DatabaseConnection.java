package org.example;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class provides a template for database connections.
 * Connections are private and should be established using the connect method before calling getConnection.
 */
public class DatabaseConnection {

    // A map to store database connections, with the connection name as the key and the Connection object as the value.
    static private final Map<String, Connection> connections = new HashMap<>();

    /**
     * Get a database connection with the default name.
     * @return Connection object.
     */
    static public Connection getConnection() {
        return getConnection("");
    }

    /**
     * Get a database connection with a specific name.
     * @param name The name of the connection.
     * @return Connection object.
     */
    static public Connection getConnection(String name) {
        return connections.get(name);
    }

    /**
     * Connect to a database with the default name.
     * @param filePath The path to the database file.
     */
    static public void connect(String filePath) {
        connect(filePath, "");
    }

    /**
     * Connect to a database with a specific name.
     * @param filePath The path to the database file.
     * @param connectionName The name of the connection.
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
     * Disconnect from a database with the default name.
     */
    static public void disconnect() {
        disconnect("");
    }

    /**
     * Disconnect from a database with a specific name.
     * @param connectionName The name of the connection.
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