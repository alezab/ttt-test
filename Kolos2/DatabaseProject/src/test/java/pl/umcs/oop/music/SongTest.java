package pl.umcs.oop.music;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import pl.umcs.oop.database.DatabaseConnection;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the functionality of the Song class.
 */
class SongTest {

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
     * This test checks if a song with a correct index can be read from the database.
     * @throws SQLException if there is an error with the database operation
     */
    @Test
    public void testCorrectIndex() throws SQLException {
        Optional<Song> song = Song.Persistence.read(3);
        assertEquals("Stairway to Heaven", song.get().title());
    }

    /**
     * This test checks if a song with an incorrect index can be read from the database.
     * @throws SQLException if there is an error with the database operation
     */
    @Test
    public void testIncorrectIndex() throws SQLException {
        Optional<Song> song = Song.Persistence.read(100);
        assertTrue(song.isEmpty());
    }

    /**
     * This method provides a stream of arguments for the parameterized test.
     * @return a stream of arguments
     */
    private static Stream<Arguments> songs() {
        return Stream.of(
                Arguments.arguments(1,"The Beatles","Hey Jude",431),
                Arguments.arguments(3,"Led Zeppelin","Stairway to Heaven",482),
                Arguments.arguments(7,"The Doors","Light My Fire",426)
        );
    }

    /**
     * This test checks if a song with a given index can be read from the database using a stream of arguments.
     * @param index the index of the song in the database
     * @param artist the artist of the song
     * @param title the title of the song
     * @param length the length of the song
     * @throws SQLException if there is an error with the database operation
     */
    @ParameterizedTest
    @MethodSource("songs")
    public void streamTest(int index, String artist, String title, int length) throws SQLException {
        Optional<Song> song = Song.Persistence.read(index);
        assertEquals(title, song.get().title());
    }

    /**
     * This test checks if a song with a given index can be read from the database using a CSV file.
     * @param index the index of the song in the database
     * @param artist the artist of the song
     * @param title the title of the song
     * @param length the length of the song
     * @throws SQLException if there is an error with the database operation
     */
    @ParameterizedTest
    @CsvFileSource(files = "songs.csv", numLinesToSkip = 1)
    public void csvTest(int index, String artist, String title, int length) throws SQLException {
        Optional<Song> song = Song.Persistence.read(index);
        assertEquals(title, song.get().title());
    }
}