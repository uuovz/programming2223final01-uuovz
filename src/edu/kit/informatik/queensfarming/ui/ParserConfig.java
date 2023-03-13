package edu.kit.informatik.queensfarming.ui;

import edu.kit.informatik.queensfarming.GameException;
import edu.kit.informatik.queensfarming.game.Config;
import edu.kit.informatik.queensfarming.game.Executable;
import edu.kit.informatik.queensfarming.rendering.config.RenderConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The ParserConfig class represents the parser for the game configuration. It is responsible for
 * parsing and validating user input for the game settings.
 * This class implements the Executable interface
 * @author uuovz
 * @version 1.0
 */
public class ParserConfig implements Executable {

    /**
     * Playercount number representation
     */
    public static final int STEP_PLAYERCOUNT = 5;
    /**
     * Name number representation
     */
    public static final int STEP_NAME = 4;
    /**
     * Initianal gold number representation
     */
    public static final int STEP_INITIANAL_GOLD = 3;
    /**
     * Final gold number representation
     */
    public static final int STEP_FINAL_GOLD = 2;
    /**
     * Seed number representation
     */
    public static final int STEP_SEED = 1;
    private static final String EXCEPTION_INVALID_NUMBER = "Please enter a natural number bigger zero.";
    private static final String EXCEPTION_INVALID_NUMBER_ZERO = "Please enter a natural number bigger equals zero.";
    private static final String EXCEPTION_INVALID_NAME = "Please enter valid name. Only letters allowed.";
    private static final String EXCEPTION_INVALID_SEED = "Please enter valid seed in range [−2147483648, 2147483647].";
    private static final Pattern PLAYERNAME_PATTERN = Pattern.compile("[A-Za-z]+");
    private final RenderConfig renderConfig = new RenderConfig();
    private final Config config;
    private boolean active;
    private IoType currentIoType;
    private int phase = STEP_PLAYERCOUNT;
    private int currentPlayer = 1;
    private boolean quit = false;


    /**
     * Creates a new ParserConfig object with the given game configuration.
     * @param config the Config object containing the game settings to parse
     */
    public ParserConfig(Config config) {
        this.config = config;
        this.active = true;
        this.currentIoType = IoType.OUTPUT;
        this.renderConfig.setPhase(this.phase);
        this.renderConfig.setCurrentPlayer(this.currentPlayer);
    }

    /**
     * Parses a configuration string according to the current phase of the game setup.
     * @param userInput the configuration string entered by the user.
     */
    public void parseConfig(String userInput) {
        if (phase == STEP_PLAYERCOUNT) {
            this.parsePlayerCount(userInput);
        } else if (phase == STEP_NAME) {
            this.parsePlayerName(userInput);
        } else if (phase == STEP_INITIANAL_GOLD) {
            this.parseInitalGold(userInput);
        } else if (phase == STEP_FINAL_GOLD) {
            this.parseTargetGold(userInput);
        } else if (phase == STEP_SEED) {
            this.parseSeed(userInput);
        }
    }

    /**
     * Gets the output stream in the form of a string.
     * @return A string representing the output stream generated by rendering the configuration entered by the user.
     */
    public String getOutputStream() {
        this.changeIoType();
        return renderConfig.render();
    }

    /**
     * Returns the current IO type.
     * @return the current IO type
     */
    public IoType getCurrentIoType() {
        return this.currentIoType;
    }

    /**
     * Returns whether or not the current game is active.
     * @return true if the game is active, false otherwise.
     */
    @Override
    public boolean isActive() { return this.active; }

    /**
     * Sets the game to inactive and sets the quit flag to true to signal to the game loop to exit.
     */
    @Override
    public void quit() {
        this.active = false;
        this.quit = true;
    }

    /**
     * Returns whether the user has requested to quit the game.
     * @return true if the user has requested to quit the game, false otherwise.
     */
    public boolean quitted() {
        return this.quit;
    }


    private void parsePlayerCount(String userInput) {
        if (!this.checkQuit(userInput)) {
            int num = this.getInteger(userInput);
            if (num <= 0) {
                throw new GameException(EXCEPTION_INVALID_NUMBER);
            }
            this.config.setPlayerCount(num);
            this.nextStep();
        }
    }

    private void parsePlayerName(String userInput) {
        final Matcher matcher = PLAYERNAME_PATTERN.matcher(userInput);
        if (!this.checkQuit(userInput)) {
            if (matcher.matches()) {
                this.config.addPlayerName(userInput);
                this.nextPlayerName();
            } else {
                throw new GameException(EXCEPTION_INVALID_NAME);
            }
        }
    }

    private void parseInitalGold(String userInput) {
        if (!this.checkQuit(userInput)) {
            int num = this.getInteger(userInput);
            if (num < 0) {
                throw new GameException(EXCEPTION_INVALID_NUMBER_ZERO);
            }
            this.config.setInitialGold(num);
            this.nextStep();
        }
    }

    private void parseTargetGold(String userInput) {
        if (!this.checkQuit(userInput)) {
            int num = this.getInteger(userInput);
            if (num <= 0) {
                throw new GameException(EXCEPTION_INVALID_NUMBER);
            }
            this.config.setTargetGold(num);
            this.nextStep();
        }

    }

    private void parseSeed(String userInput) {
        if (!this.checkQuit(userInput)) {
            Long num = this.getLong(userInput);
            if (num != null) {
                this.config.setSeed(num);
                this.nextStep();
            }
        }

    }

    private boolean checkQuit(String userInput) {
        if (userInput.equals(Shell.QUIT_ARGUMENT)) {
            this.quit();
            return true;
        }
        return false;
    }

    private void changeIoType() {
        if (this.currentIoType == IoType.INPUT) {
            this.currentIoType = IoType.OUTPUT;
        } else {
            this.currentIoType = IoType.INPUT;
        }
    }

    private Integer getInteger(String userInput) {
        try {
            return Integer.parseInt(userInput);
        } catch (NumberFormatException error) {
            throw new GameException(EXCEPTION_INVALID_NUMBER);
        }

    }

    private Long getLong(String userInput) {
        long validNum;
        try {
            validNum = Integer.parseInt(userInput);
        } catch (NumberFormatException error) {
            throw new GameException(EXCEPTION_INVALID_SEED);
        }
        return validNum;
    }

    private void nextStep() {
        this.phase -= 1;
        this.renderConfig.setPhase(this.phase);
        changeIoType();
        if (this.phase == STEP_SEED - 1) {
            this.active = false;
        }
    }

    private void nextPlayerName() {
        if (this.currentPlayer < this.config.getPlayerCount()) {
            this.currentPlayer += 1;
            this.renderConfig.setCurrentPlayer(this.currentPlayer);
            changeIoType();
        } else {
            nextStep();
        }
    }


}
