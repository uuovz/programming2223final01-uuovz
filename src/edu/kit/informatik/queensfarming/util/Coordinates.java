package edu.kit.informatik.queensfarming.util;

import java.util.Objects;

/**
 * The Coordinates class represents a point in a 2D coordinate system.
 * It implements the Comparable interface to allow sorting and
 * comparison of coordinates based on their distance from the origin.
 * It also overrides the equals and hashCode methods for equality comparisons.
 * @author uuovz
 * @version 1.0
 */
public class Coordinates implements Comparable<Coordinates> {

    /**
     * Constant value for the origin coordinates.
     */
    public static final int ORIGIN_COORDINATES = 0;
    /**
     * The origin coordinates (0,0).
     */
    public static final Coordinates ORIGIN = new Coordinates(0, 0);
    private int xCoordinate;
    private int yCoordinate;

    /**
     * Constructs a new Coordinates object with default (0,0) coordinates.
     */
    public Coordinates() { }

    /**
     * Constructs a new Coordinates object with the specified x and y coordinates.
     * @param xCoordinate the x coordinate
     * @param yCoordinate the y coordinate
     */
    public Coordinates(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * Returns the x coordinate of this Coordinates object.
     * @return the x coordinate
     */
    public int getXCoordinate() {
        return this.xCoordinate;
    }

    /**
     * Returns the y coordinate of this Coordinates object.
     * @return the y coordinate
     */
    public int getYCoordinate() {
        return this.yCoordinate;
    }

    /**
     * Compares this Coordinates object to another Coordinates object based on their distance from the origin.
     * @param coordinates the Coordinates object to compare to
     * @return a positive integer if this Coordinates object is farther from the origin
     * than the specified Coordinates object,
     */
    @Override
    public int compareTo(Coordinates coordinates) {
        return getDistance(coordinates);
    }

    private int getDistance(Coordinates coordinates) {
        return Math.abs(this.xCoordinate - coordinates.xCoordinate)
            + Math.abs(this.yCoordinate - coordinates.yCoordinate);
    }

    /**
     * Returns true if this Coordinates object is equal to the specified object, false otherwise.
     * Two Coordinates objects are considered equal if their x and y coordinates are the same.
     * @param obj the object to compare to
     * @return true if this Coordinates object is equal to the specified object, false
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Coordinates && compareTo((Coordinates) obj) == 0;
    }

    /**
     * Returns the hash code value for this Coordinates object.
     * @return the hash code value for this Coordinates object
     */
    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate);
    }
}
