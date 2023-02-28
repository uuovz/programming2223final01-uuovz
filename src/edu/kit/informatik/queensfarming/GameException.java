package edu.kit.informatik.queensfarming;

/**
 * The type Game exception.
 *
 * @author uuovz
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
