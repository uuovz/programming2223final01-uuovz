package edu.kit.informatik.queensfarming.entity;

import edu.kit.informatik.queensfarming.util.Coordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The FarmlandDeck class represents a deck of Farmland tiles, used in the Farming Game.
 * The FarmlandDeck is composed of Farmland objects, each of which has a specific FarmlandType and a set of coordinates.
 *
 * @author uuovz
 * @version 1.0
 */
public class FarmlandDeck {

    private static final int TOP_FARMLAND = 0;

    private final List<Farmland> farmlandDeck = new ArrayList<>();

    /**
     * Creates a new FarmlandDeck with a given number of players and seed.
     *
     * @param playerCount the number of players in the game
     * @param seed the seed used to shuffle the Farmland tiles
     */
    public FarmlandDeck(int playerCount, long seed) {
        for (FarmlandType farmlandType: FarmlandType.values()) {
            int count = playerCount * farmlandType.getFactor();
            for (int i = 0; i < count; i++) {
                farmlandDeck.add(new Farmland(new Coordinates(), farmlandType));
            }
        }
        Collections.shuffle(farmlandDeck, new Random(seed));
    }

    /**
     * Takes a Farmland tile from the top of the deck and removes it from the deck.
     *
     * @return the Farmland tile taken from the top of the deck
     */
    public Farmland takeTile() {
        Farmland farmland = farmlandDeck.get(TOP_FARMLAND);
        farmlandDeck.remove(TOP_FARMLAND);
        return farmland;
    }

    /**
     * Checks whether there are any Farmland tiles left in the deck.
     *
     * @return true if there are still Farmland tiles left in the deck, false otherwise
     */
    public boolean hasTilesLeft() {
        return !this.farmlandDeck.isEmpty();
    }

}
