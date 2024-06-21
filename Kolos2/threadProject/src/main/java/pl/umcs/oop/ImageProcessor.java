package pl.umcs.oop;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * This class is used to process images. It provides functionality to load, save, increase brightness,
 * calculate histogram and generate overlapped histogram of an image.
 */
public class ImageProcessor {
    private BufferedImage image;

    /**
     * Loads an image from the specified path.
     * @param path The path of the image to load.
     * @throws IOException If an error occurs during reading.
     */
    public void loadImage(String path) throws IOException {
        File file = new File(path);
        image = ImageIO.read(file);
    }

    /**
     * Saves the current image as a JPG at the specified path.
     * @param path The path to save the image to.
     * @throws IOException If an error occurs during writing.
     */
    public void saveImageJpg(String path) throws IOException {
        File file = new File(path);
        ImageIO.write(image, "jpg", file);
    }

    /**
     * Saves the current image as a PNG at the specified path.
     * @param path The path to save the image to.
     * @throws IOException If an error occurs during writing.
     */
    public void saveImagePng(String path) throws IOException {
        File file = new File(path);
        ImageIO.write(image, "png", file);
    }

    /**
     * Increases the brightness of the current image by the specified factor.
     * @param factor The factor to increase the brightness by.
     */
    public void increaseBrightness(int factor) {
        for(int x = 0 ; x < image.getWidth(); x++) {
            for(int y = 0 ; y < image.getHeight() ; y++) {
                int pixel = image.getRGB(x, y);
                pixel = brightenPixel(pixel, factor);
                image.setRGB(x, y, pixel);
            }
        }
    }

    /**
     * Increases the brightness of the current image by the specified factor using multiple threads.
     * @param factor The factor to increase the brightness by.
     * @throws InterruptedException If any thread has interrupted the current thread.
     */
    public void increaseBrightnessThreads(int factor) throws InterruptedException {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        Thread threads[] = new Thread[availableProcessors];

        for(int i = 0; i < threads.length; i++) {
            final int finalI = i;

            threads[i] = new Thread(() -> {
                int start = image.getWidth() / availableProcessors * finalI;
                int end = start + image.getWidth() / availableProcessors;
                System.out.println(finalI);
                if(finalI == availableProcessors - 1) {
                    end = image.getWidth();
                }
                for(int x = start ; x < end ; x++) {
                    for(int y = 0 ; y < image.getHeight() ; y++) {
                        int pixel = image.getRGB(x, y);
                        pixel = brightenPixel(pixel, factor);
                        image.setRGB(x, y, pixel);
                    }
                }
            });

            threads[i].start();
        }
        for(int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
    }

    /**
     * Increases the brightness of the current image by the specified factor using a thread pool.
     * @param factor The factor to increase the brightness by.
     * @throws InterruptedException If any thread has interrupted the current thread.
     */
    public void increaseBrightnessThreadsPool(int factor) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executorService.execute(() -> {
            for(int y = 0 ; y < image.getHeight() ; y++) {
                for(int x = 0 ; x < image.getWidth() ; x++) {
                    int pixel = image.getRGB(x, y);
                    pixel = brightenPixel(pixel, factor);
                    image.setRGB(x, y, pixel);
                }
            }
        });
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.HOURS);
    }

    /**
     * Calculates the histogram of the current image for the specified color.
     * @param color The color to calculate the histogram for. Can be "red", "green", or "blue".
     * @return An array representing the histogram.
     */
    public int[] calculateHistogram(String color) {
        int colorI;
        int mask = 255;
        if(color.equals("blue")) {
            colorI = 0;
        } else if (color.equals("green")) {
            colorI = 1;
        } else if (color.equals("red")) {
            colorI = 2;
        } else {
            return new int[256];
        }
        int[] result = new int[256];
        for(int i : result) {
            result[i] = 0;
        }
        for(int x = 0 ; x < image.getWidth() ; x++) {
            for(int y = 0 ; y < image.getHeight() ; y++) {
                int pixel = image.getRGB(x, y);
                int colorIndexer = (pixel >> (8 * colorI)) & mask;
                result[colorIndexer]++;
            }
        }
        return result;
    }

    /**
     * Brightens a pixel by the specified factor.
     * @param pixel The pixel to brighten.
     * @param factor The factor to brighten the pixel by.
     * @return The brightened pixel.
     */
    private int brightenPixel(int pixel, int factor) {
        int mask = 255;
        int blue = pixel & mask;
        int green = (pixel >> 8) & mask;
        int red = (pixel >> 16) & mask;
        blue = brightenPixelPart(blue, factor);
        green = brightenPixelPart(green, factor);
        red = brightenPixelPart(red, factor);
        return blue + (green << 8) + (red << 16);
    }

    /**
     * Brightens a part of a pixel by the specified factor.
     * @param color The part of the pixel to brighten.
     * @param factor The factor to brighten the part of the pixel by.
     * @return The brightened part of the pixel.
     */
    private int brightenPixelPart(int color, int factor) {
        color += factor;
        if(color > 255) {
            return 255;
        } else {
            return color;
        }
    }

    /**
     * Generates an overlapped histogram of the current image.
     * @return The generated histogram.
     */
    public CategoryChart generateOverlappedHistogram() {
        int[] redHistogramData = calculateHistogram("red");
        int[] greenHistogramData = calculateHistogram("green");
        int[] blueHistogramData = calculateHistogram("blue");

        List<Integer> redHistogramDataCollection = Arrays.stream(redHistogramData).boxed().toList();
        List<Integer> greenHistogramDataCollection = Arrays.stream(greenHistogramData).boxed().toList();
        List<Integer> blueHistogramDataCollection = Arrays.stream(blueHistogramData).boxed().toList();

        CategoryChart chart = new CategoryChartBuilder().width(800).height(300).title("Histogram").xAxisTitle("Shade").yAxisTitle("Count").build();
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setAvailableSpaceFill(.96);
        chart.getStyler().setOverlapped(true);

        List<String> xAxisLabels = createXAxisLabels(256);

        Histogram redHistogram = new Histogram(redHistogramDataCollection, 20);
        Histogram greenHistogram = new Histogram(greenHistogramDataCollection, 20);
        Histogram blueHistogram = new Histogram(blueHistogramDataCollection, 20);

        chart.addSeries("Red", createXAxisLabels(256), redHistogramDataCollection).setFillColor(new Color(255, 0, 0, 100));
        chart.addSeries("Green", createXAxisLabels(256), greenHistogramDataCollection).setFillColor(new Color(0, 255, 0, 200));
        chart.addSeries("Blue", createXAxisLabels(256), blueHistogramDataCollection).setFillColor(new Color(0, 0, 255, 150));


        new SwingWrapper<>(chart).displayChart();

        return chart;
    }

    /**
     * Creates a list of labels for the x-axis of the histogram.
     * @param length The number of labels to create.
     * @return The list of labels.
     */
    private List<String> createXAxisLabels(int length) {
        List<Integer> xAxisLabels = new ArrayList<>(length);
        for(int i = 0; i < length; i++) {
            xAxisLabels.add(i);
        }
        return xAxisLabels.stream().map(String::valueOf).collect(Collectors.toList());
    }
}