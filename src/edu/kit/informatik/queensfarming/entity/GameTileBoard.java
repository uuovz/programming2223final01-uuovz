package edu.kit.informatik.queensfarming.entity;

import edu.kit.informatik.queensfarming.util.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameTileBoard class represents a board of GameTile objects, including Farmland and Barn tiles.
 * It also implements the Roundable interface, allowing the board to be updated with each round of the game.
 *
 * @author uuovz
 * @version 1.0
 */
public class GameTileBoard implements Roundable {
    private final List<Farmland> farmlandsTiles = new ArrayList<>();
    private final Barn barn;
    private int maxXCoordinate = 1;
    private int minXCoordinate = -1;
    private int maxYCoordinate = 1;


    /**
     * Constructs a new GameTileBoard object with the given initial gold amount.
     * The board includes a Barn tile and 3 Farmland tiles, with the Barn tile at the origin.
     *
     * @param initialGold the initial gold amount
     */
    public GameTileBoard(int initialGold) {
        this.barn = new Barn(
            new Coordinates(Coordinates.ORIGIN_COORDINATES, Coordinates.ORIGIN_COORDINATES), initialGold
        );
        this.farmlandsTiles.add(new Farmland(
            new Coordinates(this.minXCoordinate, Coordinates.ORIGIN_COORDINATES), FarmlandType.GARDEN));
        this.farmlandsTiles.add(new Farmland(
            new Coordinates(this.maxXCoordinate, Coordinates.ORIGIN_COORDINATES), FarmlandType.GARDEN));
        this.farmlandsTiles.add(new Farmland(
            new Coordinates(Coordinates.ORIGIN_COORDINATES, this.maxYCoordinate), FarmlandType.FIELD));
    }

    /**
     * Returns a list of all GameTile objects on the board, including the Barn and Farmland tiles.
     *
     * @return a list of all GameTile objects on the board
     */
    public List<GameTile> getGameTiles() {
        final List<GameTile> gameTiles = new ArrayList<>();
        gameTiles.add(barn);
        gameTiles.addAll(farmlandsTiles);
        return gameTiles;
    }

    /**
     * Returns the GameTile object at the given coordinates on the board.
     *
     * @param coordinates the coordinates of the desired GameTile object
     * @return the GameTile object at the given coordinates, or null if no GameTile object exists at those coordinates
     */
    public GameTile getGameTile(Coordinates coordinates) {
        if (coordinates.compareTo(Coordinates.ORIGIN) == 0) {
            return this.barn;
        }
        for (GameTile gameTile: this.farmlandsTiles) {
            if (gameTile.getCoordinates().compareTo(coordinates) == 0) {
                return gameTile;
            }
        }
        return null;
    }

    /**
     * Returns the Farmland tile at the given coordinates on the board.
     *
     * @param coordinates the coordinates of the desired Farmland tile
     * @return the Farmland tile at the given coordinates, or null if no Farmland tile exists at those coordinates
     */
    public Farmland getFarmland(Coordinates coordinates) {
        for (Farmland farmland: farmlandsTiles) {
            if (farmland.getCoordinates().compareTo(coordinates) == 0) {
                return farmland;
            }
        }
        return null;
    }

    /**
     * This method returns the Barn object in the farm.
     *
     * @return the Barn object in the farm.
     */
    public Barn getBarn() {
        return this.barn;

    }

    /**
     * This method adds a Farmland object to the farm and updates the maximum and minimum x and y coordinates.
     *
     * @param farmland the Farmland object to be added to the farm.
     */
    public void addFarmland(Farmland farmland) {
        this.farmlandsTiles.add(farmland);
        int xCoordinate = farmland.getCoordinates().getXCoordinate();
        int yCoordinate = farmland.getCoordinates().getYCoordinate();
        maxXCoordinate = Math.max(maxXCoordinate, xCoordinate);
        minXCoordinate = Math.min(minXCoordinate, xCoordinate);
        maxYCoordinate = Math.max(maxYCoordinate, yCoordinate);
    }

    /**
     * This method calls the nextRound() method of the Barn object and each Farmland object in the farm.
     */
    @Override
    public void nextRound() {
        this.barn.nextRound();
        for (Farmland farmland: this.farmlandsTiles) {
            farmland.nextRound();
        }
    }

    /**
     * This method returns the number of spoiled vegetables in the Barn object.
     *
     * @return the number of spoiled vegetables in the Barn object.
     */
    public int getSpoiledVegetableAmount() {
        return this.barn.getSpoiledVegetables();
    }

    /**
     * This method returns the total number of grown vegetables in all Farmland objects in the farm.
     *
     * @return the total number of grown vegetables in all Farmland objects in the farm.
     */
    public int getGrownVegetableAmount() {
        int sum = 0;
        for (Farmland farmland: this.farmlandsTiles) {
            sum += farmland.getGrownVegetables();
        }
        return sum;
    }

    /**
     * This method returns the maximum x-coordinate of all Farmland objects in the farm.
     *
     * @return the maximum x-coordinate of all Farmland objects in the farm.
     */
    public int getMaxXCoordinate() { return this.maxXCoordinate; }

    /**
     * This method returns the minimum x-coordinate of all Farmland objects in the farm.
     *
     * @return the minimum x-coordinate of all Farmland objects in the farm.
     */
    public int getMinXCoordinate() { return this.minXCoordinate; }

    /**
     * This method returns the maximum y-coordinate of all Farmland objects in the farm.
     *
     * @return the maximum y-coordinate of all Farmland objects in the farm.
     */
    public int getMaxYCoordinate() { return this.maxYCoordinate; }

    /**
     * This method returns the rate of a Farmland object based on its coordinates.
     *
     * @param coordinates the coordinates of the Farmland object to calculate the rate for.
     * @return the rate of the Farmland object.
     */
    public static int getRate(Coordinates coordinates) {
        return 10 * ((Math.abs(coordinates.getXCoordinate()) + Math.abs(coordinates.getYCoordinate())) - 1);
    }

}
