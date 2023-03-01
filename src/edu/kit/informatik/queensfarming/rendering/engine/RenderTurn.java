package edu.kit.informatik.queensfarming.rendering.engine;

import edu.kit.informatik.queensfarming.entity.GameTileBoard;
import edu.kit.informatik.queensfarming.game.Config;
import edu.kit.informatik.queensfarming.game.Game;
import edu.kit.informatik.queensfarming.rendering.Render;

/**
 * The type Render turn is a child class of Render that generates a string
 * describing a player's turn in the game.
 * @author uuovz
 * @version 1.0
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
     * Constructs a new RenderTurn object.
     * @param game the game being played
     * @param config the configuration settings for the game
     */
    public RenderTurn(Game game, Config config) {
        this.game = game;
        this.config = config;
    }

    /**
     * Generates a string describing the current player's turn in the game.
     * @return the string describing the current player's turn
     */
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
     * Sets the index of the current player.
     * @param index the index of the current player
     */
    public void setIndex(int index) {
        this.index = index;
    }
}
