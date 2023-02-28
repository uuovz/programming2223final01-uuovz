package edu.kit.informatik.queensfarming.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The enum Farmland type.
 *
 * @author uuovz
 * @version 1.0
 */
public enum FarmlandType {
    /**
     * Garden farmland type.
     */
    GARDEN("Garden", "G", 2, 2, Vegetable.CARROT, Vegetable.MUSHROOM, Vegetable.SALAD, Vegetable.TOMATO),
    /**
     * Field farmland type.
     */
    FIELD("Field", "Fi", 3, 4, Vegetable.CARROT, Vegetable.SALAD, Vegetable.TOMATO),
    /**
     * The Large field.
     */
    LARGE_FIELD("Large Field", "LFi", 2, 8, Vegetable.CARROT, Vegetable.SALAD, Vegetable.TOMATO),
    /**
     * Forest farmland type.
     */
    FOREST("Forest", "Fo", 2, 4, Vegetable.CARROT, Vegetable.MUSHROOM),
    /**
     * The Large forest.
     */
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

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets shortcut.
     *
     * @return the shortcut
     */
    public String getShortcut() {
        return this.shortcut;
    }

    /**
     * Gets factor.
     *
     * @return the factor
     */
    public int getFactor() {
        return factor;
    }

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Gets plantable vegetables.
     *
     * @return the plantable vegetables
     */
    public List<Vegetable> getPlantableVegetables() {
        return Collections.unmodifiableList(this.plantableVegetables);
    }
}
