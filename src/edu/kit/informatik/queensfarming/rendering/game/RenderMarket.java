package edu.kit.informatik.queensfarming.rendering.game;

import edu.kit.informatik.queensfarming.entity.Vegetable;
import edu.kit.informatik.queensfarming.game.Game;

/**
 * The RenderMarket class is a child class of RenderGame that renders the market information
 * for the game. It includes methods for displaying the current rates for each vegetable in the market.
 * @author uuovz
 * @version 1.0
 */
public class RenderMarket extends RenderGame {

    private static final String TEMPLATE_VEGETABLE = "%s%s";
    private static final String TEMPLATE_MUSHROOM_RATE = " %d%s";
    private static final String TEMPLATE_CARROT_RATE = " %4d%s";
    private static final String TEMPLATE_TOMATO_RATE = " %3d%s";
    private static final String TEMPLATE_SALAD_RATE = " %5d";

    /**
     * Constructs a new RenderMarket object with the given Game object.
     * @param game the Game object that this RenderMarket object will render the market information for.
     */
    public RenderMarket(Game game) {
        super(game);
    }

    /**
     * Returns a String containing the current market rates for each vegetable.
     * @return a String containing the market rates for each vegetable in the format:
     */
    @Override
    public String render() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
            .append(String.format(TEMPLATE_VEGETABLE + LABEL_SEPERATOR + TEMPLATE_MUSHROOM_RATE,
                Vegetable.MUSHROOM.getName(), SUFFIX_S,
                this.getGame().getMarket(Vegetable.MUSHROOM).getRate(Vegetable.MUSHROOM), NEW_LINE))
            .append(String.format(TEMPLATE_VEGETABLE + LABEL_SEPERATOR +  TEMPLATE_CARROT_RATE,
                Vegetable.CARROT.getName(), SUFFIX_S,
                this.getGame().getMarket(Vegetable.CARROT).getRate(Vegetable.CARROT), NEW_LINE))
            .append(String.format(TEMPLATE_VEGETABLE + LABEL_SEPERATOR + TEMPLATE_TOMATO_RATE,
                Vegetable.TOMATO.getName(), SUFFIX_ES,
                this.getGame().getMarket(Vegetable.TOMATO).getRate(Vegetable.TOMATO), NEW_LINE))
            .append(String.format(TEMPLATE_VEGETABLE + LABEL_SEPERATOR +  TEMPLATE_SALAD_RATE,
                Vegetable.SALAD.getName(), SUFFIX_S,
                this.getGame().getMarket(Vegetable.SALAD).getRate(Vegetable.SALAD)));
        return stringBuilder.toString();
    }

}
