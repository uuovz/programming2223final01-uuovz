package edu.kit.informatik.queensfarming.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum MarketType {
    MUSHROOM_CARROT(Vegetable.MUSHROOM, Vegetable.CARROT, new int[][] {{12, 3}, {15, 2}, {16, 2}, {17, 2}, {20, 1}}),
    TOMATO_SALAD(Vegetable.TOMATO, Vegetable.SALAD, new int[][] {{3, 6}, {5, 5}, {6, 4}, {7, 3}, {9, 2}});

    private final List<Vegetable> pair;
    private final int[][] rates;

    MarketType(Vegetable vegetableA, Vegetable vegetableB, int[][] rates) {
        this.pair = new ArrayList<>();
        this.pair.add(vegetableA);
        this.pair.add(vegetableB);
        this.rates = rates.clone();
    }

    public List<Vegetable> getPair() {
        return Collections.unmodifiableList(pair);
    }

    public int[][] getRates() {
        return rates.clone();
    }
}
