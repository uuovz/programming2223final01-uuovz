package edu.kit.informatik.queensfarming.rendering.game;

import edu.kit.informatik.queensfarming.rendering.Render;

/**
 * The RenderPixelArt class is a subclass of the Render class, which is responsible for rendering a pixel art
 * representing the Queens Farming game.
 *
 * @version 1.0
 * @author uuovz
 */
public class RenderPixelArt extends Render {
    private static final String PIXEL_ART
        =
        "                           _.-^-._    .--.    " + NEW_LINE
            +
            "                        .-'   _   '-. |__|    " + NEW_LINE
            +
            "                       /     |_|     \\|  |    " + NEW_LINE
            +
            "                      /               \\  |    " + NEW_LINE
            +
            "                     /|     _____     |\\ |    " + NEW_LINE
            +
            "                      |    |==|==|    |  |    " + NEW_LINE
            +
            "  |---|---|---|---|---|    |--|--|    |  |    " + NEW_LINE
            +
            "  |---|---|---|---|---|    |==|==|    |  |    " + NEW_LINE
            +
            "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + NEW_LINE
            +
            "^^^^^^^^^^^^^^^ QUEENS FARMING ^^^^^^^^^^^^^^^" + NEW_LINE
            +
            "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^";

    /**
     * This method returns the pixel art as a string.
     * @return a string representing the pixel art of the Queens Farming game
     */
    @Override
    public String render() {
        return PIXEL_ART;
    }
}
