package edu.kit.informatik.queensfarming.rendering.game;

import edu.kit.informatik.queensfarming.entity.Barn;
import edu.kit.informatik.queensfarming.entity.Farmland;
import edu.kit.informatik.queensfarming.entity.GameTile;
import edu.kit.informatik.queensfarming.entity.GameTileBoard;
import edu.kit.informatik.queensfarming.game.Game;
import edu.kit.informatik.queensfarming.util.Coordinates;

/**
 * A class that extends the abstract class `RenderGame` and provides a method to render the game board as a string.
 * @author uuovz
 * @version 1.0
 */
public class RenderGameBoard extends RenderGame {

    private static final String BARRIER = "|";
    private static final int TILE_WIDTH = 5;
    private static final int TILE_HEIGHT = 3;
    private static final String LABEL_NO_COUNTDOWN = "*";
    private static final String TEMPLATE_BARN_MIDDLE = " %s %s ";
    private static final String TEMPLATE_FARMLAND_UPPER = " %d/%d ";
    private static final String TEMPLATE_FARMLAND_MIDDLE = "  %s  ";
    private static final String TEMPLATE_FARMLAND_LOWER_A = " %s %s ";
    private static final String TEMPLATE_FARMLAND_LOWER_B = " %s %s";
    private static final String TEMPLATE_FARMLAND_LOWER_C = "%s %s";
    private static final String SHORTCUT = "B";
    private int index = 0;
    private GameTileBoard gameTileBoard;
    private boolean barrier;

    /**
     * Creates a new `RenderGameBoard` instance with the specified game.
     * @param game the `Game` instance to render.
     */
    public RenderGameBoard(Game game) {
        super(game);
    }


    /**
     * Renders the game board as a string.
     * @return a string representing the game board.
     */
    @Override
    public String render() {
        this.gameTileBoard = this.getGame().getGameTileBoard(this.index);
        int maxXCoordinate = gameTileBoard.getMaxXCoordinate();
        int maxYCoordinate = gameTileBoard.getMaxYCoordinate();
        int minXCoordinate = gameTileBoard.getMinXCoordinate();
        this.stringBuilder = new StringBuilder();
        for (int yIndex = ((maxYCoordinate * TILE_HEIGHT)) + TILE_HEIGHT - 1; yIndex >= Coordinates.ORIGIN_COORDINATES;
             yIndex--) {
            int yCoordinate = yIndex / TILE_HEIGHT;
            int xCoordinate = minXCoordinate;
            this.barrier = true;
            while (xCoordinate <= maxXCoordinate || barrier) {
                if (barrier) {
                    this.barrier(xCoordinate, yCoordinate);
                } else {
                    Coordinates coordinates = new Coordinates(xCoordinate, yCoordinate);
                    Farmland farmland = gameTileBoard.getFarmland(coordinates);
                    Barn barn = gameTileBoard.getBarn();
                    if (farmland != null) {
                        this.farmlandRow(yIndex, gameTileBoard.getFarmland(new Coordinates(xCoordinate, yCoordinate)));
                    } else if (barn.getCoordinates().equals(coordinates)) {
                        this.barnRow(yIndex, gameTileBoard.getBarn());
                    } else {
                        this.stringBuilder.append(BLANK_STRING.repeat(TILE_WIDTH));
                    }
                    xCoordinate++;
                    barrier = true;
                }
            }
            if (yIndex > 0) {
                this.stringBuilder.append(NEW_LINE);
            }
        }
        return this.stringBuilder.toString();
    }

    private void barrier(int xCoordinate, int yCoordinate) {
        GameTile left = this.gameTileBoard.getGameTile(new Coordinates(xCoordinate - 1, yCoordinate));
        GameTile right = this.gameTileBoard.getGameTile(new Coordinates(xCoordinate, yCoordinate));
        if (left != null || right != null) {
            this.stringBuilder.append(BARRIER);
        } else {
            this.stringBuilder.append(BLANK_STRING);
        }
        this.barrier = false;
    }

    private void barnRow(int yIndex, Barn barn) {
        int type = yIndex % TILE_HEIGHT;
        if (type == 1) {
            int remainingTurns = barn.getCountdown().getRemainingTurns();
            String remainingTurnsDisplay = remainingTurns > 0 ? String.valueOf(remainingTurns) : LABEL_NO_COUNTDOWN;
            this.stringBuilder.append(String.format(TEMPLATE_BARN_MIDDLE,  SHORTCUT, remainingTurnsDisplay));
            return;
        }
        this.stringBuilder.append(BLANK_STRING.repeat(TILE_WIDTH));
    }

    private void farmlandRow(int tileRowIndex, Farmland farmland) {
        int type = tileRowIndex % TILE_HEIGHT;
        if (type == 0) {
            this.stringBuilder.append(String.format(TEMPLATE_FARMLAND_UPPER, farmland.getBalance(),
                farmland.getFarmlandType().getCapacity()));
            return;
        }
        if (type == 1) {
            String vegetableShortcut = farmland.getPlantedVegetable() != null
                ? farmland.getPlantedVegetable().getShortcut() : BLANK_STRING;
            stringBuilder.append(String.format(TEMPLATE_FARMLAND_MIDDLE, vegetableShortcut));
            return;
        }
        String shortcut = farmland.getFarmlandType().getShortcut();
        int shortcutLength = shortcut.length();
        int remainingTurns = farmland.getCountdown().getRemainingTurns();
        String remainingTurnsDisplay = remainingTurns > 0 ? String.valueOf(remainingTurns) : LABEL_NO_COUNTDOWN;
        if (shortcutLength == 1) {
            stringBuilder.append(String.format(TEMPLATE_FARMLAND_LOWER_A, shortcut, remainingTurnsDisplay));
        } else if (shortcutLength == 2) {
            stringBuilder.append(String.format(TEMPLATE_FARMLAND_LOWER_B, shortcut, remainingTurnsDisplay));
        } else {
            stringBuilder.append(String.format(TEMPLATE_FARMLAND_LOWER_C, shortcut, remainingTurnsDisplay));
        }
    }

    /**
     * Sets the index of the game tile board to render.
     * @param index the index of the game tile board.
     */
    public void setIndex(int index) {
        this.index = index;
    }

}
