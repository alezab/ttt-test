package pl.umcs.oop.music;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the functionality of the Playlist class.
 */
class PlaylistTest {

    /**
     * This test checks if a new playlist is empty.
     */
    @Test
    public void testIfNewPlaylistIsEmpty() {
        Playlist playlist = new Playlist();

        assertTrue(playlist.isEmpty());
    }

    /**
     * This test checks if a playlist has one song after adding a song.
     */
    @Test
    public void testIfPlaylistHasOneSong(){
        Song song1 = new Song("artist","title",320);

        Playlist playlist = new Playlist();
        playlist.add(song1);

        assertEquals(playlist.size(),1);
    }

    /**
     * This test checks if a playlist contains a song after adding the song.
     */
    @Test
    public void ifHasSameSong(){
        Song song = new Song("artist","title", 10);
        Playlist playlist = new Playlist();
        playlist.add(song);

        assertTrue(playlist.contains(song));
    }

    /**
     * This test checks if the correct song is playing at a given second in the playlist.
     */
    @Test
    void atSecond() {
        Song song1 = new Song("artist1", "title1", 50);
        Song song2 = new Song("artist2", "title2", 40);
        Playlist playlist = new Playlist();
        playlist.add(song1);
        playlist.add(song2);
        int testtimestamp = 70;
        assertEquals(playlist.atSecond(testtimestamp), song2);
    }

    /**
     * This test checks if an exception is thrown when trying to get the song at a second that is beyond the total duration of the playlist.
     */
    @Test
    public void atSecondThrowException() {
        Song song1 = new Song("artist1", "title1", 50);
        Song song2 = new Song("artist2", "title2", 40);
        Playlist playlist = new Playlist();
        playlist.add(song1);
        playlist.add(song2);
        int testtimestamp = 100;
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> {
                    playlist.atSecond(testtimestamp);
                }
        );
    }

    /**
     * This test checks if an exception is thrown when trying to get the song at a negative second.
     */
    @Test
    public void atSecondThrowExceptionWhenNumberIsNegative() {
        Song song1 = new Song("artist1", "title1", 50);
        Song song2 = new Song("artist2", "title2", 40);
        Playlist playlist = new Playlist();
        playlist.add(song1);
        playlist.add(song2);
        int testtimestamp = -20;
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> {
                    playlist.atSecond(testtimestamp);
                },
                "negative number"
        );
    }
}