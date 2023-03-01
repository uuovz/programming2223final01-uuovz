package edu.kit.informatik.queensfarming.entity;

import edu.kit.informatik.queensfarming.util.Coordinates;
import edu.kit.informatik.queensfarming.util.Countdown;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Barn represents a game tile of type barn in a farm game.
 * It holds a stock of vegetables and gold, and a countdown that reduces the stock of vegetables periodically.
 *
 * @author uuovz
 * @version 1.0
 */
public class Barn extends GameTile {
    private static final int COUNTDOWN_FREQUENCY = 6;
    private static final int INITIAL_VALUE = 1;
    private final Countdown countdown;
    private final Map<Vegetable, Integer> stock = new HashMap<>();
    private int spoiledVegetables = 0;
    private int goldStock;


    /**
     * Instantiates a new Barn game tile.
     *
     * @param coordinates the coordinates of the barn
     * @param initialGold the initial amount of gold the barn holds
     */
    public Barn(Coordinates coordinates, int initialGold) {
        super(coordinates);
        this.initializeValues();
        this.countdown = new Countdown(COUNTDOWN_FREQUENCY);
        this.countdown.start();
        this.goldStock = initialGold;
    }

    private void initializeValues() {
        for (Vegetable vegetable: EnumSet.allOf(Vegetable.class)) {
            this.stock.put(vegetable, INITIAL_VALUE);
        }
    }

    /**
     * Returns the stock amount of a given vegetable.
     *
     * @param vegetable the vegetable to get stock amount of
     * @return the stock amount of the given vegetable
     */
    public int getStockOf(Vegetable vegetable) {
        return this.stock.get(vegetable);
    }

    /**
     * Decreases the stock amount of a given vegetable by one.
     * If there are no more vegetables of this type in the stock, it deactivates the countdown.
     *
     * @param vegetable the vegetable to decrease stock amount of
     */
    public void reduceStockOf(Vegetable vegetable) {
        this.stock.put(vegetable, this.stock.get(vegetable) - 1);
        if (!this.vegetableInStock()) {
            this.countdown.deactivate();
        }
    }

    /**
     * Add one vegetable to the stock.
     *
     * @param vegetable the vegetable to be added to the stock
     */
    public void addStockOf(Vegetable vegetable) {
        if (!this.vegetableInStock()) {
            this.countdown.start();
        }
        this.stock.put(vegetable, this.stock.get(vegetable) + 1);
    }

    /**
     * Gets the spoiled vegetables.
     *
     * @return the spoiled vegetables
     */
    public int getSpoiledVegetables() {
        return this.spoiledVegetables;
    }

    /**
     * Returns if the barn is not empty
     *
     * @return true if min one vegetable in stock
     */
    public boolean vegetableInStock() {
        for (Vegetable vegetable: EnumSet.allOf(Vegetable.class)) {
            if (this.stock.get(vegetable) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets gold stock amount.
     *
     * @return the gold stock amount stored in the barn
     */
    public int getGoldStock() {
        return this.goldStock;
    }

    /**
     * Add gold to the stock
     *
     * @param amount the amount of the gold that will be added
     */
    public void addGoldStock(int amount) {
        this.goldStock += amount;
    }

    /**
     * Reduce gold from the stock.
     *
     * @param amount the amount of the gold that will be reduced
     */
    public void reduceGoldStock(int amount) {
        this.goldStock -= amount;
    }

    /**
     * Gets countdown object.
     *
     * @return the countdown object
     */
    public Countdown getCountdown() {
        return this.countdown;
    }

    /**
     * Executes the actions required to advance to the next round of the game.
     */
    @Override
    public void nextRound() {
        this.countdown.decreaseCountdown();
        if (this.countdown.isCountdownFinished()) {
            deleteAllVegetables();
        }
        else {
            this.spoiledVegetables = 0;
        }
    }

    private void deleteAllVegetables() {
        for (Vegetable vegetable: EnumSet.allOf(Vegetable.class)) {
            this.spoiledVegetables += this.stock.get(vegetable);
            this.stock.put(vegetable, 0);
        }
    }
}
