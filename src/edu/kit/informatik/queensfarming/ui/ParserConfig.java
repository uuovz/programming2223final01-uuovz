package edu.kit.informatik.queensfarming.ui;

import edu.kit.informatik.queensfarming.GameException;
import edu.kit.informatik.queensfarming.game.Config;
import edu.kit.informatik.queensfarming.game.Executable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Parser config.
 * @author uuovz
 * @version 1.0
 */
public class ParserConfig implements Executable {

    private static final String OUTPUT_PLAYERNUMBER = "How many players?";
    private static final String OUTPUT_PLAYERNAME = "Enter the name of player %d:";
    private static final String OUTPUT_INITAL_GOLD = "With how much gold should each player start?";
    private static final String OUTPUT_FINAL_GOLD = "With how much gold should a player win?";
    private static final String OUTPUT_SEED = "Please enter the seed used to shuffle the tiles:";
    private static final String EXCEPTION_INVALID_NUMBER = "Please enter a natural number bigger zero.";
    private static final String EXCEPTION_INVALID_NUMBER_ZERO = "Please enter a natural number bigger equals zero.";
    private static final String EXCEPTION_INVALID_NAME = "Please enter valid name. Only letters allowed.";
    private static final String EXCEPTION_INVALID_SEED = "Please enter valid seed in range [−2147483648, 2147483647].";
    private static final Pattern PLAYERNAME_PATTERN = Pattern.compile("[A-Za-z]+");
    private final Config config;
    private boolean active;
    private IoType currentIoType;
    private int steps = 5;
    private int currentPlayer = 1;
    private boolean quit = false;


    /**
     * Instantiates a new Parser config.
     *
     * @param config the config
     */
    public ParserConfig(Config config) {
        this.config = config;
        this.active = true;
        this.currentIoType = IoType.OUTPUT;
    }

    /**
     * Parse config string.
     *
     * @param userInput the user input
     */
    public void parseConfig(String userInput) {
        if (steps == 5) {
            this.parsePlayerCount(userInput);
        } else if (steps == 4) {
            this.parsePlayerName(userInput);
        } else if (steps == 3) {
            this.parseInitalGold(userInput);
        } else if (steps == 2) {
            this.parseTargetGold(userInput);
        } else if (steps == 1) {
            this.parseSeed(userInput);
        }
    }

    /**
     * Gets output stream.
     *
     * @return the output stream
     */
    public String getOutputStream() {
        final String output;
        if (steps == 5) {
            output = OUTPUT_PLAYERNUMBER;
        } else if (steps == 4) {
            output = String.format(OUTPUT_PLAYERNAME, currentPlayer);
        } else if (steps == 3) {
            output = OUTPUT_INITAL_GOLD;
        } else if (steps == 2) {
            output = OUTPUT_FINAL_GOLD;
        } else if (steps == 1) {
            output = OUTPUT_SEED;
        } else {
            output = null;
        }
        this.changeIoType();
        return output;
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
        if (matcher.matches()) {
            this.config.addPlayerName(userInput);
            this.nextPlayerName();
        } else {
            throw new GameException(EXCEPTION_INVALID_NAME);
        }
    }

    private void parseInitalGold(String userInput) {
        if (!this.checkQuit(userInput)) {
            int num = this.getInteger(userInput);
            if (num < 0) {
                throw new GameException(EXCEPTION_INVALID_NUMBER_ZERO);
            }
            this.config.setInitalGold(num);
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

    /**
     * Gets current io type.
     *
     * @return the current io type
     */
    public IoType getCurrentIoType() {
        return this.currentIoType;
    }

    /**
     * Is active boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean isActive() { return this.active; }

    /**
     *
     */
    @Override
    public void quit() {
        this.active = false;
        this.quit = true;
    }

    /**
     *
     * @return xwad
     */
    public boolean quitted() {
        return this.quit;
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
        this.steps -= 1;
        changeIoType();
        if (this.steps == 0) {
            this.active = false;
        }
    }

    private void nextPlayerName() {
        if (this.currentPlayer < this.config.getPlayerCount()) {
            this.currentPlayer += 1;
            changeIoType();
        } else {
            nextStep();
        }
    }


}
