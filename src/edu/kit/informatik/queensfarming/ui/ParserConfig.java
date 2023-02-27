package edu.kit.informatik.queensfarming.ui;

import edu.kit.informatik.queensfarming.GameException;
import edu.kit.informatik.queensfarming.game.Config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserConfig {

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


    public ParserConfig(Config config) {
        this.config = config;
        this.active = false;
        this.currentIoType = IoType.OUTPUT;
    }

    public String parseConfig(String userInput) {
        final String output;
        switch (steps) {
            case 5 -> output = this.parsePlayerCount(userInput);
            case 4 -> output = this.parsePlayerName(userInput);
            case 3 -> output = this.parseInitalGold(userInput);
            case 2 -> output = this.parseTargetGold(userInput);
            case 1 -> output = this.parseSeed(userInput);
            default -> output = null;
        }
        return output;
    }

    public String getOutputStream() {
        final String output;
        switch (steps) {
            case 5 -> output = Shell.OUTPUT_PLAYERNUMBER;
            case 4 -> output = String.format(Shell.OUTPUT_PLAYERNAME, currentPlayer);
            case 3 -> output = Shell.OUTPUT_INITAL_GOLD;
            case 2 -> output = Shell.OUTPUT_FINAL_GOLD;
            case 1 -> output = Shell.OUTPUT_SEED;
            default -> output = null;
        }
        this.changeIoType();
        return output;
    }


    private String parsePlayerCount(String userInput) {
        int num = this.getInteger(userInput);
        if (num <= 0) {
            throw new GameException(EXCEPTION_INVALID_NUMBER);
        }
        this.config.setPlayerCount(num);
        this.nextStep();

        return null;
    }

    private String parsePlayerName(String userInput) {
        final Matcher matcher = PLAYERNAME_PATTERN.matcher(userInput);
        if (matcher.matches()) {
            this.config.addPlayerName(userInput);
            this.nextPlayerName();
        } else {
            throw new GameException(EXCEPTION_INVALID_NAME);
        }
        return null;
    }

    private String parseInitalGold(String userInput) {
        int num = this.getInteger(userInput);
        if (num < 0) {
            throw new GameException(EXCEPTION_INVALID_NUMBER_ZERO);
        }
        this.config.setInitalGold(num);
        this.nextStep();

        return null;
    }

    private String parseTargetGold(String userInput) {
        int num = this.getInteger(userInput);
        if (num <= 0) {
            throw new GameException(EXCEPTION_INVALID_NUMBER);
        }
        this.config.setTargetGold(num);
        this.nextStep();

        return null;
    }

    private String parseSeed(String userInput) {
        Long num = this.getLong(userInput);
        if (num != null) {
            this.config.setSeed(num);
            this.nextStep();
        }
        return null;
    }

    public IoType getCurrentIoType() {
        return this.currentIoType;
    }

    public boolean isActive() { return this.active; }

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
            this.active = true;
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
