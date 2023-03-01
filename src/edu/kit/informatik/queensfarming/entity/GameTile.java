package edu.kit.informatik.queensfarming.entity;

import edu.kit.informatik.queensfarming.util.Coordinates;

/**
 * The abstract class GameTile represents a tile on the game board.
 * This class implements the Roundable interface, which allows each tile to progress
 * to the next round of the game.
 *
 * @author uuovz
 * @version 1.0
 */
public abstract class GameTile implements Roundable {

    private Coordinates coordinates;

    /**
     * Constructor for the GameTile class. Initializes a new GameTile instance
     * with the specified coordinates.
     *
     * @param coordinates the coordinates of the tile on the game board
     */
    GameTile(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Returns the coordinates of the tile on the game board.
     *
     * @return the coordinates of the tile
     */
    public Coordinates getCoordinates() { return this.coordinates; }

    /**
     * Sets the coordinates of the tile on the game board.
     *
     * @param coordinates the new coordinates of the tile
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Implements the nextRound method of the Roundable interface. Each GameTile subclass
     * should provide its own implementation of this method.
     */
    @Override
    public abstract void nextRound();
}
