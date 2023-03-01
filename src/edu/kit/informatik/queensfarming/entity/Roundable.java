package edu.kit.informatik.queensfarming.entity;

/**
 * The Roundable interface represents an entity that can progress to the next round.
 * It defines a single method that should be implemented to perform necessary actions for the next round.
 *
 * @version 1.0
 * @author uuovz
 */
public interface Roundable {
    /**
     * Advances the entity to the next round by performing any necessary actions.
     */
    void nextRound();
}
