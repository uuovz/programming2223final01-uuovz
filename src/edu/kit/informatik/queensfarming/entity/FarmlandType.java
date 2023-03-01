package edu.kit.informatik.queensfarming.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The enum Farmland type represents different types of Farmland that can be used in the game.
 * Each type has a name, shortcut, factor, capacity and a list of plantable vegetables.
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


    /**
     * Constructs a new FarmlandType instance with the given name, shortcut, factor, capacity, and plantable vegetables.
     *
     * @param name the name of the FarmlandType
     * @param shortcut the shortcut of the FarmlandType
     * @param factor the factor of the FarmlandType
     * @param capacity the capacity of the FarmlandType
     * @param plantableVegetables the list of plantable vegetables of the FarmlandType
     */
    FarmlandType(String name, String shortcut, int factor, int capacity, Vegetable... plantableVegetables) {
        this.name = name;
        this.shortcut = shortcut;
        this.factor = factor;
        this.capacity = capacity;
        this.plantableVegetables = new ArrayList<>();
        this.plantableVegetables.addAll(Arrays.asList(plantableVegetables));
    }

    /**
     * Gets the name of the FarmlandType.
     *
     * @return the name of the FarmlandType
    */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the shortcut of the FarmlandType.
     *
     * @return the shortcut of the FarmlandType
     */
    public String getShortcut() {
        return this.shortcut;
    }

    /**
     * Gets the factor of the FarmlandType.
     *
     * @return the factor of the FarmlandType
     */
    public int getFactor() {
        return factor;
    }

    /**
     * Gets the capacity of the FarmlandType.
     *
     * @return the capacity of the FarmlandType
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Gets the list of plantable vegetables of the FarmlandType.
     *
     * @return the list of plantable vegetables of the FarmlandType
     */
    public List<Vegetable> getPlantableVegetables() {
        return Collections.unmodifiableList(this.plantableVegetables);
    }
}
