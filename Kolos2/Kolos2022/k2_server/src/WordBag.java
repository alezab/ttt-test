import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * WordBag class is used to store and manage a collection of words.
 * It provides methods to populate the collection from a file and to get a random word from the collection.
 */
public class WordBag {
    private List<String> words = new ArrayList<>();
    private Random rand = new Random();

    /**
     * Populates the collection of words from a file.
     * The file is assumed to be in the same directory and named "slowa.txt".
     * Each line in the file is considered a separate word.
     */
    public void populate() {
        String path = "slowa.txt";
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            words = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a random word from the collection.
     * @return A random word from the collection.
     */
    public String get() {
        return words.get(rand.nextInt(words.size()));
    }
}