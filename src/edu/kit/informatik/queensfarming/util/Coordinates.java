package edu.kit.informatik.queensfarming.util;

/**
 * The type Coordinates.
 *
 * @author uuovz
 * @version 1.0
 */
public class Coordinates implements Comparable<Coordinates> {

    /**
     * The constant ORIGIN_COORDINATES.
     */
    public static final int ORIGIN_COORDINATES = 0;
    /**
     * The constant ORIGIN.
     */
    public static final Coordinates ORIGIN = new Coordinates(0, 0);
    private int xCoordinate;
    private int yCoordinate;

    /**
     * Instantiates a new Coordinates.
     */
    public Coordinates() { }

    /**
     * Instantiates a new Coordinates.
     *
     * @param xCoordinate the x coordinate
     * @param yCoordinate the y coordinate
     */
    public Coordinates(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * Gets x coordinate.
     *
     * @return the x coordinate
     */
    public int getXCoordinate() {
        return this.xCoordinate;
    }

    /**
     * Gets y coordinate.
     *
     * @return the y coordinate
     */
    public int getYCoordinate() {
        return this.yCoordinate;
    }

    /**
     *
     *
     * @return int distance
     */
    @Override
    public int compareTo(Coordinates coordinates) {
        return getDistance(coordinates);
    }

    private int getDistance(Coordinates coordinates) {
        return Math.abs(this.xCoordinate - coordinates.xCoordinate)
            + Math.abs(this.yCoordinate - coordinates.yCoordinate);
    }

}
