package edu.kit.informatik.queensfarming.entity;

import edu.kit.informatik.queensfarming.util.Coordinates;

public abstract class GameTile implements Roundable {

    private Coordinates coordinates;

    GameTile(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() { return this.coordinates; }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public abstract void nextRound();




}
