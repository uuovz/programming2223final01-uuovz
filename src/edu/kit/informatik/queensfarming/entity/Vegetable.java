package edu.kit.informatik.queensfarming.entity;

/**
 * The enum Vegetable.
 * This enum represents different types of vegetables with their name, shortcut, and grow frequency.
 * The grow frequency determines the number of rounds required for the vegetable to grow.
 */
public enum Vegetable {
    /**
     * Carrot vegetable.
     */
    CARROT ("carrot", "C", 1),
    /**
     * Salad vegetable.
     */
    SALAD("salad", "S", 2),
    /**
     * Tomato vegetable.
     */
    TOMATO("tomato", "T", 3),
    /**
     * Mushroom vegetable.
     */
    MUSHROOM("mushroom", "M", 4);

    private final String name;
    private final String shortcut;
    private final int growFrequency;

    /**
     * Constructs a Vegetable object with the specified name, shortcut, and grow frequency.
     *
     * @param name the name of the vegetable
     * @param shortcut the shortcut for the vegetable
     * @param growFrequency the grow frequency for the vegetable
     */
    Vegetable(String name, String shortcut, int growFrequency) {
        this.name = name;
        this.shortcut = shortcut;
        this.growFrequency = growFrequency;
    }

    /**
     * Gets the name of the vegetable.
     *
     * @return the name of the vegetable
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the shortcut for the vegetable.
     *
     * @return the shortcut for the vegetable
     */
    public String getShortcut() {
        return this.shortcut;
    }

    /**
     * Gets the grow frequency for the vegetable.
     *
     * @return the grow frequency for the vegetable
     */
    public int getGrowFrequency() {
        return this.growFrequency;
    }

}
