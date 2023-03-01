package edu.kit.informatik.queensfarming.rendering.game;

import edu.kit.informatik.queensfarming.entity.Vegetable;
import edu.kit.informatik.queensfarming.game.Game;
import edu.kit.informatik.queensfarming.ui.Shell;

/**
 *
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
     * Instantiates a new Render market.
     *
     * @param game the game
     */
    public RenderMarket(Game game) {
        super(game);
    }

    /**
     * Show market string.
     *
     * @return the string
     */
    @Override
    public String render() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
            .append(String.format(TEMPLATE_VEGETABLE + LABEL_SEPERATOR + TEMPLATE_MUSHROOM_RATE,
                Vegetable.MUSHROOM.getName(), SUFFIX_S,
                this.game.getMarket(Vegetable.MUSHROOM).getRate(Vegetable.MUSHROOM), NEW_LINE))
            .append(String.format(TEMPLATE_VEGETABLE + LABEL_SEPERATOR +  TEMPLATE_CARROT_RATE,
                Vegetable.CARROT.getName(), SUFFIX_S,
                this.game.getMarket(Vegetable.CARROT).getRate(Vegetable.CARROT), NEW_LINE))
            .append(String.format(TEMPLATE_VEGETABLE + LABEL_SEPERATOR + TEMPLATE_TOMATO_RATE,
                Vegetable.TOMATO.getName(), SUFFIX_ES,
                this.game.getMarket(Vegetable.TOMATO).getRate(Vegetable.TOMATO), NEW_LINE))
            .append(String.format(TEMPLATE_VEGETABLE + LABEL_SEPERATOR +  TEMPLATE_SALAD_RATE,
                Vegetable.SALAD.getName(), SUFFIX_S,
                this.game.getMarket(Vegetable.SALAD).getRate(Vegetable.SALAD)));
        return stringBuilder.toString();
    }

}
