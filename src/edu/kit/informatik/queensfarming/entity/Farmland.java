package edu.kit.informatik.queensfarming.entity;

import edu.kit.informatik.queensfarming.util.Coordinates;
import edu.kit.informatik.queensfarming.util.Countdown;

/**
 * The type Farmland.
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
     * Instantiates a new Farmland.
     *
     * @param coordinates  the coordinates
     * @param farmlandType the farmland type
     */
    Farmland(Coordinates coordinates, FarmlandType farmlandType) {
        super(coordinates);
        this.balance = 0;
        this.farmlandType = farmlandType;
        this.countdown = new Countdown();
    }

    /**
     * Is plantable boolean.
     *
     * @param vegetable the vegetable
     * @return the boolean
     */
    public boolean isPlantable(Vegetable vegetable) {
        return this.farmlandType.getPlantableVegetables().contains(vegetable);
    }

    /**
     * Is plantable boolean.
     *
     * @return the boolean
     */
    public boolean isPlantable() {
        return this.balance == 0;
    }

    /**
     * Plant.
     *
     * @param vegetable the vegetable
     */
    public void plant(Vegetable vegetable) {
        this.plantedVegetable = vegetable;
        this.balance = 1;
        this.countdown.setRestartRounds(vegetable.getGrowFrequency());
        this.countdown.start();
    }

    /**
     * Harvest.
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
     * Gets grown vegetables.
     *
     * @return the grown vegetables
     */
    public int getGrownVegetables() {
        return this.grownVegetables;
    }

    /**
     * Gets planted vegetable.
     *
     * @return the planted vegetable
     */
    public Vegetable getPlantedVegetable() {
        return this.plantedVegetable;
    }

    /**
     * Gets farmland type.
     *
     * @return the farmland type
     */
    public FarmlandType getFarmlandType() {
        return this.farmlandType;
    }

    /**
     * Gets balance.
     *
     * @return the balance
     */
    public int getBalance() { return this.balance; }

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
