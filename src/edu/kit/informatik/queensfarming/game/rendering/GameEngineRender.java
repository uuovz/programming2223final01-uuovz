package edu.kit.informatik.queensfarming.game.rendering;

import edu.kit.informatik.queensfarming.entity.GameTileBoard;
import edu.kit.informatik.queensfarming.entity.Vegetable;
import edu.kit.informatik.queensfarming.game.Config;
import edu.kit.informatik.queensfarming.game.Game;
import edu.kit.informatik.queensfarming.ui.Shell;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * The type Game engine render.
 *
 * @author uuovz
 * @version 1.0
 */
public class GameEngineRender {

    private static final String MESSAGE_TEMPLATE_PURCHASE = "You have bought a %s for %d gold.";
    private static final String MESSAGE_TEMPLATE_SALE = "You have sold %d vegetable%s for %d gold.";
    private static final String MESSAGE_TEMPLATE_HARVEST = "You have harvested %d %s%s.";
    private static final String MESSAGE_TEMPLATE_TURN = "It is %s's turn!";
    private static final String MESSAGE_TEMPLATE_GROWN = "%d vegetable%s %s grown since your last turn.";
    private static final String GROWN_SINGLE = "has";
    private static final String GROWN_MULTIPLE = "have";
    private static final String MESSAGE_TEMPLATE_SPOILED = "The vegetables in your barn are spoiled.";
    private static final String MESSAGE_TEMPLATE_RESULT_PLAYER = "Player %d (%s): %d%s";
    private static final String MESSAGE_TEMPLATE_HAS_WON = "%s has won!";
    private static final String MESSAGE_TEMPLATE_HAVE_WON = " have won!";
    private static final String LABEL_SEPERATOR_COMMA = ",";
    private static final String LABEL_SEPERATOR_AND = "and";
    private final Config config;
    private final Game game;

    /**
     * Instantiates a new Game engine render.
     *
     * @param config the config
     * @param game   the game
     */
    public GameEngineRender(Config config, Game game) {
        this.config = config;
        this.game = game;
    }

    /**
     * Output buy vegetable string.
     *
     * @param vegetableName the vegetable name
     * @param rate          the rate
     * @return the string
     */
    public String outputBuyVegetable(String vegetableName, int rate) {
        return String.format(MESSAGE_TEMPLATE_PURCHASE, vegetableName, rate);
    }

    /**
     * Output buy land string.
     *
     * @param farmlandName the farmland name
     * @param rate         the rate
     * @return the string
     */
    public String outputBuyLand(String farmlandName, int rate) {
        return String.format(MESSAGE_TEMPLATE_PURCHASE, farmlandName, rate);
    }

    /**
     * Output sell string.
     *
     * @param totalSoldVegetables the total sold vegetables
     * @param totalGold           the total gold
     * @return the string
     */
    public String outputSell(int totalSoldVegetables, int totalGold) {
        String suffix = getSuffix(null, totalSoldVegetables);
        return String.format(MESSAGE_TEMPLATE_SALE, totalSoldVegetables, suffix, totalGold);
    }

    /**
     * Output harvest string.
     *
     * @param vegetable the vegetable
     * @param amount    the amount
     * @return the string
     */
    public String outputHarvest(Vegetable vegetable, int amount) {
        String suffix = getSuffix(vegetable, amount);
        return String.format(MESSAGE_TEMPLATE_HARVEST, amount, vegetable.getName(), suffix);
    }

    /**
     * Print before turn string.
     *
     * @param currentPlayerIndex the current player index
     * @return the string
     */
    public String printBeforeTurn(int currentPlayerIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        String playerName = this.config.getPlayerName(currentPlayerIndex);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(String.format(MESSAGE_TEMPLATE_TURN, playerName));
        GameTileBoard playersGameBoard = this.game.getGameTileBoard(currentPlayerIndex);
        int grownVegetableAmount = playersGameBoard.getGrownVegetableAmount();
        if (grownVegetableAmount > 0) {
            stringBuilder.append(System.lineSeparator());
            String suffix = getSuffix(null, grownVegetableAmount);
            String verb = grownVegetableAmount == 1 ? GROWN_SINGLE : GROWN_MULTIPLE;
            stringBuilder.append(String.format(MESSAGE_TEMPLATE_GROWN, grownVegetableAmount, suffix, verb));
        }
        int spoiledVegetableAmount = playersGameBoard.getSpoiledVegetableAmount();
        if (spoiledVegetableAmount > 0) {
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(MESSAGE_TEMPLATE_SPOILED);
        }
        return stringBuilder.toString();
    }

    /**
     * Print game result string.
     *
     * @return the string
     */
    public String printGameResult() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Integer> winners = new ArrayList<>();
        List<Integer> playerHighestGoldAmount = new ArrayList<>();
        int highestGoldAmount = 0;

        for (int playerIndex = 0; playerIndex < this.config.getPlayerCount(); playerIndex++) {
            int gold = this.game.getGameTileBoard(playerIndex).getBarn().getGoldStock();
            stringBuilder.append(
                String.format(MESSAGE_TEMPLATE_RESULT_PLAYER, playerIndex + 1, this.config.getPlayerName(playerIndex),
                    gold, System.lineSeparator())
            );
            if (gold >= this.config.getTargetGold()) {
                winners.add(playerIndex);
            }
            if (winners.isEmpty()) {
                if (gold > highestGoldAmount) {
                    playerHighestGoldAmount.clear();
                    playerHighestGoldAmount.add(playerIndex);
                    highestGoldAmount = gold;
                } else if (gold == highestGoldAmount) {
                    playerHighestGoldAmount.add(playerIndex);
                }
            }
        }
        winners = winners.isEmpty() ? playerHighestGoldAmount : winners;
        int numWinners = winners.size();
        if (winners.size() == 1) {
            stringBuilder.append(String.format(MESSAGE_TEMPLATE_HAS_WON, this.config.getPlayerName(winners.get(0))));
        } else {
            StringJoiner sj = new StringJoiner(LABEL_SEPERATOR_COMMA + Shell.BLANK_STRING, Shell.EMPTY_STRING,
                MESSAGE_TEMPLATE_HAVE_WON);
            for (int i: winners) {
                sj.add(this.config.getPlayerName(winners.get(i)));
            }
            String sjString = sj.toString();
            if (numWinners > 1) {
                int lastCommaIndex = sjString.lastIndexOf(LABEL_SEPERATOR_COMMA);
                sjString = sjString.substring(0, lastCommaIndex) + Shell.BLANK_STRING + LABEL_SEPERATOR_AND
                    + sjString.substring(lastCommaIndex + 1);
            }
            stringBuilder.append(sjString);
        }

        return stringBuilder.toString();
    }

    /**
     * Gets suffix.
     *
     * @param vegetable the vegetable
     * @param amount    the amount
     * @return the suffix
     */
    public static String getSuffix(Vegetable vegetable, int amount) {
        String suffix = Shell.EMPTY_STRING;
        if (amount == 0) {
            suffix = Shell.SUFFIX_S;
        } else if (amount > 1) {
            if (vegetable != null && vegetable.equals(Vegetable.TOMATO)) {
                suffix = Shell.SUFFIX_ES;
            } else {
                suffix = Shell.SUFFIX_S;
            }
        }
        return suffix;
    }


}
