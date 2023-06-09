package edu.kit.informatik.queensfarming.ui;

import edu.kit.informatik.queensfarming.GameException;
import edu.kit.informatik.queensfarming.entity.Vegetable;
import edu.kit.informatik.queensfarming.game.GameEngine;
import edu.kit.informatik.queensfarming.util.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An enum representing the different commands that can be executed in the Queen's Farming game.
 */
public enum ParserGame {

    /**
     * The Quit command.
     * Exits the game.
     */
    QUIT("^" + Shell.QUIT_ARGUMENT) {
        @Override public String execute(final String userInput, final GameEngine gameEngine) {
            gameEngine.quit();
            return null;
        }
    },
    /**
     * The Buy Vegetable command.
     * Buys a vegetable from the market.
     */
    BUY_VEGETABLE("^buy vegetable" + ParserGame.COMMAND_ALL) {
        @Override public String execute(final String userInput, final GameEngine gameEngine) {
            String[] arguments = ParserGame.splitArguments(ParserGame.getUserArguments(this, userInput));
            if (arguments == null || arguments.length != ARGUMENTS_BUY_VEGETABLE) {
                throw new GameException(EXCEPTION_ONE_NAME_EXPECTED);
            }
            Vegetable vegetable = ParserGame.getVegetable(arguments[0]);
            return gameEngine.buyVegetable(vegetable);
        }
    },
    /**
     * The Buy Land command.
     * Buys a land tile on the game board.
     */
    BUY_LAND("^buy land" + ParserGame.COMMAND_ALL) {
        @Override public String execute(final String userInput, final GameEngine gameEngine) {
            String[] arguments = ParserGame.splitArguments(ParserGame.getUserArguments(this, userInput));
            if (arguments == null || arguments.length != ARGUMENTS_BUY_LAND) {
                throw new GameException(EXCEPTION_TWO_COORDINATES);
            }
            final List<Integer> coordinateNumeric = new ArrayList<>();
            for (String argument: arguments) {
                coordinateNumeric.add(ParserGame.getNumeric(argument));
            }
            ParserGame.validateNaturalNumber(coordinateNumeric.get(1));
            Coordinates coordinates = new Coordinates(coordinateNumeric.get(0), coordinateNumeric.get(1));
            return gameEngine.buyLand(coordinates);
        }
    },
    /**
     * The Sell command.
     * Sells a vegetable from the player's barn.
     */
    SELL("^sell" + ParserGame.COMMAND_ALL) {
        @Override public String execute(final String userInput, final GameEngine gameEngine) {
            String[] arguments = ParserGame.splitArguments(ParserGame.getUserArguments(this, userInput));

            if (arguments != null && arguments.length == 1 && arguments[0].equals(ARGUMENT_ALL)) {
                return gameEngine.sell();
            }
            final List<Vegetable> vegetables = new ArrayList<>();
            if (arguments == null) {
                return gameEngine.sell(vegetables);
            }
            for (String uiVegetable: arguments) {
                if (!uiVegetable.equals(Shell.EMPTY_STRING)) {
                    vegetables.add(ParserGame.getVegetable(uiVegetable));
                }
            }
            return gameEngine.sell(vegetables);
        }
    },
    /**
     * The Plant command.
     * Plants a vegetable on a land tile on the game board.
     */
    PLANT("^plant" + ParserGame.COMMAND_ALL) {
        @Override public String execute(final String userInput, final GameEngine gameEngine) {
            String[] arguments = ParserGame.splitArguments(ParserGame.getUserArguments(this, userInput));
            final List<Integer> coordinateNumeric = new ArrayList<>();
            if (arguments == null || arguments.length != ARGUMENTS_PLANT) {
                throw new GameException(EXCEPTION_PLANT_THREE_ARGUMENTS);
            }
            for (int i = 0; i < 2; i++) {
                coordinateNumeric.add(ParserGame.getNumeric(arguments[i]));
            }
            ParserGame.validateNaturalNumber(coordinateNumeric.get(1));
            Coordinates coordinates = new Coordinates(coordinateNumeric.get(0), coordinateNumeric.get(1));
            Vegetable vegetable = ParserGame.getVegetable(arguments[2]);

            return gameEngine.plant(coordinates, vegetable);
        }
    },
    /**
     * The Harvest command.
     * Harvest a vegetable on a farmland on the game board.
     */
    HARVEST("^harvest" + ParserGame.COMMAND_ALL) {
        @Override public String execute(final String userInput, final GameEngine gameEngine) {
            String[] arguments = ParserGame.splitArguments(ParserGame.getUserArguments(this, userInput));
            if (arguments == null || arguments.length != ARGUMENTS_HARVEST) {
                throw new GameException(EXCEPTION_HARVEST_THREE_ARGUMENTS);
            }
            final List<Integer> coordinateNumeric = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                coordinateNumeric.add(ParserGame.getNumeric(arguments[i]));
            }
            ParserGame.validateNaturalNumber(coordinateNumeric.get(1));
            Coordinates coordinates = new Coordinates(coordinateNumeric.get(0), coordinateNumeric.get(1));
            int amount = ParserGame.getNumeric(arguments[2]);
            ParserGame.validateNaturalNumber(amount);
            return gameEngine.harvest(coordinates, amount);
        }
    },
    /**
     * The Show market command.
     * Shows current market conditions
     */
    SHOW_MARKET("^show market") {
        @Override public String execute(final String userInput, final GameEngine gameEngine) {
            return gameEngine.showMarket();
        }
    },
    /**
     * The Show barn command.
     * Shows barn conditions
     */
    SHOW_BARN("^show barn") {
        @Override public String execute(final String userInput, final GameEngine gameEngine) {
            return gameEngine.showBarn();
        }
    },
    /**
     * The Show board command.
     * Shows board conditions.
     */
    SHOW_BOARD("^show board") {
        @Override public String execute(final String userInput, final GameEngine gameEngine) {
            return gameEngine.showBoard();
        }
    },
    /**
     * The End turn command.
     * Ends turn of user.
     */
    END_TURN("^end turn") {
        @Override public String execute(final String userInput, final GameEngine gameEngine) {
            return gameEngine.endTurn();
        }
    };

    private static final String COMMAND_SEPARATOR = " ";
    private static final String BLANK_STRING = " ";
    private static final String COMMAND_ALL = ".*";
    private static final int ARGUMENTS_BUY_VEGETABLE = 1;
    private static final int ARGUMENTS_BUY_LAND = 2;
    private static final int ARGUMENTS_PLANT = 3;
    private static final int ARGUMENTS_HARVEST = 3;
    private static final String EXCEPTION_ONE_NAME_EXPECTED = "One vegetable name expected.";
    private static final String EXCEPTION_TWO_COORDINATES = "Only two Coordinates allowed.";
    private static final String EXCEPTION_PLANT_THREE_ARGUMENTS = "Three arguments needed for plant command.";
    private static final String EXCEPTION_HARVEST_THREE_ARGUMENTS = "Three arguments needed for harvest command.";
    private static final String EXCEPTION_SEPERATE_BLANKS = "Please seperate commands with blank \" \".";
    private static final String EXCEPTION_TEMPLATE_VEGETABLE_UNKNOWN = "Vegetable \"%s\" unknown.";
    private static final String EXCEPTION_TEMPLATE_NUMERIC = "\"%s\" is not numeric.";
    private static final String EXCEPTION_TEMPLATE_NEGATIVE = "\"%s\" cannot be negative";
    private static final String EXCEPTION_INVALID_COMMAND = "Invalid command.";
    private static final String EXCEPTION_BLANK_CLOSING = "Blank not allowed at the end of a command.";
    private static final String ARGUMENT_ALL = "all";
    private final String uICommand;
    private final Pattern pattern;

    /**
     * This class represents a parser for a game command. It takes a regular expression as input
     * and compiles it into a pattern object, which can be used to match strings against the command
     * @param uICommand regex of command
     */
    ParserGame(String uICommand) {
        this.uICommand = uICommand;
        this.pattern = Pattern.compile(uICommand);
    }

    private static String getUserArguments(ParserGame command, String userInput) {
        //check if last character is blank
        if (userInput.endsWith(BLANK_STRING)) {
            throw new GameException(EXCEPTION_BLANK_CLOSING);
        }

        return userInput.replaceAll(command.uICommand.replace(COMMAND_ALL, Shell.EMPTY_STRING),
            Shell.EMPTY_STRING);
    }

    private static String[] splitArguments(String arguments) {
        if (!arguments.isEmpty()) {
            if (!String.valueOf(arguments.charAt(0)).equals(COMMAND_SEPARATOR)) {
                throw new GameException(EXCEPTION_SEPERATE_BLANKS);
            }
            return arguments.substring(1).split(COMMAND_SEPARATOR);
        }
        return null;
    }

    private static Vegetable getVegetable(String uiVegetable) {
        for (Vegetable vegetable : Vegetable.values()) {
            if (uiVegetable.equals(vegetable.getName())) {
                return vegetable;
            }
        }
        throw new GameException(String.format(EXCEPTION_TEMPLATE_VEGETABLE_UNKNOWN, uiVegetable));
    }

    private static int getNumeric(String uiNumber) {
        try {
            return Integer.parseInt(uiNumber);
        } catch (NumberFormatException error) {
            throw new GameException(String.format(EXCEPTION_TEMPLATE_NUMERIC, uiNumber));
        }
    }

    private static void validateNaturalNumber(int uiNumber) {
        if (uiNumber < 0) {
            throw new GameException(String.format(EXCEPTION_TEMPLATE_NEGATIVE, uiNumber));
        }
    }

    /**
     * Parses the user input command and executes the appropriate command based on the parsed result.
     * @param userInput the user input command to be parsed
     * @param gameEngine the game engine that manages the game state
     * @return the result of the executed command
     * @throws GameException if the user input command is invalid and cannot be parsed
     */
    public static String parseCommand(final String userInput, final GameEngine gameEngine) {
        for (final ParserGame command : values()) {
            final Matcher matcher = command.pattern.matcher(userInput);
            if (matcher.matches()) {
                return command.execute(userInput, gameEngine);
            }
        }
        throw new GameException(EXCEPTION_INVALID_COMMAND);
    }

    /**
     * Executes the parsed user input command.
     * @param userInput the user input command to be executed
     * @param gameEngine the game engine that manages the game state
     * @return the result of the executed command
     */
    abstract String execute(String userInput, GameEngine gameEngine);
}
