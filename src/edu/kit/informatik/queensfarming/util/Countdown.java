package edu.kit.informatik.queensfarming.util;

public class Countdown {

    private static final int NO_TURNS_REMAINING = 0;
    private int remainingTurns = NO_TURNS_REMAINING;
    private int restartRounds;
    private boolean active = false;

    public Countdown(int restartRounds) {
        this.restartRounds = restartRounds;
    }

    public Countdown() { }

    public void start() {
        this.active = true;
        this.remainingTurns = restartRounds;
    }

    public void deactivate() {
        this.active = false;
        this.remainingTurns = NO_TURNS_REMAINING;
    }

    public void decreaseCountdown() {
        if (this.active && remainingTurns == NO_TURNS_REMAINING) {
            this.deactivate();
        }
        if (this.remainingTurns > NO_TURNS_REMAINING) {
            this.remainingTurns -= 1;
        }
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean isCountdownFinished() {
        return this.active && this.remainingTurns == NO_TURNS_REMAINING;
    }

    public void setRestartRounds(int restartRounds) {
        this.restartRounds = restartRounds;
        this.deactivate();
    }

    public int getRemainingTurns() {
        return this.remainingTurns;
    }


}
