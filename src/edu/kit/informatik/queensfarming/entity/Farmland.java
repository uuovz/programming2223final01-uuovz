package edu.kit.informatik.queensfarming.entity;

import edu.kit.informatik.queensfarming.util.Coordinates;
import edu.kit.informatik.queensfarming.util.Countdown;

/**
 * The Farmland class represents a tile on the game board that can be used to grow and harvest crops.
 * It extends the GameTile class and contains information on the type of farmland
 *
 * @author uuovz
 * @version 1.0
 */
public class Farmland extends GameTile {

    private final FarmlandType farmlandType;
    private Vegetable plantedVegetable;
    private final Countdown countdown;
    private int grownVegetables;
    private int balance;

    /**
     * Creates a new instance of Farmland.
     *
     * @param coordinates the coordinates of the tile
     * @param farmlandType the type of farmland
     */
    Farmland(Coordinates coordinates, FarmlandType farmlandType) {
        super(coordinates);
        this.balance = 0;
        this.farmlandType = farmlandType;
        this.countdown = new Countdown();
    }

    /**
     * Checks if a specific vegetable can be planted on this farmland.
     *
     * @param vegetable the vegetable to check
     * @return true if the vegetable can be planted, false otherwise
     */
    public boolean isPlantable(Vegetable vegetable) {
        return this.farmlandType.getPlantableVegetables().contains(vegetable);
    }

    /**
     * Checks if the farmland is currently plantable.
     *
     * @return true if the farmland is plantable, false otherwise
     */
    public boolean isPlantable() {
        return this.balance == 0;
    }

    /**
     * Plants a vegetable on the farmland.
     * @param vegetable the vegetable to plant
     */
    public void plant(Vegetable vegetable) {
        this.plantedVegetable = vegetable;
        this.balance = 1;
        this.countdown.setRestartRounds(vegetable.getGrowFrequency());
        this.countdown.start();
    }

    /**
     * Harvests the crop from the farmland.
     */
    public void harvest() {
        this.balance -= 1;
        if (this.isPlantable()) {
            this.plantedVegetable = null;
            this.countdown.deactivate();
        } else {
            if (!this.countdown.isActive() || this.countdown.isCountdownFinished()) {
                this.countdown.start();
            }
        }
    }

    /**
     * Gets the number of grown vegetables on the farmland.
     *
     * @return the number of grown vegetables
     */
    public int getGrownVegetables() {
        return this.grownVegetables;
    }

    /**
     * Gets the vegetable currently planted on the farmland.
     *
     * @return the planted vegetable, null if no vegetable is planted
     */
    public Vegetable getPlantedVegetable() {
        return this.plantedVegetable;
    }

    /**
     * Gets the type of farmland.
     *
     * @return the farmland type
     */
    public FarmlandType getFarmlandType() {
        return this.farmlandType;
    }

    /**
     * Gets the balance of the farmland.
     *
     * @return the balance
     */
    public int getBalance() { return this.balance; }

    /**
     * Gets the countdown for growing vegetables.
     *
     * @return the countdownn
     */
    public Countdown getCountdown() {
        return this.countdown;
    }

    /**
     * Increases the balance of the farmland if the countdown is finished and the farmland is not full.
     */
    @Override
    public void nextRound() {
        this.countdown.decreaseCountdown();
        if (this.countdown.isCountdownFinished()) {
            this.grownVegetables = this.grow();
            if (!this.isFull()) {
                this.countdown.start();
            }
        } else {
            this.grownVegetables = 0;
        }
    }

    private int grow() {
        int newBalance = this.balance * 2;
        if (newBalance >= this.farmlandType.getCapacity()) {
            newBalance = this.farmlandType.getCapacity();
        }
        int oldBalance = this.balance;
        this.balance = newBalance;
        return newBalance - oldBalance;
    }


    private boolean isFull() {
        return this.balance == this.farmlandType.getCapacity();
    }

}
