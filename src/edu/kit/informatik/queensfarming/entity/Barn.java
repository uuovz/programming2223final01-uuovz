package edu.kit.informatik.queensfarming.entity;

import edu.kit.informatik.queensfarming.util.Coordinates;
import edu.kit.informatik.queensfarming.util.Countdown;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Barn.
 *
 * @author uuovz
 * @version 1.0
 */
public class Barn extends GameTile {

    /**
     * The constant SHORTCUT.
     */
    public static final String SHORTCUT = "B";
    private static final int COUNTDOWN_FREQUENCY = 6;
    private static final int MINIMUM_COUNTDOWN = 0;
    private static final int INITIAL_VALUE = 1;
    private final Countdown countdown;
    private final Map<Vegetable, Integer> stock = new HashMap<>();
    private int spoiledVegetables = 0;
    private int goldStock;


    /**
     * Instantiates a new Barn.
     *
     * @param coordinates the coordinates
     * @param initialGold the initial gold
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
     * Gets stock of.
     *
     * @param vegetable the vegetable
     * @return the stock of
     */
    public int getStockOf(Vegetable vegetable) {
        return this.stock.get(vegetable);
    }

    /**
     * Reduce stock of.
     *
     * @param vegetable the vegetable
     */
    public void reduceStockOf(Vegetable vegetable) {
        this.stock.put(vegetable, this.stock.get(vegetable) - 1);
        if (!this.vegetableInStock()) {
            this.countdown.deactivate();
        }
    }

    /**
     * Add stock of.
     *
     * @param vegetable the vegetable
     */
    public void addStockOf(Vegetable vegetable) {
        if (!this.vegetableInStock()) {
            this.countdown.start();
        }
        this.stock.put(vegetable, this.stock.get(vegetable) + 1);
    }

    /**
     * Gets spoiled vegetables.
     *
     * @return the spoiled vegetables
     */
    public int getSpoiledVegetables() {
        return this.spoiledVegetables;
    }

    /**
     * Vegetable in stock boolean.
     *
     * @return the boolean
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
     * Gets gold stock.
     *
     * @return the gold stock
     */
    public int getGoldStock() {
        return this.goldStock;
    }

    /**
     * Add gold stock.
     *
     * @param amount the amount
     */
    public void addGoldStock(int amount) {
        this.goldStock += amount;
    }

    /**
     * Reduce gold stock.
     *
     * @param amount the amount
     */
    public void reduceGoldStock(int amount) {
        this.goldStock -= amount;
    }

    /**
     * Gets countdown.
     *
     * @return the countdown
     */
    public Countdown getCountdown() {
        return this.countdown;
    }

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
