package edu.kit.informatik.queensfarming.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The enum Market type defines different types of markets.
 * Each type has a pair of vegetables and their corresponding rates.
 * The vegetable pair and rates are defined upon instantiation of each type.
 *
 * @author uuovz
 * @version 1.0
 */
public enum MarketType {
    /**
     * The Mushroom carrot market type.
     */
    MUSHROOM_CARROT(Vegetable.MUSHROOM, Vegetable.CARROT, new int[][] {{12, 3}, {15, 2}, {16, 2}, {17, 2}, {20, 1}}),
    /**
     * The Tomato salad market type.
     */
    TOMATO_SALAD(Vegetable.TOMATO, Vegetable.SALAD, new int[][] {{3, 6}, {5, 5}, {6, 4}, {7, 3}, {9, 2}});

    private final List<Vegetable> pair;
    private final int[][] rates;

    /**
     * Creates a new market type with the given vegetable pair and their corresponding rates.
     *
     * @param vegetableA the first vegetable in the pair
     * @param vegetableB the second vegetable in the pair
     * @param rates the corresponding rates for the vegetable pair
     */
    MarketType(Vegetable vegetableA, Vegetable vegetableB, int[][] rates) {
        this.pair = new ArrayList<>();
        this.pair.add(vegetableA);
        this.pair.add(vegetableB);
        this.rates = rates.clone();
    }

    /**
     * Gets the vegetable pair for this market type.
     *
     * @return an unmodifiable list of the vegetable pair
     */
    public List<Vegetable> getPair() {
        return Collections.unmodifiableList(pair);
    }

    /**
     * Gets the rates for this market type.
     *
     * @return a clone of the rates array
     */
    public int[][] getRates() {
        return rates.clone();
    }
}
