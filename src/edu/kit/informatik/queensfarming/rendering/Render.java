package edu.kit.informatik.queensfarming.rendering;

import edu.kit.informatik.queensfarming.entity.Vegetable;

public abstract class Render implements Renderable {

    /**
     * The constant SUFFIX_S.
     */
    protected static final String SUFFIX_S = "s";
    /**
     * The constant SUFFIX_ES.
     */
    protected static final String SUFFIX_ES = "es";
    
    protected static final String EMPTY_STRING = "";

    protected static final String NEW_LINE = System.lineSeparator();

    /**
     *
     * @return
     */
    @Override
    public abstract String render();

    /**
     * Gets suffix.
     *
     * @param vegetable the vegetable
     * @param amount    the amount
     * @return the suffix
     */
    public static String getSuffixVegetable(Vegetable vegetable, int amount) {
        String suffix = EMPTY_STRING;
        if (amount == 0) {
            suffix = SUFFIX_S;
        } else if (amount > 1) {
            if (vegetable.equals(Vegetable.TOMATO)) {
                suffix = SUFFIX_ES;
            } else {
                suffix = SUFFIX_S;
            }
        }
        return suffix;
    }

    public static String getSuffix(int amount) {
        String suffix = SUFFIX_S;
        if (amount == 1) {
            suffix = EMPTY_STRING;
        }
        return suffix;
    }
}
