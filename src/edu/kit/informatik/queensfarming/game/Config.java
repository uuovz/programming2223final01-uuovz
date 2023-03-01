package edu.kit.informatik.queensfarming.game;

import java.util.ArrayList;
import java.util.List;

/**
 * The Config class represents the configuration of a Queens Farming Game.
 *
 * @author uuovz
 * @version 1.0
 */
public class Config {

    private int playerCount; //default value
    private final List<String> playerNames = new ArrayList<>();
    private int initialGold; //default value
    private int targetGold; //default value
    private long seed; //default value


    /**
     * Sets the number of players in the game.
     *
     * @param playerCount the number of players in the game
     */
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    /**
     * Adds the name of a player to the list of player names.
     *
     * @param playerName the name of the player
     */
    public void addPlayerName(String playerName) {
        this.playerNames.add(playerName);
    }

    /**
     * Sets the initial amount of gold for each player in the game.
     *
     * @param initialGold the initial amount of gold for each player in the game
     */
    public void setInitialGold(int initialGold) {
        this.initialGold = initialGold;
    }

    /**
     * Sets the target amount of gold for each player to win the game.
     *
     * @param targetGold the target amount of gold for each player to win the game
     */
    public void setTargetGold(int targetGold) {
        this.targetGold = targetGold;
    }

    /**
     * Sets the seed used to shuffle the tiles.
     *
     * @param seed the seed used to shuffle the tiles
     */
    public void setSeed(long seed) {
        this.seed = seed;
    }

    /**
     * Returns the number of players in the game.
     *
     * @return the number of players in the game
     */
    public int getPlayerCount() {
        return this.playerCount;
    }

    /**
     * Returns the name of a player at the specified index.
     *
     * @param index the index of the player name to return
     * @return the name of the player at the specified index
     */
    public String getPlayerName(int index) {
        return this.playerNames.get(index);
    }

    /**
     * Returns the initial amount of gold for each player in the game.
     *
     * @return the initial amount of gold for each player in the game
     */
    public int getInitialGold() {
        return this.initialGold;
    }

    /**
     * Returns the target amount of gold for each player to win the game.
     *
     * @return the target amount of gold for each player to win the game
     */
    public int getTargetGold() {
        return this.targetGold;
    }

    /**
     * Returns the seed used to shuffle the tiles.
     *
     * @return the seed used to shuffle the tiles
     */
    public long getSeed() {
        return this.seed;
    }

}
