package edu.kit.informatik.queensfarming.game.rendering;

import edu.kit.informatik.queensfarming.entity.Barn;
import edu.kit.informatik.queensfarming.entity.Farmland;
import edu.kit.informatik.queensfarming.entity.GameTile;
import edu.kit.informatik.queensfarming.entity.GameTileBoard;
import edu.kit.informatik.queensfarming.entity.Vegetable;
import edu.kit.informatik.queensfarming.game.Game;
import edu.kit.informatik.queensfarming.ui.Shell;
import edu.kit.informatik.queensfarming.util.Coordinates;
import edu.kit.informatik.queensfarming.util.Countdown;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The type Game render.
 *
 * @author uuovz
 * @version 1.0
 */
public class GameRender {

    private static final String OUTPUT_BARN = "Barn";
    private static final String OUTPUT_BARN_SPOILS = "(spoils in %d turn%s)";
    private static final String BARRIER = "|";
    private static final String EMPTY_STRING = "";
    private static final String SUFFIX_S = "s";
    private static final String SUFFIX_ES = "es";
    private static final int TILE_WIDTH = 5;
    private static final int TILE_HEIGHT = 3;
    private static final String LABEL_SUM = "Sum:";
    private static final String LABEL_GOLD = "Gold:";
    private static final String LABEL_NO_COUNTDOWN = "*";
    private static final String LABEL_BLANK = " ";
    private static final String LABEL_SEPERATOR = ":";

    private final Game game;

    /**
     * Instantiates a new Game render.
     *
     * @param game the game
     */
    public GameRender(Game game) {
        this.game = game;
    }

