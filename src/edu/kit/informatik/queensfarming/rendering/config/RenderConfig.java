package edu.kit.informatik.queensfarming.rendering.config;

import edu.kit.informatik.queensfarming.rendering.Render;
import edu.kit.informatik.queensfarming.ui.ParserConfig;

/**
 * The RenderConfig class is responsible for rendering the configuration menu of the game.
 * It extends the Render class and overrides its render() method.
 * @author uuovz
 * @version 1.0
 */
public class RenderConfig extends Render {

    private static final String OUTPUT_PLAYERNUMBER = "How many players?";
    private static final String OUTPUT_PLAYERNAME = "Enter the name of player %d:";
    private static final String OUTPUT_INITAL_GOLD = "With how much gold should each player start?";
    private static final String OUTPUT_FINAL_GOLD = "With how much gold should a player win?";
    private static final String OUTPUT_SEED = "Please enter the seed used to shuffle the tiles:";
    private int phase = 0;
    private int currentPlayer = 0;

    /**
     * Overrides the render() method of the Render class to display the appropriate message
     * for the current phase of the configuration menu.
     * @return a String containing the message to display for the current phase of the menu.
     */
    @Override
    public String render() {
        String output;
        if (phase == ParserConfig.STEP_PLAYERCOUNT) {
            output = OUTPUT_PLAYERNUMBER;
        } else if (phase == ParserConfig.STEP_NAME) {
            output = String.format(OUTPUT_PLAYERNAME, this.currentPlayer);
        } else if (phase == ParserConfig.STEP_INITIANAL_GOLD) {
            output = OUTPUT_INITAL_GOLD;
        } else if (phase == ParserConfig.STEP_FINAL_GOLD) {
            output = OUTPUT_FINAL_GOLD;
        } else if (phase == ParserConfig.STEP_SEED) {
            output = OUTPUT_SEED;
        } else {
            output = null;
        }
        return output;
    }

    /**
     * Sets the current phase of the configuration menu.
     * @param phase the phase to set.
     */
    public void setPhase(int phase) {
        this.phase = phase;
    }

    /**
     * Sets the current player of the configuration menu.
     * @param currentPlayer the currentPlayer to set.
     */
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
