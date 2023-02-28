package edu.kit.informatik.queensfarming.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The enum Market type.
 *
 * @author uuovz
 * @version 1.0
 */
public enum MarketType {
    /**
     * The Mushroom carrot.
     */
    MUSHROOM_CARROT(Vegetable.MUSHROOM, Vegetable.CARROT, new int[][] {{12, 3}, {15, 2}, {16, 2}, {17, 2}, {20, 1}}),
    /**
     * The Tomato salad.
     */
    TOMATO_SALAD(Vegetable.TOMATO, Vegetable.SALAD, new int[][] {{3, 6}, {5, 5}, {6, 4}, {7, 3}, {9, 2}});

    private final List<Vegetable> pair;
    private final int[][] rates;

    /**
     *
     * @param vegetableA
     * @param vegetableB
     * @param rates
     */
    MarketType(Vegetable vegetableA, Vegetable vegetableB, int[][] rates) {
        this.pair = new ArrayList<>();
        this.pair.add(vegetableA);
        this.pair.add(vegetableB);
        this.rates = rates.clone();
    }

    /**
     * Gets pair.
     *
     * @return the pair
     */
    public List<Vegetable> getPair() {
        return Collections.unmodifiableList(pair);
    }

    /**
     * Get rates int [ ] [ ].
     *
     * @return the int [ ] [ ]
     */
    public int[][] getRates() {
        return rates.clone();
    }
}
