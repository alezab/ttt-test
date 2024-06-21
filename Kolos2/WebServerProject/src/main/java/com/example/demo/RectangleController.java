package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The RectangleController class is a REST controller that handles requests related to rectangles.
 * It provides endpoints for adding, retrieving, updating, and deleting rectangles, as well as for generating SVG representations of rectangles.
 */
@RestController
public class RectangleController {
    private List<Rectangle> rectangles = new ArrayList<>();

    /**
     * Adds a rectangle to the list of rectangles.
     * @param rectangle The rectangle to add.
     * @return The new size of the list of rectangles.
     */
    @PostMapping("addRectangle")
    public int addRectangle(@RequestBody Rectangle rectangle) {
        rectangles.add(rectangle);
        return rectangles.size();
    }

    /**
     * Returns a new rectangle with specific position, dimensions, and color.
     * @return The new rectangle.
     */
    @GetMapping("rectangle")
    public Rectangle getRectangle() {
        return new Rectangle(20, 10, 100, 213, "red");
    }

    /**
     * Returns the list of rectangles.
     * @return The list of rectangles.
     */
    @GetMapping("rectangles")
    public List<Rectangle> getRectangles() {
        return this.rectangles;
    }

    /**
     * Returns the rectangle at the specified index in the list of rectangles.
     * @param index The index of the rectangle to return.
     * @return The rectangle at the specified index.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    @GetMapping("GET/{index}")
    public Rectangle GET(@PathVariable Long index) {
        if(index < 0 || index > rectangles.size()) {
            throw new IndexOutOfBoundsException();
        }
        return rectangles.get(index.intValue());
    }

    /**
     * Replaces the rectangle at the specified index in the list of rectangles with the specified rectangle.
     * @param rectangle The rectangle to place at the specified index.
     * @param index The index at which to place the rectangle.
     * @return The new size of the list of rectangles.
     */
    @PutMapping("PUT")
    public int putRectangle(@RequestBody Rectangle rectangle, @RequestParam int index) {
        rectangles.set(index, rectangle);
        return rectangles.size();
    }

    /**
     * Removes the rectangle at the specified index in the list of rectangles.
     * @param index The index of the rectangle to remove.
     * @return The new size of the list of rectangles.
     */
    @DeleteMapping("DELETE")
    public int deleteRectangle(@RequestParam int index) {
        rectangles.remove(index);
        return rectangles.size();
    }

    /**
     * Returns the vertices of the specified rectangle.
     * @param rectangle The rectangle whose vertices to return.
     * @return The vertices of the rectangle.
     */
    public static Point[] getVertices(Rectangle rectangle) {
        Point[] vertices = new Point[4];

        int halfWidth = rectangle.getWidth() / 2;
        int halfHeight = rectangle.getHeight() / 2;

        vertices[0] = new Point((rectangle.getX() - halfWidth), (rectangle.getY() - halfHeight)); // Top left
        vertices[1] = new Point((rectangle.getX() + halfWidth), (rectangle.getY() - halfHeight)); // Top right
        vertices[2] = new Point((rectangle.getX() + halfWidth), (rectangle.getY() + halfHeight)); // Bottom right
        vertices[3] = new Point((rectangle.getX() - halfWidth), (rectangle.getY() + halfHeight)); // Bottom left

        return vertices;
    }

    /**
     * Returns an SVG representation of the specified rectangle.
     * @param rectangle The rectangle to represent as SVG.
     * @return The SVG representation of the rectangle.
     */
    private static String toSvg(Rectangle rectangle) {
        String color = rectangle.getColor();
        Point[] vertices = getVertices(rectangle);
        StringBuilder values = new StringBuilder();
        for(Point p : vertices) {
            values.append(p.x).append(",").append(p.y).append(" ");
        }
        return String.format(Locale.ENGLISH, "<polygon points=\"%s\" style=\"fill:%s;stroke:black;stroke-width:3\" />", values.toString(), color);
    }

    /**
     * Returns an SVG representation of all rectangles in the list of rectangles.
     * @return The SVG representation of all rectangles.
     */
    @GetMapping("rectanglesSVG")
    public String toSVG() {
        StringBuilder result = new StringBuilder();
        result.append("<svg width=\"800\" height=\"600\" xmlns=\"http://www.w3.org/2000/svg\">");
        for (Rectangle rectangle : rectangles) {
            result.append(toSvg(rectangle));
        }
        result.append("</svg>");
        return result.toString();
    }

}