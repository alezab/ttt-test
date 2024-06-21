package com.example.demo;

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
 * The ImageProcessor class provides methods for image processing.
 * It includes methods for loading and saving images, increasing brightness, calculating histograms, and generating overlapped histograms.
 */
public class ImageProcessor {
    private BufferedImage image;

    /**
     * Sets the image to be processed.
     * @param image The image to be processed.
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Returns the processed image.
     * @return The processed image.
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Loads an image from a file.
     * @param path The path of the image file.
     * @throws IOException If an error occurs during loading.
     */
    public void loadImage(String path) throws IOException {
        File file = new File(path);
        image = ImageIO.read(file);
    }

    /**
     * Saves the processed image as a JPG file.
     * @param path The path of the output file.
     * @throws IOException If an error occurs during saving.
     */
    public void saveImageJpg(String path) throws IOException {
        File file = new File(path);
        ImageIO.write(image, "jpg", file);
    }

    /**
     * Saves the processed image as a PNG file.
     * @param path The path of the output file.
     * @throws IOException If an error occurs during saving.
     */
    public void saveImagePng(String path) throws IOException {
        File file = new File(path);
        ImageIO.write(image, "png", file);
    }

    /**
     * Increases the brightness of the image.
     * @param factor The factor by which to increase the brightness.
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
     * Increases the brightness of the image using multiple threads.
     * @param factor The factor by which to increase the brightness.
     * @throws InterruptedException If the thread execution is interrupted.
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
     * Increases the brightness of the image using a thread pool.
     * @param factor The factor by which to increase the brightness.
     * @throws InterruptedException If the thread execution is interrupted.
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
     * Calculates the histogram of the image for a specific color.
     * @param color The color for which to calculate the histogram ("red", "green", or "blue").
     * @return The histogram as an array of integers.
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
     * Brightens a pixel by a specific factor.
     * @param pixel The pixel to brighten.
     * @param factor The factor by which to brighten the pixel.
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
     * Brightens a part of a pixel by a specific factor.
     * @param color The part of the pixel to brighten.
     * @param factor The factor by which to brighten the part of the pixel.
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
     * Generates an overlapped histogram for the image.
     * @return The overlapped histogram as a CategoryChart.
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
     * @param length The length of the list.
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