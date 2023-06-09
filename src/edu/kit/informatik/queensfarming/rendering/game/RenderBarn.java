package edu.kit.informatik.queensfarming.rendering.game;

import edu.kit.informatik.queensfarming.entity.Barn;
import edu.kit.informatik.queensfarming.entity.Vegetable;
import edu.kit.informatik.queensfarming.game.Game;
import edu.kit.informatik.queensfarming.util.Countdown;

import java.util.ArrayList;
import java.util.List;

/**
 * The Render of the Barn.
 * This class extends the abstract class RenderGame and provides methods for rendering
 * the barn of a player.
 * @author uuovz
 * @version 1.0
 */
public class RenderBarn extends RenderGame {

    private static final String OUTPUT_BARN_SPOILS = "(spoils in %d turn%s)";
    private static final String OUTPUT_BARN = "Barn";
    private static final String LABEL_SUM = "Sum" + LABEL_SEPERATOR;
    private static final String LABEL_GOLD = "Gold" + LABEL_SEPERATOR;
    private static final String SEPERATOR_GOLD = "-";
    private int maxNameLength;
    private int maxQuantityLength;
    private int index = 0;


    /**
     * Instantiates a new Render barn.
     * @param game the game
     */
    public RenderBarn(Game game) {
        super(game);
    }

    /**
     * Returns the string message that shows the barn with its countdown, gold stock and vegetable stock.
     * If there are vegetables in the barn, it will display the name,
     * quantity and the total sum of each vegetable in stock.
     * If there are no vegetables in the barn, only the gold stock will be displayed.
     * @return the string message that shows the barn with its countdown, gold stock and vegetable stock
    */
    @Override
    public String render() {
        Barn barn = this.getGame().getGameTileBoard(this.index).getBarn();
        int goldStock = barn.getGoldStock();

        this.maxNameLength = LABEL_GOLD.length() - LABEL_SEPERATOR.length();
        this.maxQuantityLength = String.valueOf(goldStock).length();

        this.stringBuilder = new StringBuilder();
        stringBuilder.append(OUTPUT_BARN);
        if (barn.vegetableInStock()) {
            this.upperPart(barn);
        }
        else {
            stringBuilder.append(NEW_LINE);
        }
        this.lowerPart(goldStock);
        return stringBuilder.toString();
    }

    private List<Vegetable> getSortedVegetableList(Barn barn) {
        List<Vegetable> sortedList = new ArrayList<>();
        for (Vegetable vegetable : Vegetable.values()) {
            if (barn.getStockOf(vegetable) > 0) {
                sortedList.add(vegetable);
            }
        }
        sortedList.sort((v1, v2) -> {
            int quantity1 = barn.getStockOf(v1);
            int quantity2 = barn.getStockOf(v2);
            if (quantity1 == quantity2) {
                return v1.getName().compareTo(v2.getName());
            }
            return quantity1 - quantity2;
        });
        return sortedList;
    }

    /**
     * Sets the index of the RenderBarn object to the specified value.
     * @param index the new index value to be set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    private void upperPart(Barn barn) {
        List<Vegetable> sortedList = this.getSortedVegetableList(barn);
        Countdown countdown = barn.getCountdown();
        int remainingTurns = countdown.getRemainingTurns();
        String suffix = remainingTurns == 1 ? EMPTY_STRING : SUFFIX_S;
        if (barn.vegetableInStock()) {
            stringBuilder.append(String.format(BLANK_STRING + OUTPUT_BARN_SPOILS, remainingTurns, suffix));
            stringBuilder.append(NEW_LINE);

            int totalSum = 0;
            for (Vegetable vegetable : sortedList) {
                int quantity = barn.getStockOf(vegetable);
                suffix = getSuffixVegetable(vegetable, 2);
                this.maxNameLength = Math.max(maxNameLength, (vegetable.getName() + suffix).length());
                totalSum += quantity;
                this.maxQuantityLength = Math.max(this.maxQuantityLength,
                    Math.max(String.valueOf(quantity).length(), String.valueOf(totalSum).length()));
            }


            for (Vegetable vegetable : sortedList) {
                int quantity = barn.getStockOf(vegetable);
                suffix = getSuffixVegetable(vegetable, 2);
                stringBuilder.append(
                        String.format("%-" + (this.maxNameLength + 1) + "s", (vegetable.getName() + suffix)
                            + LABEL_SEPERATOR)
                    )
                    .append(String.format("%" + (this.maxQuantityLength + 1) + "d", quantity))
                    .append(NEW_LINE);
            }

            stringBuilder.append(SEPERATOR_GOLD.repeat(this.maxNameLength + this.maxQuantityLength + 2))
                .append(NEW_LINE)
                .append(String.format("%-" + (this.maxNameLength + 1) + "s", LABEL_SUM))
                .append(String.format("%" + (this.maxQuantityLength + 1) + "d", totalSum))
                .append(NEW_LINE.repeat(2));
        }
    }

    private void lowerPart(int goldStock) {
        stringBuilder.append(String.format("%-" + (this.maxNameLength + 2) + "s", LABEL_GOLD))
            .append(String.format("%" + (this.maxQuantityLength ) + "d", goldStock));
    }
}
