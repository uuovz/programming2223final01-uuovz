package edu.kit.informatik.queensfarming.ui;

import edu.kit.informatik.queensfarming.GameException;
import edu.kit.informatik.queensfarming.game.Config;
import edu.kit.informatik.queensfarming.game.GameEngine;

import java.util.Scanner;

/**
 *
 * @author uuovz
 * Inspired by Thomas Weber
 *
 */
public class Shell {

    /**
     * The separator between a command and the parameters.
     */
    public static final String COMMAND_SEPARATOR = " ";
    public static final String COMMAND_ALL = ".*";
    public static final String SUFFIX_S = "s";
    public static final String SUFFIX_ES = "es";
    public static final String OUTPUT_PLAYERNUMBER = "How many players?";
    public static final String OUTPUT_PLAYERNAME = "Enter the name of player %d:";
    public static final String OUTPUT_INITAL_GOLD = "With how much gold should each player start?";
    public static final String OUTPUT_FINAL_GOLD = "With how much gold should a player win?";
    public static final String OUTPUT_SEED = "Please enter the seed used to shuffle the tiles:";

    /**
     * The separator between lines of output.
     */
    public static final String LINE_SEPARATOR = System.lineSeparator();
    /**
     * An empty string used for some outputs.
     */
    public static final String EMPTY_STRING = "";

    public static final String BLANK_STRING = " ";
    /**
     * The start of an output string for a failed operation.
     */
    public static final String ERROR_START = "Error: ";

    public static final String ERROR_END = " Please try again!";
    private static final String PIXEL_ART
        =
        "                           _.-^-._    .--.    " + LINE_SEPARATOR
            +
        "                        .-'   _   '-. |__|    " + LINE_SEPARATOR
            +
        "                       /     |_|     \\|  |    " + LINE_SEPARATOR
            +
        "                      /               \\  |    " + LINE_SEPARATOR
            +
        "                     /|     _____     |\\ |    " + LINE_SEPARATOR
            +
        "                      |    |==|==|    |  |    " + LINE_SEPARATOR
            +
        "  |---|---|---|---|---|    |--|--|    |  |    " + LINE_SEPARATOR
            +
        "  |---|---|---|---|---|    |==|==|    |  |    " + LINE_SEPARATOR
            +
        "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + LINE_SEPARATOR
            +
        "^^^^^^^^^^^^^^^ QUEENS FARMING ^^^^^^^^^^^^^^^" + LINE_SEPARATOR
            +
        "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^";

    public void start() {
        System.out.println(PIXEL_ART);
        Config config = new Config();
        ParserConfig parserConfig = new ParserConfig(config);
        Scanner scanner = new Scanner(System.in);
        //setup queensframing config
        while (!parserConfig.isActive()) {
            if (parserConfig.getCurrentIoType() == IoType.INPUT) {
                try {
                    String userInput = scanner.nextLine();
                    parserConfig.parseConfig(userInput);
                } catch (GameException exception) {
                    System.err.println(ERROR_START + exception.getMessage() + ERROR_END);
                }
            } else {
                System.out.println(parserConfig.getOutputStream());
            }
        }
        GameEngine gameEngine = new GameEngine(config);
        while (gameEngine.isActive()) {
            if (gameEngine.getIoType() == IoType.INPUT) {
                try {
                    String userInput = scanner.nextLine();
                    String output = ParserGame.parseCommand(userInput, gameEngine);
                    if (output != null) {
                        System.out.println(output);
                    }
                } catch (GameException exception) {
                    System.err.println(ERROR_START + exception.getMessage() + ERROR_END);
                }
            } else {
                System.out.println(gameEngine.getOutputStream());
            }
        }

        scanner.close();

    }
}
