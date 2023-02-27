package edu.kit.informatik.queensfarming.game;

import edu.kit.informatik.queensfarming.GameException;
import edu.kit.informatik.queensfarming.entity.Farmland;
import edu.kit.informatik.queensfarming.entity.FarmlandDeck;
import edu.kit.informatik.queensfarming.entity.GameTile;
import edu.kit.informatik.queensfarming.entity.GameTileBoard;
import edu.kit.informatik.queensfarming.entity.Vegetable;
import edu.kit.informatik.queensfarming.game.rendering.GameEngineRender;
import edu.kit.informatik.queensfarming.game.rendering.GameRender;
import edu.kit.informatik.queensfarming.ui.ExecutionState;
import edu.kit.informatik.queensfarming.ui.IoType;
import edu.kit.informatik.queensfarming.util.Coordinates;

import java.util.Collections;
import java.util.List;

public class GameEngine implements Executable {

    private static final String EXCEPTION_MISSING_FARMTILES = "No farmland tiles left.";
    private static final String EXCEPTION_OWNS_FARMTILE = "You cannot place tiles on fields you already own.";
    private static final String EXCEPTION_TEMPLATE_MISSING_VEGETABLE = "%d missing to sell %d %s%s.";
    private static final String EXCEPTION_MISSING_VEGETABLE = "You do not own a %s";
    private static final String EXCEPTION_INVALID_COORDINATES = "You do not own a property with this coordinates.";
    private static final String EXCEPTION_PLANT_BARN = "You are not able to plant in the barn.";
    private static final String EXCEPTION_PLANT_OCCUPIED = "You already grow %s on this tile.";
    private static final String EXCEPTION_TEMPLATE_PLANT_TYPE = "You are not able to plant %s on a \"%s\".";
    private static final String EXCEPTION_HARVEST_BARN = "You are not able to harvest in the barn.";
    private static final String EXCEPTION_MISSING_VEGETABLE_TILE = "There are no vegetables on this tile.";
    private static final String EXCEPTION_HARVEST_ZERO = "You cannot harvest 0 vegetables.";
    private static final String EXCEPTION_TEMPLATE_MISSING_HARVEST = "%d missing to harvest %d.";
    private static final String EXCEPTION_TEMPLATE_ISSUFICENT_BALANCE = "Insufficent balance. %d gold missing.";
    private static final String EXCEPTION_BUY_TOP_TO_BOTTOM = "Buy land not allowed from top to bottom.";
    private static final String EXCEPTION_BUY_ADJACENT_PROPERTY = "You do not own an adjacent property.";
    private static final int NEIGHBOUR_DISTANCE = 1;
    private static final int ACTION_COUNT = 2;
    private ExecutionState executionState;
    private IoType ioType;
    private final Config config;
    private final Game game;
    private int currentPlayerIndex;
    private int currentActionLeft;
    private final GameRender gameRender;
    private final GameEngineRender gameEngineRender;

    public GameEngine(Config config) {
        this.executionState = ExecutionState.RUNNING;
        this.config = config;
        this.game = new Game(config);
        this.gameRender = new GameRender(this.game);
        this.gameEngineRender = new GameEngineRender(this.config, this.game);
        this.currentPlayerIndex = 0;
        this.currentActionLeft = ACTION_COUNT;
        this.ioType = IoType.OUTPUT;
    }

    @Override
    public void quit() {
        this.executionState = ExecutionState.FINISHED;
        this.ioType = IoType.OUTPUT;
    }

    public String buyVegetable(Vegetable vegetable) {
        int rate = this.game.getMarket(vegetable).getRate(vegetable);
        GameTileBoard playersGameBoard = this.game.getGameTileBoard(this.currentPlayerIndex);
        if (this.ownsEnoughGold(playersGameBoard, rate)) {
            this.game.buyVegetable(playersGameBoard, vegetable);
            this.actionDone();
            return this.gameEngineRender.outputBuyVegetable(vegetable.getName(), rate);
        }
        return null;
    }

