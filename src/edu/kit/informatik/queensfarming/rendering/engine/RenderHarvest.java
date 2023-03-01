package edu.kit.informatik.queensfarming.rendering.engine;

import edu.kit.informatik.queensfarming.entity.Vegetable;
import edu.kit.informatik.queensfarming.rendering.Render;

public class RenderHarvest extends Render {

    private static final String MESSAGE_TEMPLATE = "You have harvested %d %s%s.";
    private static final String GROWN_SINGLE = "has";
    private static final String GROWN_MULTIPLE = "have";
    private Vegetable vegetable;
    private int amount;

    @Override
    public String render() {
        String suffix = getSuffixVegetable(this.vegetable, this.amount);
        return String.format(MESSAGE_TEMPLATE, amount, vegetable.getName(), suffix);
    }

    public void setVegetable(Vegetable vegetable) {
        this.vegetable = vegetable;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
