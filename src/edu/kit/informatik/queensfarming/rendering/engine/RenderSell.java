package edu.kit.informatik.queensfarming.rendering.engine;

import edu.kit.informatik.queensfarming.rendering.Render;

/**
 * The type Render sell.
 */
public class RenderSell extends Render {


    private static final String MESSAGE_TEMPLATE = "You have sold %d vegetable%s for %d gold.";
    private int totalSoldVegetables;
    private int totalGold;

    /**
     * Output sell string.
     *
     * @param totalSoldVegetables the total sold vegetables
     * @param totalGold           the total gold
     * @return the string
     */
    @Override
    public String render() {
        String suffix = getSuffix(this.totalSoldVegetables);
        return String.format(MESSAGE_TEMPLATE, totalSoldVegetables, suffix, totalGold);
    }

    /**
     * Sets total sold vegetables.
     *
     * @param totalSoldVegetables the total sold vegetables
     */
    public void setTotalSoldVegetables(int totalSoldVegetables) {
        this.totalSoldVegetables = totalSoldVegetables;
    }

    public void setTotalGold(int totalGold) {
        this.totalGold = totalGold;
    }
}