    public String buyLand(Coordinates coordinates) {
        FarmlandDeck farmlandDeck = this.game.getFarmlandDeck();
        GameTileBoard playersGameBoard = this.game.getGameTileBoard(this.currentPlayerIndex);
        if (!farmlandDeck.hasTilesLeft()) {
            throw new GameException(EXCEPTION_MISSING_FARMTILES);
        }
        int rate = GameTileBoard.getRate(coordinates);
        if (this.ownsThisTile(playersGameBoard, coordinates)) {
            throw new GameException(EXCEPTION_OWNS_FARMTILE);
        }
        if (this.ownsAdjacentProperty(playersGameBoard, coordinates) && this.ownsEnoughGold(playersGameBoard, rate)) {
            Farmland farmland = farmlandDeck.takeTile();
            this.game.buyLand(playersGameBoard, farmland, coordinates);
            this.actionDone();
            return this.gameEngineRender.outputBuyLand(farmland.getFarmlandType().getName() , rate);
        }
        return null;
    }

    public String sell(List<Vegetable> vegetables) {
        int totalSoldVegetables = 0;
        int totalGold = 0;
        GameTileBoard playersGameBoard = this.game.getGameTileBoard(this.currentPlayerIndex);
        for (Vegetable vegetable: Vegetable.values()) {
            int buyAmount = Collections.frequency(vegetables, vegetable);
            int stockAmount = playersGameBoard.getBarn().getStockOf(vegetable);
            if (buyAmount > stockAmount) {
                throw new GameException(String.format(EXCEPTION_TEMPLATE_MISSING_VEGETABLE,
                    buyAmount - stockAmount, buyAmount, vegetable.getName(),
                    GameEngineRender.getSuffix(vegetable, buyAmount)));
            }
        }

        for (Vegetable vegetable: vegetables) {
            if (this.ownsVegetable(playersGameBoard, vegetable)) {
                totalSoldVegetables++;
                totalGold += this.game.getMarket(vegetable).getRate(vegetable);
                this.game.sell(playersGameBoard, vegetable);
            }
        }
        this.actionDone();
        return this.gameEngineRender.outputSell(totalSoldVegetables, totalGold);
    }

    public String sell() {
        int totalSoldVegetables = 0;
        int totalGold = 0;
        GameTileBoard playersGameBoard = this.game.getGameTileBoard(this.currentPlayerIndex);
        for (Vegetable vegetable: Vegetable.values()) {
            while (this.ownsVegetable(playersGameBoard, vegetable)) {
                totalSoldVegetables++;
                totalGold += this.game.getMarket(vegetable).getRate(vegetable);
                this.game.sell(playersGameBoard, vegetable);
            }
        }
        this.actionDone();
        return this.gameEngineRender.outputSell(totalSoldVegetables, totalGold);
    }

    public String plant(Coordinates coordinates, Vegetable vegetable) {
        GameTileBoard playersGameBoard = this.game.getGameTileBoard(this.currentPlayerIndex);
        if (!this.ownsThisTile(playersGameBoard, coordinates)) {
            throw new GameException(EXCEPTION_INVALID_COORDINATES);
        }
        if (coordinates.compareTo(Coordinates.ORIGIN) == 0) {
            throw new GameException(EXCEPTION_PLANT_BARN);
        }
        if (!this.ownsVegetable(playersGameBoard, vegetable)) {
            throw new GameException(String.format(EXCEPTION_MISSING_VEGETABLE, vegetable.getName()));
        }
        Farmland farmland = playersGameBoard.getFarmland(coordinates);
        if (!farmland.isPlantable()) {
            throw new GameException(String.format(EXCEPTION_PLANT_OCCUPIED,
                farmland.getPlantedVegetable().getName()));
        }
        if (!farmland.isPlantable(vegetable)) {
            throw new GameException(String.format(EXCEPTION_TEMPLATE_PLANT_TYPE,
                vegetable.getName(), farmland.getFarmlandType().getName()));
        }
        this.game.plant(playersGameBoard, coordinates, vegetable);
        this.actionDone();
        return null;
    }

