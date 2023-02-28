package edu.kit.informatik.queensfarming.entity;

import edu.kit.informatik.queensfarming.util.Coordinates;

/**
 * The type Game tile.
 *
 * @author uuovz
 * @version 1.0
 */
public abstract class GameTile implements Roundable {

    private Coordinates coordinates;

    /**
     * Instantiates a new Game tile.
     *
     * @param coordinates the coordinates
     */
    GameTile(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    public Coordinates getCoordinates() { return this.coordinates; }

    /**
     * Sets coordinates.
     *
     * @param coordinates the coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public abstract void nextRound();




}
