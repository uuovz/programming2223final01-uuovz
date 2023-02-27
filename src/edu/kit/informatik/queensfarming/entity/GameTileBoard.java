package edu.kit.informatik.queensfarming.entity;

import edu.kit.informatik.queensfarming.util.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class GameTileBoard implements Roundable {
    private final List<Farmland> farmlandsTiles = new ArrayList<>();
    private final Barn barn;
    private int maxXCoordinate = 1;
    private int minXCoordinate = -1;
    private int maxYCoordinate = 1;


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

    public List<GameTile> getGameTiles() {
        final List<GameTile> gameTiles = new ArrayList<>();
        gameTiles.add(barn);
        gameTiles.addAll(farmlandsTiles);
        return gameTiles;
    }

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

    public Farmland getFarmland(Coordinates coordinates) {
        for (Farmland farmland: farmlandsTiles) {
            if (farmland.getCoordinates().compareTo(coordinates) == 0) {
                return farmland;
            }
        }
        return null;
    }

    public Barn getBarn() {
        return this.barn;

    }

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

    public int getSpoiledVegetableAmount() {
        return this.barn.getSpoiledVegetables();
    }

    public int getGrownVegetableAmount() {
        int sum = 0;
        for (Farmland farmland: this.farmlandsTiles) {
            sum += farmland.getGrownVegetables();
        }
        return sum;
    }

    public int getMaxXCoordinate() { return this.maxXCoordinate; }
    public int getMinXCoordinate() { return this.minXCoordinate; }
    public int getMaxYCoordinate() { return this.maxYCoordinate; }

    public static int getRate(Coordinates coordinates) {
        return 10 * ((Math.abs(coordinates.getXCoordinate()) + Math.abs(coordinates.getYCoordinate())) - 1);
    }

}
