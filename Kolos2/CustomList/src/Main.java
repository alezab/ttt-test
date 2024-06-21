import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Main class that contains the main method to run the application.
 */
public class Main {
    /**
     * The main method that is the entry point of the application.
     * It creates two collections of integers, compares their sums using a comparator from the CustomList class,
     * and prints out which sum is larger, or if they are equal.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Create two collections of integers
        Collection<Integer> collection1 = Arrays.asList(1, 2, 3, 4, 5);
        Collection<Integer> collection2 = Arrays.asList(6, 7, 8, 9, 10);

        // Use the compareSums method from the CustomList class to get a comparator
        Comparator<Integer> comparator = CustomList.compareSums(collection1, collection2);

        // Use the comparator to compare the sums of the two collections
        // The compare method takes any two integers, as it is comparing the sums of the collections, not the integers themselves
        int result = comparator.compare(0, 0);

        // Print out which sum is larger, or if they are equal
        if (result < 0) {
            System.out.println("Suma pierwszej kolekcji jest mniejsza od sumy drugiej kolekcji.");
        } else if (result > 0) {
            System.out.println("Suma pierwszej kolekcji jest większa od sumy drugiej kolekcji.");
        } else {
            System.out.println("Sumy obu kolekcji są równe.");
        }
    }
}