package pl.umcs.oop;

import org.knowm.xchart.CategoryChart;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Main class is the entry point of the application.
 * It contains the main method which demonstrates the usage of the ImageProcessor class.
 */
public class Main {
    /**
     * The main method of the application.
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        // Create an instance of ImageProcessor
        ImageProcessor ip = new ImageProcessor();

        // Uncomment the following block to test the performance of increasing brightness with and without thread pool
        /*
        try {
            long start1 = System.currentTimeMillis();
            ip.loadImage("car.jpg");
            ip.increaseBrightness(100);
            ip.saveImageJpg("carB.jpg");
            long end1 = System.currentTimeMillis();

            long start2 = System.currentTimeMillis();
            ip.loadImage("car.jpg");
            ip.increaseBrightnessThreadsPool(100);
            ip.saveImageJpg("carB.jpg");
            long end2 = System.currentTimeMillis();
            System.out.println(end1 - start1);
            System.out.println(end2 - start2);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        */

        // Load an image and calculate its red color histogram
        try {
            ip.loadImage("car.jpg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int[] arr = ip.calculateHistogram("red");
        System.out.println(Arrays.toString(arr));

        // Generate an overlapped histogram of the image
        CategoryChart example = ip.generateOverlappedHistogram();
    }
}