package edu.kit.informatik.queensfarming.rendering.game;

import edu.kit.informatik.queensfarming.entity.Barn;
import edu.kit.informatik.queensfarming.entity.Vegetable;
import edu.kit.informatik.queensfarming.game.Game;
import edu.kit.informatik.queensfarming.util.Countdown;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author uuovz
 * @version 1.0
 */
public class RenderBarn extends RenderGame {

    private static final String OUTPUT_BARN_SPOILS = "(spoils in %d turn%s)";
    private static final String OUTPUT_BARN = "Barn";
    private static final String LABEL_SUM = "Sum" + LABEL_SEPERATOR;
    private static final String LABEL_GOLD = "Gold" + LABEL_SEPERATOR;
    private static final String SEPERATOR_GOLD = "-";
    private int index = 0;


    /**
     *
     * @param game
     */
    public RenderBarn(Game game) {
        super(game);
    }

    /**
     * Show barn string.
     *
     * @return the string
     */
    @Override
    public String render() {
        Barn barn = this.game.getGameTileBoard(this.index).getBarn();
        Countdown countdown = barn.getCountdown();
        int goldStock = barn.getGoldStock();

        List<Vegetable> sortedList = this.getSortedVegetableList(barn);
        int maxNameLength = LABEL_GOLD.length() - LABEL_SEPERATOR.length();
        int maxQuantityLength = String.valueOf(goldStock).length();

        StringBuilder stringBuilder = new StringBuilder();
        int remainingTurns = countdown.getRemainingTurns();
        String suffix = remainingTurns == 1 ? EMPTY_STRING : SUFFIX_S;
        stringBuilder.append(OUTPUT_BARN);
        if (barn.vegetableInStock()) {
            stringBuilder.append(String.format(LABEL_BLANK + OUTPUT_BARN_SPOILS, remainingTurns, suffix));
            stringBuilder.append(NEW_LINE);

            for (Vegetable vegetable : sortedList) {
                suffix = getSuffixVegetable(vegetable, 2);
                maxNameLength = Math.max(maxNameLength, (vegetable.getName() + suffix).length());
                maxQuantityLength = Math.max(maxQuantityLength, String.valueOf(barn.getStockOf(vegetable)).length());
            }

            int totalSum = 0;
            for (Vegetable vegetable : sortedList) {
                int quantity = barn.getStockOf(vegetable);
                suffix = getSuffixVegetable(vegetable, 2);
                stringBuilder.append(
                        String.format("%-" + (maxNameLength + 1) + "s", (vegetable.getName() + suffix)
                            + LABEL_SEPERATOR)
                    )
                    .append(String.format("%" + (maxQuantityLength + 1) + "d", quantity))
                    .append(NEW_LINE);
                totalSum += quantity;
            }

            stringBuilder.append(SEPERATOR_GOLD.repeat(maxNameLength + maxQuantityLength + 2))
                .append(NEW_LINE)
                .append(String.format("%-" + (maxNameLength + 1) + "s", LABEL_SUM))
                .append(String.format("%" + (maxQuantityLength + 1) + "d", totalSum))
                .append(NEW_LINE.repeat(2));
        }

        else {
            stringBuilder.append(NEW_LINE);
        }
        stringBuilder.append(String.format("%-" + (maxNameLength + 2) + "s", LABEL_GOLD))
            .append(String.format("%" + (maxQuantityLength ) + "d", goldStock));
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
     *
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }
}
