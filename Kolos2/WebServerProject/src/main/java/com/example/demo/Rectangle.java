package com.example.demo;

/**
 * The Rectangle class represents a rectangle with a specific position, dimensions, and color.
 */
public class Rectangle {
    private int x, y, width, height;
    private String color;

    /**
     * Constructs a new Rectangle with the specified position, dimensions, and color.
     *
     * @param x The x-coordinate of the rectangle.
     * @param y The y-coordinate of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param color The color of the rectangle.
     */
    public Rectangle(int x, int y, int width, int height, String color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     * Returns the x-coordinate of the rectangle.
     *
     * @return The x-coordinate of the rectangle.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return The width of the rectangle.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the y-coordinate of the rectangle.
     *
     * @return The y-coordinate of the rectangle.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return The height of the rectangle.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the color of the rectangle.
     *
     * @return The color of the rectangle.
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the x-coordinate of the rectangle.
     *
     * @param x The new x-coordinate of the rectangle.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the rectangle.
     *
     * @param y The new y-coordinate of the rectangle.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets the width of the rectangle.
     *
     * @param width The new width of the rectangle.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Sets the height of the rectangle.
     *
     * @param height The new height of the rectangle.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets the color of the rectangle.
     *
     * @param color The new color of the rectangle.
     */
    public void setColor(String color) {
        this.color = color;
    }

}