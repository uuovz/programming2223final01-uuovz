package edu.kit.informatik.queensfarming.game;

import edu.kit.informatik.queensfarming.entity.Farmland;
import edu.kit.informatik.queensfarming.entity.FarmlandDeck;
import edu.kit.informatik.queensfarming.entity.GameTileBoard;
import edu.kit.informatik.queensfarming.entity.Market;
import edu.kit.informatik.queensfarming.entity.MarketType;
import edu.kit.informatik.queensfarming.entity.Vegetable;
import edu.kit.informatik.queensfarming.util.Coordinates;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * The type Game.
 *
 * @author uuovz
 * @version 1.0
 */
public class Game {

    private final List<GameTileBoard> gameTileBoards = new ArrayList<>();
    private final List<Market> markets = new ArrayList<>();
    private final FarmlandDeck farmlandDeck;

    /**
     * Instantiates a new Game.
     *
     * @param config the config
     */
    public Game(Config config) {
        for (int i = 0; i < config.getPlayerCount(); i++) {
            this.gameTileBoards.add(new GameTileBoard(config.getInitalGold()));
        }
        for (MarketType marketType: EnumSet.allOf(MarketType.class)) {
            this.markets.add(new Market(marketType));
        }
        this.farmlandDeck = new FarmlandDeck(config.getPlayerCount(), config.getSeed());
    }

    /**
     * Gets farmland deck.
     *
     * @return the farmland deck
     */
    public FarmlandDeck getFarmlandDeck() {
        return farmlandDeck;
    }

    /**
     * Gets game tile board.
     *
     * @param index the index
     * @return the game tile board
     */
    public GameTileBoard getGameTileBoard(int index) {
        return this.gameTileBoards.get(index);
    }

    /**
     * Gets market.
     *
     * @param vegetable the vegetable
     * @return the market
     */
    public Market getMarket(Vegetable vegetable) {
        for (Market market: this.markets) {
            if (market.sellable(vegetable)) {
                return market;
            }
        }
        return null;
    }

    /**
     * Next round.
     */
    public void nextRound() {
        for (GameTileBoard gameTileBoard: this.gameTileBoards) {
            gameTileBoard.nextRound();
        }
    }

    /**
     * Recalculate markets.
     */
    public void recalculateMarkets() {
        for (Market market: markets) {
            market.reCalculate();
        }
    }

    /**
     * Buy vegetable.
     *
     * @param gameTileBoard the game tile board
     * @param vegetable     the vegetable
     */
    public void buyVegetable(GameTileBoard gameTileBoard, Vegetable vegetable) {
        gameTileBoard.getBarn().reduceGoldStock(this.getMarket(vegetable).getRate(vegetable));
        gameTileBoard.getBarn().addStockOf(vegetable);
    }

    /**
     * Buy land.
     *
     * @param gameTileBoard the game tile board
     * @param farmland      the farmland
     * @param coordinates   the coordinates
     */
    public void buyLand(GameTileBoard gameTileBoard, Farmland farmland, Coordinates coordinates) {
        farmland.setCoordinates(coordinates);
        gameTileBoard.addFarmland(farmland);
        gameTileBoard.getBarn().reduceGoldStock(GameTileBoard.getRate(coordinates));
    }

    /**
     * Sell.
     *
     * @param gameTileBoard the game tile board
     * @param vegetable     the vegetable
     */
    public void sell(GameTileBoard gameTileBoard, Vegetable vegetable) {
        gameTileBoard.getBarn().reduceStockOf(vegetable);
        gameTileBoard.getBarn().addGoldStock(this.getMarket(vegetable).getRate(vegetable));
        this.getMarket(vegetable).sell(vegetable);
    }

    /**
     * Plant.
     *
     * @param gameTileBoard the game tile board
     * @param coordinates   the coordinates
     * @param vegetable     the vegetable
     */
    public void plant(GameTileBoard gameTileBoard, Coordinates coordinates, Vegetable vegetable) {
        gameTileBoard.getFarmland(coordinates).plant(vegetable);
        gameTileBoard.getBarn().reduceStockOf(vegetable);
    }

    /**
     * Harvest.
     *
     * @param gameTileBoard the game tile board
     * @param coordinates   the coordinates
     * @param amount        the amount
     */
    public void harvest(GameTileBoard gameTileBoard, Coordinates coordinates, int amount) {
        Farmland farmland = gameTileBoard.getFarmland(coordinates);
        for (int i = 0; i < amount; i++) {
            gameTileBoard.getBarn().addStockOf(farmland.getPlantedVegetable());
            gameTileBoard.getFarmland(coordinates).harvest();
        }
    }
}
