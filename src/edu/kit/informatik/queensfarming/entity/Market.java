package edu.kit.informatik.queensfarming.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Market.
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
     * Instantiates a new Market.
     *
     * @param marketType the market type
     */
    public Market(MarketType marketType) {
        for (Vegetable vegetable: marketType.getPair()) {
            vegetablePair.add(vegetable);
        }
        this.rates = marketType.getRates();
    }

    /**
     * Re calculate.
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
     * Sell.
     *
     * @param vegetable the vegetable
     */
    public void sell(Vegetable vegetable) {
        if (this.getIndexOfVegetable(vegetable) == 0) {
            this.sellDifference += 1;
        } else {
            this.sellDifference -= 1;
        }
    }

    /**
     * Sellable boolean.
     *
     * @param vegetable the vegetable
     * @return the boolean
     */
    public boolean sellable(Vegetable vegetable) {
        return this.vegetablePair.contains(vegetable);
    }

    /**
     * Gets rate.
     *
     * @param vegetable the vegetable
     * @return the rate
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
