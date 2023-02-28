package edu.kit.informatik.queensfarming.game;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Config.
 *
 * @author uuovz
 * @version 1.0
 */
public class Config {

    private int playerCount; //default value
    private final List<String> playerNames = new ArrayList<>();
    private int initalGold; //default value
    private int targetGold; //default value
    private long seed; //default value


    /**
     * Sets player count.
     *
     * @param playerCount the player count
     */
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    /**
     * Add player name.
     *
     * @param playerName the player name
     */
    public void addPlayerName(String playerName) {
        this.playerNames.add(playerName);
    }

    /**
     * Sets inital gold.
     *
     * @param initalGold the inital gold
     */
    public void setInitalGold(int initalGold) {
        this.initalGold = initalGold;
    }

    /**
     * Sets target gold.
     *
     * @param targetGold the target gold
     */
    public void setTargetGold(int targetGold) {
        this.targetGold = targetGold;
    }

    /**
     * Sets seed.
     *
     * @param seed the seed
     */
    public void setSeed(long seed) {
        this.seed = seed;
    }

    /**
     * Gets player count.
     *
     * @return the player count
     */
    public int getPlayerCount() {
        return this.playerCount;
    }

    /**
     * Gets player name.
     *
     * @param index the index
     * @return the player name
     */
    public String getPlayerName(int index) {
        return this.playerNames.get(index);
    }

    /**
     * Gets inital gold.
     *
     * @return the inital gold
     */
    public int getInitalGold() {
        return this.initalGold;
    }

    /**
     * Gets target gold.
     *
     * @return the target gold
     */
    public int getTargetGold() {
        return this.targetGold;
    }

    /**
     * Gets seed.
     *
     * @return the seed
     */
    public long getSeed() {
        return this.seed;
    }

}
