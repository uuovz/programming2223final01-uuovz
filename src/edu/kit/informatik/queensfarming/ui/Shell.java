package edu.kit.informatik.queensfarming.ui;

import edu.kit.informatik.queensfarming.GameException;
import edu.kit.informatik.queensfarming.game.Config;
import edu.kit.informatik.queensfarming.game.GameEngine;

import java.util.Scanner;

/**
 * The type Shell.
 *
 * @author uuovz  Inspired by Thomas Weber
 * @version 1.0
 */
public class Shell {

    /**
     *
     */
    public static final String QUIT_ARGUMENT = "quit";

    /**
     * The constant BLANK_STRING.
     */
    public static final String BLANK_STRING = " ";
    /**
     * The start of an output string for a failed operation.
     */
    public static final String ERROR_START = "Error: ";

    /**
     * The constant ERROR_END.
     */
    private static final String ERROR_END = " Please try again!";
    private static final String PIXEL_ART
        =
        "                           _.-^-._    .--.    " + System.lineSeparator()
            +
        "                        .-'   _   '-. |__|    " + System.lineSeparator()
            +
        "                       /     |_|     \\|  |    " + System.lineSeparator()
            +
        "                      /               \\  |    " + System.lineSeparator()
            +
        "                     /|     _____     |\\ |    " + System.lineSeparator()
            +
        "                      |    |==|==|    |  |    " + System.lineSeparator()
            +
        "  |---|---|---|---|---|    |--|--|    |  |    " + System.lineSeparator()
            +
        "  |---|---|---|---|---|    |==|==|    |  |    " + System.lineSeparator()
            +
        "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + System.lineSeparator()
            +
        "^^^^^^^^^^^^^^^ QUEENS FARMING ^^^^^^^^^^^^^^^" + System.lineSeparator()
            +
        "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^";

    /**
     * Start.
     */
    public void start() {
        System.out.println(PIXEL_ART);
        Config config = new Config();
        ParserConfig parserConfig = new ParserConfig(config);
        Scanner scanner = new Scanner(System.in);
        //setup queensframing config
        while (parserConfig.isActive()) {
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
        if (!parserConfig.quitted()) {
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
        }

        scanner.close();

    }
}
