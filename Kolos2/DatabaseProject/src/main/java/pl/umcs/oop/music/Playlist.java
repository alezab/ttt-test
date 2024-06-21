package pl.umcs.oop.music;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Represents a playlist of songs.
 * A playlist is a list of songs that extends ArrayList<Song>.
 */
public class Playlist extends ArrayList<Song> {

    /**
     * Returns the song that is playing at the given second in the playlist.
     * The method iterates over the songs in the playlist, subtracting the duration of each song from the given seconds until it becomes less than or equal to zero.
     * The song at which the seconds become less than or equal to zero is the song that is playing at the given second.
     * @param seconds the second in the playlist
     * @return the song that is playing at the given second
     * @throws IndexOutOfBoundsException if the given second is negative or if there are not enough songs in the playlist to reach the given second
     */
    public Song atSecond(int seconds) {
        if(seconds < 0) {
            throw new IndexOutOfBoundsException("negative number");
        }

        for(Song song : this){
            seconds = seconds - song.duration();
            if(seconds <= 0){
                return song;
            }
        }
        throw new IndexOutOfBoundsException("out of songs");
    }
}