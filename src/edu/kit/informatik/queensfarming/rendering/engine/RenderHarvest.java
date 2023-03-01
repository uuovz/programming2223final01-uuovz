package edu.kit.informatik.queensfarming.rendering.engine;

import edu.kit.informatik.queensfarming.entity.Vegetable;
import edu.kit.informatik.queensfarming.rendering.Render;

/**
 * The rendering engine for the "harvest" command.
 * @author uuovz
 * @version 1.0
 */

public class RenderHarvest extends Render {

    private static final String MESSAGE_TEMPLATE = "You have harvested %d %s%s.";
    private Vegetable vegetable;
    private int amount;

    /**
     * Renders the output for harvest a farmland.
     * @return The string representation of the output message.
     */
    @Override
    public String render() {
        String suffix = getSuffixVegetable(this.vegetable, this.amount);
        return String.format(MESSAGE_TEMPLATE, amount, vegetable.getName(), suffix);
    }

    /**
     * Sets the vegetable of the item being harvested.
     * @param vegetable the vegetable being harvested
     */
    public void setVegetable(Vegetable vegetable) {
        this.vegetable = vegetable;
    }

    /**
     * Sets the amount of the vegetable being harvested.
     * @param amount the amount of the vegetable being harvested
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
