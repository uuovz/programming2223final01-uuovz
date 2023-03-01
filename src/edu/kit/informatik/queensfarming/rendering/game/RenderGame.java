package edu.kit.informatik.queensfarming.rendering.game;

import edu.kit.informatik.queensfarming.game.Game;
import edu.kit.informatik.queensfarming.rendering.Render;

/**
 *
 * @author uuovz
 * @version 1.0
 */
public abstract class RenderGame extends Render {

    /**
     * The constant EMPTY_STRING.
     */
    protected static final String EMPTY_STRING = "";
    /**
     * The constant LABEL_BLANK.
     */
    protected static final String LABEL_BLANK = " ";
    /**
     * The constant LABEL_SEPERATOR.
     */
    protected static final String LABEL_SEPERATOR = ":";

    /**
     * The Game.
     */
    protected final Game game;

    /**
     * Instantiates a new Render game.
     *
     * @param game the game
     */
    RenderGame(Game game) {
        this.game = game;
    }

    @Override
    public abstract String render();
}
