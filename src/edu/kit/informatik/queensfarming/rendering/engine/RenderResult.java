package edu.kit.informatik.queensfarming.rendering.engine;

import edu.kit.informatik.queensfarming.game.Config;
import edu.kit.informatik.queensfarming.game.Game;
import edu.kit.informatik.queensfarming.rendering.Render;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * A class that extends Render and is responsible for rendering the game result as a string.
 * @author uuovz
 * @version 1.0
 */
public class RenderResult extends Render {

    private static final String MESSAGE_TEMPLATE_RESULT_PLAYER = "Player %d (%s): %d%s";
    private static final String MESSAGE_TEMPLATE_HAS_WON = "%s has won!";
    private static final String MESSAGE_TEMPLATE_HAVE_WON = " have won!";
    private static final String LABEL_SEPERATOR_COMMA = ",";
    private static final String LABEL_SEPERATOR_AND = "and";
    private final Config config;
    private final Game game;

    /**
     * Constructs a new RenderResult object with the given Game and Config objects.
     * @param game the Game object containing game state data
     * @param config the Config object containing game configuration data
     */
    public RenderResult(Game game, Config config) {
        this.game = game;
        this.config = config;
    }

    /**
     * Renders the game result as a string.
     * @return a string containing the game result
     */
    @Override
    public String render() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Integer> winners = new ArrayList<>();
        List<Integer> playerHighestGoldAmount = new ArrayList<>();
        int highestGoldAmount = 0;

        for (int playerIndex = 0; playerIndex < this.config.getPlayerCount(); playerIndex++) {
            int gold = this.game.getGameTileBoard(playerIndex).getBarn().getGoldStock();
            stringBuilder.append(
                String.format(MESSAGE_TEMPLATE_RESULT_PLAYER, playerIndex + 1, this.config.getPlayerName(playerIndex),
                    gold, System.lineSeparator())
            );
            if (gold >= this.config.getTargetGold()) {
                winners.add(playerIndex);
            }
            if (winners.isEmpty()) {
                if (gold > highestGoldAmount) {
                    playerHighestGoldAmount.clear();
                    playerHighestGoldAmount.add(playerIndex);
                    highestGoldAmount = gold;
                } else if (gold == highestGoldAmount) {
                    playerHighestGoldAmount.add(playerIndex);
                }
            }
        }
        winners = winners.isEmpty() ? playerHighestGoldAmount : winners;
        int numWinners = winners.size();
        if (winners.size() == 1) {
            stringBuilder.append(String.format(MESSAGE_TEMPLATE_HAS_WON, this.config.getPlayerName(winners.get(0))));
        } else {
            StringJoiner sj = new StringJoiner(LABEL_SEPERATOR_COMMA + BLANK_STRING, EMPTY_STRING,
                MESSAGE_TEMPLATE_HAVE_WON);
            for (int index: winners) {
                sj.add(this.config.getPlayerName(index));
            }
            String sjString = sj.toString();
            if (numWinners > 1) {
                int lastCommaIndex = sjString.lastIndexOf(LABEL_SEPERATOR_COMMA);
                sjString = sjString.substring(0, lastCommaIndex) + BLANK_STRING + LABEL_SEPERATOR_AND
                    + sjString.substring(lastCommaIndex + 1);
            }
            stringBuilder.append(sjString);
        }

        return stringBuilder.toString();
    }
}
