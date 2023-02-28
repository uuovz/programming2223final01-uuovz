package edu.kit.informatik.queensfarming.util;

/**
 * The type Countdown.
 * @author uuovz
 * @version 1.0
 */
public class Countdown {

    private static final int NO_TURNS_REMAINING = 0;
    private int remainingTurns = NO_TURNS_REMAINING;
    private int restartRounds;
    private boolean active = false;

    /**
     * Instantiates a new Countdown.
     *
     * @param restartRounds the restart rounds
     */
    public Countdown(int restartRounds) {
        this.restartRounds = restartRounds;
    }

    /**
     * Instantiates a new Countdown.
     */
    public Countdown() { }

    /**
     * Start.
     */
    public void start() {
        this.active = true;
        this.remainingTurns = restartRounds;
    }

    /**
     * Deactivate.
     */
    public void deactivate() {
        this.active = false;
        this.remainingTurns = NO_TURNS_REMAINING;
    }

    /**
     * Decrease countdown.
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
     * Is active boolean.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Is countdown finished boolean.
     *
     * @return the boolean
     */
    public boolean isCountdownFinished() {
        return this.active && this.remainingTurns == NO_TURNS_REMAINING;
    }

    /**
     * Sets restart rounds.
     *
     * @param restartRounds the restart rounds
     */
    public void setRestartRounds(int restartRounds) {
        this.restartRounds = restartRounds;
        this.deactivate();
    }

    /**
     * Gets remaining turns.
     *
     * @return the remaining turns
     */
    public int getRemainingTurns() {
        return this.remainingTurns;
    }


}
