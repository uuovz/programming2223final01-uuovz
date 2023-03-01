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
 * The Game class represents the Queens farming game state and provides methods to interact with it.
 *
 * @author uuovz
 * @version 1.0
 */
public class Game {

    private final List<GameTileBoard> gameTileBoards = new ArrayList<>();
    private final List<Market> markets = new ArrayList<>();
    private final FarmlandDeck farmlandDeck;

    /**
     * Constructs a new Game instance.
     *
     * @param config the configuration for the game.
     */
    public Game(Config config) {
        for (int i = 0; i < config.getPlayerCount(); i++) {
            this.gameTileBoards.add(new GameTileBoard(config.getInitialGold()));
        }
        for (MarketType marketType: EnumSet.allOf(MarketType.class)) {
            this.markets.add(new Market(marketType));
        }
        this.farmlandDeck = new FarmlandDeck(config.getPlayerCount(), config.getSeed());
    }

    /**
     * Returns the FarmlandDeck for this game.
     *
     * @return the FarmlandDeck object for this game.
     */
    public FarmlandDeck getFarmlandDeck() {
        return farmlandDeck;
    }

    /**
     * Returns the GameTileBoard for the specified player index.
     *
     * @param index the index of the player.
     * @return the GameTileBoard for the specified player index.
     */
    public GameTileBoard getGameTileBoard(int index) {
        return this.gameTileBoards.get(index);
    }

    /**
     * Returns the Market that sells the specified Vegetable.
     *
     * @param vegetable the Vegetable to find the Market for.
     * @return the Market that sells the specified Vegetable.
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
     * Advances the game to the next round and triggers all objects
     */
    public void nextRound() {
        for (GameTileBoard gameTileBoard: this.gameTileBoards) {
            gameTileBoard.nextRound();
        }
    }

    /**
     * Recalculates the prices for all Markets.
     */
    public void recalculateMarkets() {
        for (Market market: markets) {
            market.reCalculate();
        }
    }

    /**
     * Buys the specified vegetable from the market and adds it to the barn's stock,
     * reducing the corresponding amount of gold from the barn's gold stock.
     *
     * @param gameTileBoard the game tile board containing the barn and market
     * @param vegetable the vegetable to buy
     */
    public void buyVegetable(GameTileBoard gameTileBoard, Vegetable vegetable) {
        gameTileBoard.getBarn().reduceGoldStock(this.getMarket(vegetable).getRate(vegetable));
        gameTileBoard.getBarn().addStockOf(vegetable);
    }

    /**
     * Buys the specified farmland and adds it to the game tile board at the specified coordinates,
     * reducing the corresponding amount of gold from the barn's gold stock.
     *
     * @param gameTileBoard the game tile board containing the barn and game tile board
     * @param farmland the farmland to buy
     * @param coordinates the coordinates to place the farmland on the game tile board
    */
    public void buyLand(GameTileBoard gameTileBoard, Farmland farmland, Coordinates coordinates) {
        farmland.setCoordinates(coordinates);
        gameTileBoard.addFarmland(farmland);
        gameTileBoard.getBarn().reduceGoldStock(GameTileBoard.getRate(coordinates));
    }

    /**
     * Sells the specified vegetable from the barn's stock to the market,
     * increasing the corresponding amount of gold in the barn's gold stock.
     *
     * @param gameTileBoard the game tile board containing the barn and market
     * @param vegetable the vegetable to sell
     */
    public void sell(GameTileBoard gameTileBoard, Vegetable vegetable) {
        gameTileBoard.getBarn().reduceStockOf(vegetable);
        gameTileBoard.getBarn().addGoldStock(this.getMarket(vegetable).getRate(vegetable));
        this.getMarket(vegetable).sell(vegetable);
    }

    /**
     * Plants the specified vegetable on the farmland at the specified coordinates,
     * reducing the corresponding amount of the vegetable from the barn's stock.
     *
     * @param gameTileBoard the game tile board containing the farmland and barn
     * @param coordinates the coordinates of the farmland to plant on
     * @param vegetable the vegetable to plant
     */
    public void plant(GameTileBoard gameTileBoard, Coordinates coordinates, Vegetable vegetable) {
        gameTileBoard.getFarmland(coordinates).plant(vegetable);
        gameTileBoard.getBarn().reduceStockOf(vegetable);
    }

    /**
     * Harvests the planted vegetable on the farmland at the specified coordinates,
     * adding the corresponding amount of the vegetable to the barn's stock.
     *
     * @param gameTileBoard the game tile board containing the farmland and barn
     * @param coordinates the coordinates of the farmland to harvest from
     * @param amount the amount of vegetables to harvest
     */
    public void harvest(GameTileBoard gameTileBoard, Coordinates coordinates, int amount) {
        Farmland farmland = gameTileBoard.getFarmland(coordinates);
        for (int i = 0; i < amount; i++) {
            gameTileBoard.getBarn().addStockOf(farmland.getPlantedVegetable());
            gameTileBoard.getFarmland(coordinates).harvest();
        }
    }
}
