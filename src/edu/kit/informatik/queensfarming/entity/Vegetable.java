package edu.kit.informatik.queensfarming.entity;

public enum Vegetable {
    CARROT ("carrot", "C", 1),
    SALAD("salad", "S", 2),
    TOMATO("tomato", "T", 3),
    MUSHROOM("mushroom", "M", 4);

    private final String name;
    private final String shortcut;
    private final int growFrequency;

    Vegetable(String name, String shortcut, int growFrequency) {
        this.name = name;
        this.shortcut = shortcut;
        this.growFrequency = growFrequency;
    }

    public String getName() {
        return this.name;
    }

    public String getShortcut() {
        return this.shortcut;
    }

    public int getGrowFrequency() {
        return this.growFrequency;
    }

}
