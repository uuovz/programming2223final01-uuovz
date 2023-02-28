package edu.kit.informatik.queensfarming.entity;

import edu.kit.informatik.queensfarming.util.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Game tile board.
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
     * Instantiates a new Game tile board.
     *
     * @param initalGold the inital gold
     */
    public GameTileBoard(int initalGold) {
        this.barn = new Barn(
            new Coordinates(Coordinates.ORIGIN_COORDINATES, Coordinates.ORIGIN_COORDINATES), initalGold
        );
        this.farmlandsTiles.add(new Farmland(
            new Coordinates(this.minXCoordinate, Coordinates.ORIGIN_COORDINATES), FarmlandType.GARDEN));
        this.farmlandsTiles.add(new Farmland(
            new Coordinates(this.maxXCoordinate, Coordinates.ORIGIN_COORDINATES), FarmlandType.GARDEN));
        this.farmlandsTiles.add(new Farmland(
            new Coordinates(Coordinates.ORIGIN_COORDINATES, this.maxYCoordinate), FarmlandType.FIELD));
    }

    /**
     * Gets game tiles.
     *
     * @return the game tiles
     */
    public List<GameTile> getGameTiles() {
        final List<GameTile> gameTiles = new ArrayList<>();
        gameTiles.add(barn);
        gameTiles.addAll(farmlandsTiles);
        return gameTiles;
    }

    /**
     * Gets game tile.
     *
     * @param coordinates the coordinates
     * @return the game tile
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
     * Gets farmland.
     *
     * @param coordinates the coordinates
     * @return the farmland
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
     * Gets barn.
     *
     * @return the barn
     */
    public Barn getBarn() {
        return this.barn;

    }

    /**
     * Add farmland.
     *
     * @param farmland the farmland
     */
    public void addFarmland(Farmland farmland) {
        this.farmlandsTiles.add(farmland);
        int xCoordinate = farmland.getCoordinates().getXCoordinate();
        int yCoordinate = farmland.getCoordinates().getYCoordinate();
        maxXCoordinate = Math.max(maxXCoordinate, xCoordinate);
        minXCoordinate = Math.min(minXCoordinate, xCoordinate);
        maxYCoordinate = Math.max(maxYCoordinate, yCoordinate);
    }

    @Override
    public void nextRound() {
        this.barn.nextRound();
        for (Farmland farmland: this.farmlandsTiles) {
            farmland.nextRound();
        }
    }

    /**
     * Gets spoiled vegetable amount.
     *
     * @return the spoiled vegetable amount
     */
    public int getSpoiledVegetableAmount() {
        return this.barn.getSpoiledVegetables();
    }

    /**
     * Gets grown vegetable amount.
     *
     * @return the grown vegetable amount
     */
    public int getGrownVegetableAmount() {
        int sum = 0;
        for (Farmland farmland: this.farmlandsTiles) {
            sum += farmland.getGrownVegetables();
        }
        return sum;
    }

    /**
     * Gets max x coordinate.
     *
     * @return the max x coordinate
     */
    public int getMaxXCoordinate() { return this.maxXCoordinate; }

    /**
     * Gets min x coordinate.
     *
     * @return the min x coordinate
     */
    public int getMinXCoordinate() { return this.minXCoordinate; }

    /**
     * Gets max y coordinate.
     *
     * @return the max y coordinate
     */
    public int getMaxYCoordinate() { return this.maxYCoordinate; }

    /**
     * Gets rate.
     *
     * @param coordinates the coordinates
     * @return the rate
     */
    public static int getRate(Coordinates coordinates) {
        return 10 * ((Math.abs(coordinates.getXCoordinate()) + Math.abs(coordinates.getYCoordinate())) - 1);
    }

}
