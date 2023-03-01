package edu.kit.informatik.queensfarming.rendering.engine;

import edu.kit.informatik.queensfarming.rendering.Render;

/**
 * The rendering engine for the "buy" command.
 * @author uuovz
 * @version 1.0
 */

public class RenderBuy extends Render {

    private static final String MESSAGE_TEMPLATE = "You have bought a %s for %d gold.";
    private String item;
    private int rate;

    /**
     * Renders the output for buying a land.
     * @return The string representation of the output message.
     */
    @Override
    public String render() {
        return String.format(MESSAGE_TEMPLATE, this.item, rate);
    }

    /**
     * Sets the name of the item being bought.
     * @param name The name of the item.
     */
    public void setItem(String name) {
        this.item = name;
    }

    /**
     * Sets the rate (cost) of the item being bought.
     * @param rate The cost of the item.
     */
    public void setRate(int rate) {
        this.rate = rate;
    }
}