    public String harvest(Coordinates coordinates, int amount) {
        GameTileBoard playersGameBoard = this.game.getGameTileBoard(this.currentPlayerIndex);
        Farmland farmland = playersGameBoard.getFarmland(coordinates);
        if (!this.ownsThisTile(playersGameBoard, coordinates)) {
            throw new GameException(EXCEPTION_INVALID_COORDINATES);
        }
        if (coordinates.compareTo(Coordinates.ORIGIN) == 0) {
            throw new GameException(EXCEPTION_HARVEST_BARN);
        }
        if (farmland.isPlantable()) {
            throw new GameException(EXCEPTION_MISSING_VEGETABLE_TILE);
        }
        if (amount == 0) {
            throw new GameException(EXCEPTION_HARVEST_ZERO);
        }
        int balance = farmland.getBalance();
        if (balance < amount) {
            int difference = amount - balance;
            throw new GameException(String.format(EXCEPTION_TEMPLATE_MISSING_HARVEST, difference, amount));
        }
        Vegetable vegetable = farmland.getPlantedVegetable();
        this.game.harvest(playersGameBoard, coordinates, amount);
        this.actionDone();
        return this.gameEngineRender.outputHarvest(vegetable, amount);
    }

    public String showMarket() {
        return this.gameRender.showMarket();
    }

    public String showBarn() { return this.gameRender.showBarn(this.currentPlayerIndex); }

    public String showBoard() {
        return this.gameRender.showBoard(this.currentPlayerIndex);
    }

    public String endTurn() {
        this.switchToNextPlayer();
        return null;
    }

    public String getOutputStream() {
        this.ioType = IoType.INPUT;
        if (this.executionState == ExecutionState.RUNNING) {
            if (this.currentActionLeft == ACTION_COUNT) {
                return this.gameEngineRender.printBeforeTurn(this.currentPlayerIndex);
            }
        } else if (this.executionState == ExecutionState.FINISHED) {
            this.executionState = ExecutionState.EXITED;
            return this.gameEngineRender.printGameResult();
        }
        return null;

    }

    @Override
    public boolean isActive() {
        return this.executionState != ExecutionState.EXITED;
    }

    public IoType getIoType() {
        return this.ioType;
    }

    private boolean ownsEnoughGold(GameTileBoard gameTileBoard, int rate) {
        int balance = gameTileBoard.getBarn().getGoldStock();
        if (balance < rate) {
            throw new GameException(String.format(EXCEPTION_TEMPLATE_ISSUFICENT_BALANCE, rate - balance));
        }
        return true;
    }

    private boolean ownsThisTile(GameTileBoard gameTileBoard, Coordinates coordinates) {
        return gameTileBoard.getGameTile(coordinates) != null;
    }

    private boolean ownsAdjacentProperty(GameTileBoard gameTileBoard, Coordinates coordinates) {
        //true, if there is a tile above the one the user wants to buy, to throw specific exception but keep looking for
        //other locations.
        boolean topToBottom = false;
        for (GameTile gameTile: gameTileBoard.getGameTiles()) {
            if (gameTile.getCoordinates().compareTo(coordinates) == NEIGHBOUR_DISTANCE) {
                int newY = coordinates.getYCoordinate();
                int adjacentY = gameTile.getCoordinates().getYCoordinate();
                if (adjacentY - newY == 1) {
                    topToBottom = true;
                    continue;
                }
                return true;
            }
        }
        if (topToBottom) {
            throw new GameException(EXCEPTION_BUY_TOP_TO_BOTTOM);
        }
        throw new GameException(EXCEPTION_BUY_ADJACENT_PROPERTY);
    }

    private boolean ownsVegetable(GameTileBoard gameTileBoard, Vegetable vegetable) {
        return gameTileBoard.getBarn().getStockOf(vegetable) > 0;
    }

    private void switchToNextPlayer() {
        this.currentPlayerIndex += 1;
        this.currentActionLeft = ACTION_COUNT;
        this.game.recalculateMarkets();
        this.ioType = IoType.OUTPUT;
        if (this.currentPlayerIndex >= this.config.getPlayerCount()) {
            this.game.nextRound();
            this.currentPlayerIndex = 0;
            this.setExecutionState();
        }
    }

    private void actionDone() {
        this.currentActionLeft--;
        if (this.currentActionLeft <= 0) {
            this.switchToNextPlayer();
        }
    }

    private void setExecutionState() {
        for (int player = 0; player < this.config.getPlayerCount(); player++) {
            if (this.game.getGameTileBoard(player).getBarn().getGoldStock() >= config.getTargetGold()) {
                this.executionState = ExecutionState.FINISHED;
            }
        }
    }
}
