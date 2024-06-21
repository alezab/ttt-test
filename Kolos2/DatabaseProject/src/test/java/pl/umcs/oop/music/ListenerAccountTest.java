package pl.umcs.oop.music;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.umcs.oop.auth.Account;
import pl.umcs.oop.database.DatabaseConnection;

import javax.naming.AuthenticationException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the functionality of the ListenerAccount class.
 */
class ListenerAccountTest {

    /**
     * This method sets up the database connection before all tests.
     */
    @BeforeAll
    public static void setup() {
        DatabaseConnection.connect("songs.db");
    }

    /**
     * This method disconnects the database connection after all tests.
     */
    @AfterAll
    public static void close() {
        DatabaseConnection.disconnect();
    }

    /**
     * This test checks if a new account can be registered.
     * @throws SQLException if there is an error with the database operation
     */
    @Test
    public void testNewAccountRegister() throws SQLException {
        ListenerAccount.Persistence.init();
        int accountId = ListenerAccount.Persistence.register("username", "password");
        assertTrue(accountId > 0);
    }

    /**
     * This test checks if a new account can be authenticated.
     * @throws SQLException if there is an error with the database operation
     * @throws AuthenticationException if the username or password is incorrect
     */
    @Test
    public void testNewAccountLogin() throws SQLException, AuthenticationException {
        Account la = Account.Persistence.authenticate("username", "password");
        assertNotNull(la);
    }

    // The test method below is incomplete and needs to be finished.
//    @Test
//    public void testAccountBegin
}