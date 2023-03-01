package edu.kit.informatik.queensfarming.rendering.engine;

import edu.kit.informatik.queensfarming.entity.Vegetable;
import edu.kit.informatik.queensfarming.rendering.Render;

public class RenderBuy extends Render {

    private static final String MESSAGE_TEMPLATE = "You have bought a %s for %d gold.";
    private String item;
    private int rate;

    /**
     * Output buy land string.
     *
     * @param farmlandName the farmland name
     * @param rate         the rate
     * @return the string
     */
    @Override
    public String render() {
        return String.format(MESSAGE_TEMPLATE, this.item, rate);
    }

    /**
     *
     * @param vegetable
     */
    public void setItem(String name) {
        this.item = name;
    }

    /**
     *
     * @param rate
     */
    public void setRate(int rate) {
        this.rate = rate;
    }
}
