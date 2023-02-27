package edu.kit.informatik.queensfarming.util;

public class Coordinates implements Comparable<Coordinates> {

    public static final int ORIGIN_COORDINATES = 0;
    public static final Coordinates ORIGIN = new Coordinates(0, 0);
    private int xCoordinate;
    private int yCoordinate;

    public Coordinates() { }
    public Coordinates(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getXCoordinate() {
        return this.xCoordinate;
    }

    public int getYCoordinate() {
        return this.yCoordinate;
    }


    @Override
    public int compareTo(Coordinates coordinates) {
        return getDistance(coordinates);
    }

    private int getDistance(Coordinates coordinates) {
        return Math.abs(this.xCoordinate - coordinates.xCoordinate)
            + Math.abs(this.yCoordinate - coordinates.yCoordinate);
    }

}
