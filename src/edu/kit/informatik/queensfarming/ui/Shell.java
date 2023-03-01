package edu.kit.informatik.queensfarming.ui;

import edu.kit.informatik.queensfarming.GameException;
import edu.kit.informatik.queensfarming.game.Config;
import edu.kit.informatik.queensfarming.game.GameEngine;
import edu.kit.informatik.queensfarming.rendering.Renderable;
import edu.kit.informatik.queensfarming.rendering.game.RenderPixelArt;

import java.util.Scanner;

/**
 * The Shell class represents the command-line interface for the N-Queens game.
 * It provides the user with an interactive shell where they can input commands and receive output from the game engine.
 * The Shell class is responsible for initializing the game configuration,
 * parsing user input, and running the game engine.
 *
 * @author uuovz
 * @version 1.0
 */
public class Shell {

    /**
     * The QUIT_ARGUMENT constant represents the user input string used to quit the game.
     */
    public static final String QUIT_ARGUMENT = "quit";
    /**
     * The EMPTY_STRING constant is an empty string used for some outputs.
     */
    public static final String EMPTY_STRING = "";

    private final Renderable renderPixelArt = new RenderPixelArt();
    private final Config config = new Config();
    private final ParserConfig parserConfig = new ParserConfig(this.config);
    private GameEngine gameEngine;

    /**
     * This method starts the game shell.
     * It reads the user's input using a Scanner
     * object and parses it according to the current I/O type.
     */
    public void start() {
        System.out.println(this.renderPixelArt.render());
        Scanner scanner = new Scanner(System.in);
        this.setupLoop(scanner);
        if (!parserConfig.quitted()) {
            this.gameEngine = new GameEngine(this.config);
            this.gameLoop(scanner);
        }

        scanner.close();
    }

    private void setupLoop(Scanner scanner) {
        while (this.parserConfig.isActive()) {
            if (this.parserConfig.getCurrentIoType() == IoType.INPUT) {
                try {
                    String userInput = scanner.nextLine();
                    this.parserConfig.parseConfig(userInput);
                } catch (GameException exception) {
                    System.err.println(exception.getMessage());
                }
            } else {
                System.out.println(this.parserConfig.getOutputStream());
            }
        }
    }

    private void gameLoop(Scanner scanner) {
        while (gameEngine.isActive()) {
            if (gameEngine.getIoType() == IoType.INPUT) {
                try {
                    String userInput = scanner.nextLine();
                    String output = ParserGame.parseCommand(userInput, gameEngine);
                    if (output != null) {
                        System.out.println(output);
                    }
                } catch (GameException exception) {
                    System.err.println(exception.getMessage());
                }
            } else {
                System.out.println(gameEngine.getOutputStream());
            }
        }
    }
}
