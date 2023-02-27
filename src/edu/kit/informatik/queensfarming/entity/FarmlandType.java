package edu.kit.informatik.queensfarming.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum FarmlandType {
    GARDEN("Garden", "G", 2, 2, Vegetable.CARROT, Vegetable.MUSHROOM, Vegetable.SALAD, Vegetable.TOMATO),
    FIELD("Field", "Fi", 3, 4, Vegetable.CARROT, Vegetable.SALAD, Vegetable.TOMATO),
    LARGE_FIELD("Large Field", "LFi", 2, 8, Vegetable.CARROT, Vegetable.SALAD, Vegetable.TOMATO),
    FOREST("Forest", "Fo", 2, 4, Vegetable.CARROT, Vegetable.MUSHROOM),
    LARGE_FOREST("Large Forest", "LFo", 1, 8, Vegetable.CARROT, Vegetable.MUSHROOM);

    private final String name;
    private final String shortcut;
    private final int factor;
    private final int capacity;
    private final List<Vegetable> plantableVegetables;
    FarmlandType(String name, String shortcut, int factor, int capacity, Vegetable... plantableVegetables) {
        this.name = name;
        this.shortcut = shortcut;
        this.factor = factor;
        this.capacity = capacity;
        this.plantableVegetables = new ArrayList<>();
        this.plantableVegetables.addAll(Arrays.asList(plantableVegetables));
    }

    public String getName() {
        return this.name;
    }

    public String getShortcut() {
        return this.shortcut;
    }

    public int getFactor() {
        return factor;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public List<Vegetable> getPlantableVegetables() {
        return Collections.unmodifiableList(this.plantableVegetables);
    }
}
