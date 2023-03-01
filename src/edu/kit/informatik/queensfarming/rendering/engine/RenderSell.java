package edu.kit.informatik.queensfarming.rendering.engine;

import edu.kit.informatik.queensfarming.rendering.Render;

/**
 * The type Render sell.
 * This class extends the abstract class Render and provides methods for rendering
 * the result of selling vegetables in the game.
 * @author uuovz
 * @version 1.0
 */
public class RenderSell extends Render {

    private static final String MESSAGE_TEMPLATE = "You have sold %d vegetable%s for %d gold.";
    private int totalSoldVegetables;
    private int totalGold;

    /**
     * Returns the string message that shows the result of selling vegetables.
     * @return the string message that shows the result of selling vegetables
     */
    @Override
    public String render() {
        String suffix = getSuffix(this.totalSoldVegetables);
        return String.format(MESSAGE_TEMPLATE, totalSoldVegetables, suffix, totalGold);
    }

    /**
     * Sets the total number of vegetables that have been sold.
     * @param totalSoldVegetables the total number of vegetables that have been sold
     */
    public void setTotalSoldVegetables(int totalSoldVegetables) {
        this.totalSoldVegetables = totalSoldVegetables;
    }

    /**
     * Sets the total gold obtained from selling vegetables.
     * @param totalGold the total gold obtained from selling vegetables
     */
    public void setTotalGold(int totalGold) {
        this.totalGold = totalGold;
    }
}
