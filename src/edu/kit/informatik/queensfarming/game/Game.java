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

public class Game {

    private final List<GameTileBoard> gameTileBoards = new ArrayList<>();
    private final List<Market> markets = new ArrayList<>();
    private final FarmlandDeck farmlandDeck;

    public Game(Config config) {
        for (int i = 0; i < config.getPlayerCount(); i++) {
            this.gameTileBoards.add(new GameTileBoard(config.getInitalGold()));
        }
        for (MarketType marketType: EnumSet.allOf(MarketType.class)) {
            this.markets.add(new Market(marketType));
        }
        this.farmlandDeck = new FarmlandDeck(config.getPlayerCount(), config.getSeed());
    }

    public FarmlandDeck getFarmlandDeck() {
        return farmlandDeck;
    }

    public GameTileBoard getGameTileBoard(int index) {
        return this.gameTileBoards.get(index);
    }

    public Market getMarket(Vegetable vegetable) {
        for (Market market: this.markets) {
            if (market.sellable(vegetable)) {
                return market;
            }
        }
        return null;
    }

    public void nextRound() {
        for (GameTileBoard gameTileBoard: this.gameTileBoards) {
            gameTileBoard.nextRound();
        }
    }

    public void recalculateMarkets() {
        for (Market market: markets) {
            market.reCalculate();
        }
    }

    public void buyVegetable(GameTileBoard gameTileBoard, Vegetable vegetable) {
        gameTileBoard.getBarn().reduceGoldStock(this.getMarket(vegetable).getRate(vegetable));
        gameTileBoard.getBarn().addStockOf(vegetable);
    }

    public void buyLand(GameTileBoard gameTileBoard, Farmland farmland, Coordinates coordinates) {
        farmland.setCoordinates(coordinates);
        gameTileBoard.addFarmland(farmland);
        gameTileBoard.getBarn().reduceGoldStock(GameTileBoard.getRate(coordinates));
    }

    public void sell(GameTileBoard gameTileBoard, Vegetable vegetable) {
        gameTileBoard.getBarn().reduceStockOf(vegetable);
        gameTileBoard.getBarn().addGoldStock(this.getMarket(vegetable).getRate(vegetable));
        this.getMarket(vegetable).sell(vegetable);
    }

    public void plant(GameTileBoard gameTileBoard, Coordinates coordinates, Vegetable vegetable) {
        gameTileBoard.getFarmland(coordinates).plant(vegetable);
        gameTileBoard.getBarn().reduceStockOf(vegetable);
    }

    public void harvest(GameTileBoard gameTileBoard, Coordinates coordinates, int amount) {
        Farmland farmland = gameTileBoard.getFarmland(coordinates);
        for (int i = 0; i < amount; i++) {
            gameTileBoard.getBarn().addStockOf(farmland.getPlantedVegetable());
            gameTileBoard.getFarmland(coordinates).harvest();
        }
    }
}
