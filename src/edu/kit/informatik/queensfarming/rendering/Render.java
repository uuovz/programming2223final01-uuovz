package edu.kit.informatik.queensfarming.rendering;

import edu.kit.informatik.queensfarming.entity.Vegetable;

/**
 * The abstract class Render that implements the Renderable interface.
 * It contains methods that help in rendering the different components of the output.
 * @author uuovz
 * @version 1.0
 */

public abstract class Render implements Renderable {

    /**
     * The constant SUFFIX_S.
     */
    protected static final String SUFFIX_S = "s";
    /**
     * The constant SUFFIX_ES for tomato.
     */
    protected static final String SUFFIX_ES = "es";
    /**
     * The constant is an empty string.
     */
    protected static final String EMPTY_STRING = "";
    /**
     * The constant is a blank string.
     */
    protected static final String BLANK_STRING = " ";
    /**
     * The constant is a line seperator.
     */
    protected static final String NEW_LINE = System.lineSeparator();

    /**
     * This method must be implemented by the classes that inherit from Render
     * and contain the logic for rendering the output.
     * @return a String representation of the rendered output.
     */
    @Override
    public abstract String render();

    /**
     * Returns the suffix for the amount of the given Vegetable.
     * @param vegetable the Vegetable for which the suffix needs to be returned.
     * @param amount the amount of the given Vegetable.
     * @return the suffix for the amount of the given Vegetable.
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

    /**
     * Returns the suffix for the given amount.
     * @param amount the amount for which the suffix needs to be returned.
     * @return the suffix for the given amount.
     */
    protected static String getSuffix(int amount) {
        String suffix = SUFFIX_S;
        if (amount == 1) {
            suffix = EMPTY_STRING;
        }
        return suffix;
    }
}
