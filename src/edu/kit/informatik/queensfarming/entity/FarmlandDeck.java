package edu.kit.informatik.queensfarming.entity;

import edu.kit.informatik.queensfarming.util.Coordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The type Farmland deck.
 *
 * @author uuovz
 * @version 1.0
 */
public class FarmlandDeck {

    private static final int TOP_FARMLAND = 0;

    private final List<Farmland> farmlandDeck = new ArrayList<>();

    /**
     * Instantiates a new Farmland deck.
     *
     * @param playerCount the player count
     * @param seed        the seed
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
     * Take tile farmland.
     *
     * @return the farmland
     */
    public Farmland takeTile() {
        Farmland farmland = farmlandDeck.get(TOP_FARMLAND);
        farmlandDeck.remove(TOP_FARMLAND);
        return farmland;
    }

    /**
     * Has tiles left boolean.
     *
     * @return the boolean
     */
    public boolean hasTilesLeft() {
        return !this.farmlandDeck.isEmpty();
    }



}