    /**
     * Show barn string.
     *
     * @param gameBoardIndex the game board index
     * @return the string
     */
    public String showBarn(int gameBoardIndex) {
        Barn barn = this.game.getGameTileBoard(gameBoardIndex).getBarn();
        Countdown countdown = barn.getCountdown();
        int goldStock = barn.getGoldStock();

        List<Vegetable> sortedList = this.getSortedVegetableList(barn);
        int maxNameLength = 4; //Gold hat die Länge 4
        int maxQuantityLength = String.valueOf(goldStock).length();

        StringBuilder stringBuilder = new StringBuilder();
        int remainingTurns = countdown.getRemainingTurns();
        String suffix = remainingTurns == 1 ? EMPTY_STRING : SUFFIX_S;
        stringBuilder.append(OUTPUT_BARN);
        if (barn.vegetableInStock()) {
            stringBuilder.append(String.format(LABEL_BLANK + OUTPUT_BARN_SPOILS, remainingTurns, suffix));
            stringBuilder.append(System.lineSeparator());

            for (Vegetable vegetable : sortedList) {
                suffix = vegetable.equals(Vegetable.TOMATO) ? SUFFIX_ES : SUFFIX_S;
                maxNameLength = Math.max(maxNameLength, (vegetable.getName() + suffix).length());
                maxQuantityLength = Math.max(maxQuantityLength, String.valueOf(barn.getStockOf(vegetable)).length());
            }

            int totalSum = 0;
            for (Vegetable vegetable : sortedList) {
                int quantity = barn.getStockOf(vegetable);
                suffix = vegetable.equals(Vegetable.TOMATO) ? SUFFIX_ES : SUFFIX_S;
                stringBuilder.append(
                        String.format("%-" + (maxNameLength + 1) + "s", (vegetable.getName() + suffix)
                            + LABEL_SEPERATOR)
                    )
                    .append(String.format("%" + (maxQuantityLength + 1) + "d", quantity))
                    .append(System.lineSeparator());
                totalSum += quantity;
            }

            stringBuilder.append("-".repeat(maxNameLength + maxQuantityLength + 2))
                .append(System.lineSeparator())
                .append(String.format("%-" + (maxNameLength + 1) + "s", LABEL_SUM))
                .append(String.format("%" + (maxQuantityLength + 1) + "d", totalSum))
                .append(System.lineSeparator().repeat(2));
        }

        else {
            stringBuilder.append(System.lineSeparator());
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
        Collections.sort(sortedList, new Comparator<>() {
            @Override
            public int compare(Vegetable v1, Vegetable v2) {
                int quantity1 = barn.getStockOf(v1);
                int quantity2 = barn.getStockOf(v2);
                if (quantity1 == quantity2) {
                    return v1.getName().compareTo(v2.getName());
                }
                return quantity1 - quantity2;
            }
        });
        return sortedList;
    }


    /**
     * Show board string.
     *
     * @param gameBoardIndex the game board index
     * @return the string
     */
    public String showBoard(int gameBoardIndex) {
        GameTileBoard gameTileBoard = this.game.getGameTileBoard(gameBoardIndex);
        int maxXCoordinate = gameTileBoard.getMaxXCoordinate();
        int maxYCoordinate = gameTileBoard.getMaxYCoordinate();
        int minXCoordinate = gameTileBoard.getMinXCoordinate();
        StringBuilder stringBuilder = new StringBuilder();
        for (int yIndex = ((maxYCoordinate * TILE_HEIGHT)) + TILE_HEIGHT - 1; yIndex >= Coordinates.ORIGIN_COORDINATES;
             yIndex--) {
            int yCoordinate = yIndex / TILE_HEIGHT;
            int xCoordinate = minXCoordinate;
            boolean barrier = true;
            while (xCoordinate <= maxXCoordinate || barrier) {
                if (barrier) {
                    GameTile left = gameTileBoard.getGameTile(new Coordinates(xCoordinate - 1, yCoordinate));
                    GameTile right = gameTileBoard.getGameTile(new Coordinates(xCoordinate, yCoordinate));
                    if (left != null || right != null) {
                        stringBuilder.append(BARRIER);
                    } else {
                        stringBuilder.append(LABEL_BLANK);
                    }
                    barrier = false;
                } else {
                    GameTile gameTile = gameTileBoard.getGameTile(new Coordinates(xCoordinate, yCoordinate));
                    if (gameTile instanceof Farmland) {
                        stringBuilder.append(
                            this.farmlandRow(yIndex, gameTileBoard.getFarmland(
                                new Coordinates(xCoordinate, yCoordinate))
                            )
                        );
                    } else if (gameTile instanceof Barn) {
                        stringBuilder.append(
                            barnRow(yIndex, gameTileBoard.getBarn())
                        );
                    } else {
                        stringBuilder.append(LABEL_BLANK.repeat(TILE_WIDTH));
                    }
                    xCoordinate++;
                    barrier = true;
                }
            }
            if (yIndex > 0) {
                stringBuilder.append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }

    private String barnRow(int yIndex, Barn barn) {
        int type = yIndex % TILE_HEIGHT;
        if (type == 1) {
            int remainingTurns = barn.getCountdown().getRemainingTurns();
            String remainingTurnsDisplay = remainingTurns > 0 ? String.valueOf(remainingTurns) : LABEL_NO_COUNTDOWN;
            return String.format(" %s %s ",  Barn.SHORTCUT, remainingTurnsDisplay);
        }
        return LABEL_BLANK.repeat(TILE_WIDTH);
    }

    private String farmlandRow(int tileRowIndex, Farmland farmland) {
        int type = tileRowIndex % TILE_HEIGHT;
        if (type == 0) {
            return String.format(" %d/%d ", farmland.getBalance(), farmland.getFarmlandType().getCapacity());
        }
        if (type == 1) {
            String vegetableShortcut = farmland.getPlantedVegetable() != null
                ? farmland.getPlantedVegetable().getShortcut() : LABEL_BLANK;
            return String.format("  %s  ", vegetableShortcut);
        }
        String shortcut = farmland.getFarmlandType().getShortcut();
        int shortcutLength = shortcut.length();
        int remainingTurns = farmland.getCountdown().getRemainingTurns();
        String remainingTurnsDisplay = remainingTurns > 0 ? String.valueOf(remainingTurns) : LABEL_NO_COUNTDOWN;
        if (shortcutLength == 1) {
            return String.format(" %s %s ", shortcut, remainingTurnsDisplay);
        } else if (shortcutLength == 2) {
            return String.format(" %s %s", shortcut, remainingTurnsDisplay);
        } else {
            return String.format("%s %s", shortcut, remainingTurnsDisplay);
        }
    }

    /**
     * Show market string.
     *
     * @return the string
     */
    public String showMarket() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
            String.format("%s%s" + LABEL_SEPERATOR + " %d%s", Vegetable.MUSHROOM.getName(), Shell.SUFFIX_S,
                this.game.getMarket(Vegetable.MUSHROOM).getRate(Vegetable.MUSHROOM), System.lineSeparator()))
            .append(String.format("%s%s" + LABEL_SEPERATOR +  " %4d%s", Vegetable.CARROT.getName(), Shell.SUFFIX_S,
                this.game.getMarket(Vegetable.CARROT).getRate(Vegetable.CARROT), System.lineSeparator()))
            .append(String.format("%s%s" + LABEL_SEPERATOR + " %3d%s", Vegetable.TOMATO.getName(), Shell.SUFFIX_ES,
                this.game.getMarket(Vegetable.TOMATO).getRate(Vegetable.TOMATO), System.lineSeparator()))
            .append(String.format("%s%s" + LABEL_SEPERATOR +  " %5d", Vegetable.SALAD.getName(), Shell.SUFFIX_S,
                this.game.getMarket(Vegetable.SALAD).getRate(Vegetable.SALAD)));
        return stringBuilder.toString();
    }

}
