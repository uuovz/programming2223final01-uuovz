package edu.kit.informatik.queensfarming.rendering.game;

import edu.kit.informatik.queensfarming.game.Game;
import edu.kit.informatik.queensfarming.rendering.Render;

/**
 * The abstract class RenderGame is a subclass of Render and is the parent class for all rendering classes
 * that render the Game object in some way.
 * @author uuovz
 * @version 1.0
 */
public abstract class RenderGame extends Render {

    /**
     * The constant LABEL_SEPERATOR.
     */
    protected static final String LABEL_SEPERATOR = ":";

    private final Game game;

    /**
     * Creates a new RenderGame object with the given Game object.
     * @param game the Game object to be rendered.
     */
    RenderGame(Game game) {
        this.game = game;
    }

    /**
     * Renders the Game object in some way.
     * @return a string representation of the rendered Game object.
     */
    @Override
    public abstract String render();

    /**
     * The Game object to be rendered.
     *
     * @return game object
     */
    public Game getGame() {
        return game;
    }
}
