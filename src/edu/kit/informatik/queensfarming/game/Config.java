package edu.kit.informatik.queensfarming.game;

import java.util.ArrayList;
import java.util.List;

public class Config {

    private int playerCount; //default value
    private final List<String> playerNames = new ArrayList<>();
    private int initalGold; //default value
    private int targetGold; //default value
    private long seed; //default value


    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public void addPlayerName(String playerName) {
        this.playerNames.add(playerName);
    }

    public void setInitalGold(int initalGold) {
        this.initalGold = initalGold;
    }

    public void setTargetGold(int targetGold) {
        this.targetGold = targetGold;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public int getPlayerCount() {
        return this.playerCount;
    }

    public String getPlayerName(int index) {
        return this.playerNames.get(index);
    }

    public int getInitalGold() {
        return this.initalGold;
    }

    public int getTargetGold() {
        return this.targetGold;
    }

    public long getSeed() {
        return this.seed;
    }

}
