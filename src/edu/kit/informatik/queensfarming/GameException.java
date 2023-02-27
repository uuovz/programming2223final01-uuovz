/*
 * Copyright (c) 2021, KASTEL. All rights reserved.
 */

package edu.kit.informatik.queensfarming;

/**
 * Encapsulates an exception that may occur during a {@link edu.kit.informatik.scrabble.game.ArithmeticScrabbleGame}.
 * Inspired by Thomas Weber
 *
 * @author Florian Schwieren
 * @version 1.0
 */
public class GameException extends IllegalArgumentException {
    private static final long serialVersionUID = -4491591333105161142L;

    /**
     * Instantiates a new {@link GameException} with the give message.
     *
     * @param message the message of the exception
     */
    public GameException(final String message) {
        super(message);
    }
}
