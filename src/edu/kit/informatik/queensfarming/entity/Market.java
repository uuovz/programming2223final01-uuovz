package edu.kit.informatik.queensfarming.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The Market class represents a market where vegetables can be sold.
 * It keeps track of the vegetable pairs that can be sold at the market, the rate of sale for each vegetable,
 * the rate phase, and the sell difference.
 *
 * @author uuovz
 * @version 1.0
 */
public class Market {
    private static final int DIFFERENCE = 2;
    private static final int MAX_PHASE = 4;
    private static final int MIN_PHASE = 0;
    private final List<Vegetable> vegetablePair = new ArrayList<>();
    private final int[][] rates;
    private int ratePhase = 2;
    private int sellDifference = 0;

    /**
     * Constructs a new Market object with the specified MarketType.
     *
     * @param marketType the MarketType that defines the vegetable pairs and rates for this Market
     */
    public Market(MarketType marketType) {
        vegetablePair.addAll(marketType.getPair());
        this.rates = marketType.getRates();
    }

    /**
     * Recalculates the rate phase based on the current sell difference.
     */
    public void reCalculate() {
        while (this.sellDifference >= DIFFERENCE || this.sellDifference <= -DIFFERENCE) {
            if (this.sellDifference >= DIFFERENCE) {
                setRatePhase(-1);
                this.sellDifference -= DIFFERENCE;
            } else {
                setRatePhase(1);
                this.sellDifference += DIFFERENCE;
            }
        }
        this.sellDifference = 0;
    }

    /**
     * Adds the specified Vegetable object to the market to be sold.
     * @param vegetable the Vegetable object to be sold
     */
    public void sell(Vegetable vegetable) {
        if (this.getIndexOfVegetable(vegetable) == 0) {
            this.sellDifference += 1;
        } else {
            this.sellDifference -= 1;
        }
    }

    /**
     * Checks if the specified Vegetable object is sellable at this Market.
     *
     * @param vegetable the Vegetable object to check
     * @return true if the vegetable is included in the vegetable pair list, false otherwise
     */
    public boolean sellable(Vegetable vegetable) {
        return this.vegetablePair.contains(vegetable);
    }

    /**
     * Gets the rate of sale for the specified Vegetable object at the current rate phase.
     *
     * @param vegetable the Vegetable object to get the rate for
     * @return the rate of sale for the specified vegetable at the current rate phase
     */
    public int getRate(Vegetable vegetable) {
        return this.rates[this.ratePhase][this.getIndexOfVegetable(vegetable)];
    }

    private void setRatePhase(int direction) {
        int newPhase = this.ratePhase + direction;
        if (newPhase > MAX_PHASE) {
            newPhase = MAX_PHASE;
        } else if (newPhase < MIN_PHASE) {
            newPhase = MIN_PHASE;
        }
        this.ratePhase = newPhase;
    }

    private int getIndexOfVegetable(Vegetable vegetable) {
        return vegetablePair.indexOf(vegetable);
    }
}
