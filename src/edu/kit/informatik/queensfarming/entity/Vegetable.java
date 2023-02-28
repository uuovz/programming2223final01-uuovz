package edu.kit.informatik.queensfarming.entity;

/**
 * The enum Vegetable.
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
     *
     * @param name
     * @param shortcut
     * @param growFrequency
     */
    Vegetable(String name, String shortcut, int growFrequency) {
        this.name = name;
        this.shortcut = shortcut;
        this.growFrequency = growFrequency;
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
     * Gets grow frequency.
     *
     * @return the grow frequency
     */
    public int getGrowFrequency() {
        return this.growFrequency;
    }

}
