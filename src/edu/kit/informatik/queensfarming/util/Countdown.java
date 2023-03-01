package edu.kit.informatik.queensfarming.util;

/**
 * The Countdown class is a utility class that provides functionality for a countdown mechanism.
 * It can be used to implement a turn-based system in a game, where each turn is represented by a round.
 * @author uuovz
 * @version 1.0
 */
public class Countdown {
    /**
     * The default value for no remaining turns.
     */
    private static final int NO_TURNS_REMAINING = 0;
    /**
     * The number of remaining turns for the countdown.
     */
    private int remainingTurns = NO_TURNS_REMAINING;
    /**
     * The number of rounds to restart the countdown.
     */
    private int restartRounds;
    /**
     * A flag to indicate if the countdown is active.
     */
    private boolean active = false;

    /**
     * Instantiates a new Countdown with a specified number of restart rounds.
     * @param restartRounds the number of rounds to restart the countdown
     */
    public Countdown(int restartRounds) {
        this.restartRounds = restartRounds;
    }

    /**
     * Instantiates a new Countdown with default values.
     */
    public Countdown() { }

    /**
     * Starts the countdown by setting the active flag to true and the remaining turns to the number of restart rounds.
     */
    public void start() {
        this.active = true;
        this.remainingTurns = restartRounds;
    }

    /**
     * Deactivates the countdown by setting the active flag to false and the remaining turns to the default value.
     */
    public void deactivate() {
        this.active = false;
        this.remainingTurns = NO_TURNS_REMAINING;
    }

    /**
     * Decreases the remaining turns by one if the countdown is active and there are remaining turns.
     * If the countdown is not active or the remaining turns are zero, the countdown is deactivated.
     */
    public void decreaseCountdown() {
        if (this.active && remainingTurns == NO_TURNS_REMAINING) {
            this.deactivate();
        }
        if (this.remainingTurns > NO_TURNS_REMAINING) {
            this.remainingTurns -= 1;
        }
    }

    /**
     * Returns whether the countdown is active.
     * @return true if the countdown is active, false otherwise
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Returns whether the countdown is finished, i.e., the countdown is active and there are no remaining turns.
     * @return true if the countdown is finished, false otherwise
     */
    public boolean isCountdownFinished() {
        return this.active && this.remainingTurns == NO_TURNS_REMAINING;
    }

    /**
     * Sets the number of rounds to restart the countdown.
     * This method also deactivates the countdown.
     * @param restartRounds the number of rounds to restart the countdown
     */
    public void setRestartRounds(int restartRounds) {
        this.restartRounds = restartRounds;
        this.deactivate();
    }

    /**
     * Gets remaining turns.
     * @return the remaining turns
     */
    public int getRemainingTurns() {
        return this.remainingTurns;
    }
}
