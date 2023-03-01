package edu.kit.informatik.queensfarming.rendering.engine;

import edu.kit.informatik.queensfarming.entity.GameTileBoard;
import edu.kit.informatik.queensfarming.game.Config;
import edu.kit.informatik.queensfarming.game.Game;
import edu.kit.informatik.queensfarming.rendering.Render;

/**
 * The type Render turn.
 */
public class RenderTurn extends Render {

    private static final String MESSAGE_TEMPLATE_TURN = "It is %s's turn!";
    private static final String MESSAGE_TEMPLATE_GROWN = "%d vegetable%s %s grown since your last turn.";
    private static final String MESSAGE_TEMPLATE_SPOILED = "The vegetables in your barn are spoiled.";
    private static final String GROWN_SINGLE = "has";
    private static final String GROWN_MULTIPLE = "have";
    private final Game game;
    private final Config config;
    private int index = 0;

    /**
     * Instantiates a new Render turn.
     *
     * @param game   the game
     * @param config the config
     */
    public RenderTurn(Game game, Config config) {
        this.game = game;
        this.config = config;
    }

    @Override
    public String render() {
        StringBuilder stringBuilder = new StringBuilder();
        String playerName = this.config.getPlayerName(index);
        stringBuilder.append(NEW_LINE);
        stringBuilder.append(String.format(MESSAGE_TEMPLATE_TURN, playerName));
        GameTileBoard playersGameBoard = this.game.getGameTileBoard(index);
        int grownVegetableAmount = playersGameBoard.getGrownVegetableAmount();
        if (grownVegetableAmount > 0) {
            stringBuilder.append(NEW_LINE);
            String suffix = getSuffix(grownVegetableAmount);
            String verb = grownVegetableAmount == 1 ? GROWN_SINGLE : GROWN_MULTIPLE;
            stringBuilder.append(String.format(MESSAGE_TEMPLATE_GROWN, grownVegetableAmount, suffix, verb));
        }
        int spoiledVegetableAmount = playersGameBoard.getSpoiledVegetableAmount();
        if (spoiledVegetableAmount > 0) {
            stringBuilder.append(NEW_LINE);
            stringBuilder.append(MESSAGE_TEMPLATE_SPOILED);
        }
        return stringBuilder.toString();
    }

    /**
     * Sets index.
     *
     * @param index the index
     */
    public void setIndex(int index) {
        this.index = index;
    }
}
