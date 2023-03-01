package edu.kit.informatik.queensfarming.game;

import edu.kit.informatik.queensfarming.GameException;
import edu.kit.informatik.queensfarming.entity.Farmland;
import edu.kit.informatik.queensfarming.entity.FarmlandDeck;
import edu.kit.informatik.queensfarming.entity.GameTile;
import edu.kit.informatik.queensfarming.entity.GameTileBoard;
import edu.kit.informatik.queensfarming.entity.Vegetable;
import edu.kit.informatik.queensfarming.rendering.Render;
import edu.kit.informatik.queensfarming.rendering.engine.RenderBuy;
import edu.kit.informatik.queensfarming.rendering.engine.RenderHarvest;
import edu.kit.informatik.queensfarming.rendering.engine.RenderResult;
import edu.kit.informatik.queensfarming.rendering.engine.RenderSell;
import edu.kit.informatik.queensfarming.rendering.engine.RenderTurn;
import edu.kit.informatik.queensfarming.rendering.game.RenderGameBoard;
import edu.kit.informatik.queensfarming.rendering.game.RenderBarn;
import edu.kit.informatik.queensfarming.rendering.game.RenderMarket;
import edu.kit.informatik.queensfarming.ui.ExecutionState;
import edu.kit.informatik.queensfarming.ui.IoType;
import edu.kit.informatik.queensfarming.util.Coordinates;

import java.util.Collections;
import java.util.List;

/**
 * The `GameEngine` class represents the game engine of the Queens Farming Game.
 * It implements the `Executable` interface and is responsible for managing the gameplay logic of the farming game.
 *
 * @author uuovz
 * @version 1.0
 */
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
    private final RenderGameBoard renderGameBoard;
    private final RenderBarn renderBarn;
    private final RenderMarket renderMarket;
    private final RenderBuy renderBuyVegetable = new RenderBuy();
    private final RenderSell renderSell = new RenderSell();
    private final RenderHarvest renderHarvest = new RenderHarvest();
    private final RenderTurn renderTurn;
    private final RenderResult renderResult;
    private final Config config;
    private final Game game;

    private ExecutionState executionState;
    private IoType ioType;
    private int currentPlayerIndex;
    private int currentActionLeft;


    /**
     * Instantiates a new GameEngine object with the specified game configuration.
     *
     * @param config the game configuration
     */
    public GameEngine(Config config) {
        this.executionState = ExecutionState.RUNNING;
        this.config = config;
        this.game = new Game(config);
        this.renderGameBoard = new RenderGameBoard(this.game);
        this.renderBarn = new RenderBarn(this.game);
        this.renderMarket = new RenderMarket(this.game);
        this.renderTurn = new RenderTurn(this.game, this.config);
        this.renderResult = new RenderResult(this.game, this.config);
        this.currentPlayerIndex = 0;
        this.currentActionLeft = ACTION_COUNT;
        this.ioType = IoType.OUTPUT;
    }

    /**
     * Marks the game engine as finished and sets the IO type to output.
     */
    @Override
    public void quit() {
        this.executionState = ExecutionState.FINISHED;
        this.ioType = IoType.OUTPUT;
    }

    /**
     * Buys the specified vegetable for the current player.
     *
     * @param vegetable the vegetable to buy
     * @return a string representation of the purchase result, or null if the purchase failed
     */
    public String buyVegetable(Vegetable vegetable) {
        int rate = this.game.getMarket(vegetable).getRate(vegetable);
        GameTileBoard playersGameBoard = this.game.getGameTileBoard(this.currentPlayerIndex);
        if (this.ownsEnoughGold(playersGameBoard, rate)) {
            this.game.buyVegetable(playersGameBoard, vegetable);
            this.renderBuyVegetable.setItem(vegetable.getName());
            this.renderBuyVegetable.setRate(rate);
            this.actionDone();
            return this.renderBuyVegetable.render();
        }
        return null;
    }

    /**
     * Buy land from the Farmland deck for the current player.
     *
     * @param coordinates the coordinates of the tile being bought
     * @return the String representation of the rendered Buy Vegetable action
     * @throws GameException if there are no more farm tiles left, the player already owns
     */
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
            this.renderBuyVegetable.setItem(farmland.getFarmlandType().getName());
            this.renderBuyVegetable.setRate(rate);
            this.actionDone();
            return this.renderBuyVegetable.render();
        }
        return null;
    }

    /**
     * Sells all vegetables in the list on the market for the current player.
     *
     * @param vegetables the list of vegetables to sell
     * @return a string representing the result of the sell action
     * @throws GameException if the player tries to sell more vegetables than they own or vegetables they do not own
     */
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
                    Render.getSuffixVegetable(vegetable, buyAmount)));
            }
        }

        for (Vegetable vegetable: vegetables) {
            if (this.ownsVegetable(playersGameBoard, vegetable)) {
                totalSoldVegetables++;
                totalGold += this.game.getMarket(vegetable).getRate(vegetable);
                this.game.sell(playersGameBoard, vegetable);
            }
        }
        this.renderSell.setTotalSoldVegetables(totalSoldVegetables);
        this.renderSell.setTotalGold(totalGold);
        this.actionDone();
        return this.renderSell.render();
    }

    /**
     * Sells all vegetables in the barn on the market for the current player.
     *
     * @return a string representing the result of the sell action
     */
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
        this.renderSell.setTotalSoldVegetables(totalSoldVegetables);
        this.renderSell.setTotalGold(totalGold);
        this.actionDone();
        return this.renderSell.render();
    }

    /**
     * Plant a vegetable on a farmland tile owned by the current player at the specified coordinates.
     *
     * @param coordinates the coordinates of the farmland tile to plant the vegetable on
     * @param vegetable the vegetable to plant on the specified farmland tile
     * @return null, as no rendering is required for this action
     * @throws GameException if the specified coordinates are invalid,
     * if the specified coordinates correspond to the barn,
     */
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

    /**
     * Harvests vegetables from a farmland tile at the specified coordinates for the current player.
     *
     * @param coordinates the coordinates of the farmland tile to harvest from
     * @param amount the amount of vegetables to harvest
     * @return a string representing the result of the harvest action
     * @throws GameException if the specified coordinates are invalid or the tile is not plantable or does not contain
     *         enough vegetables, or the amount to harvest is zero
     */
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
        this.renderHarvest.setVegetable(vegetable);
        this.renderHarvest.setAmount(amount);
        this.actionDone();
        return this.renderHarvest.render();
    }

    /**
     * Displays the current state of the game's market.
     *
     * @return A string representation of the market's current state.
     */
    public String showMarket() {
        return this.renderMarket.render();
    }

    /**
     * Returns a string representation of the current player's barn.
     *
     * @return a string representation of the current player's barn
     */
    public String showBarn() {
        this.renderBarn.setIndex(this.currentPlayerIndex);
        return this.renderBarn.render();
    }

    /**
     * Returns a string representation of the current player's board.
     *
     * @return a string representation of the current player's board
     */
    public String showBoard() {
        this.renderGameBoard.setIndex(this.currentPlayerIndex);
        return this.renderGameBoard.render();
    }

    /**
     * End turn method that switches to the next player's turn.
     *
     * @return null
     */
    public String endTurn() {
        this.switchToNextPlayer();
        return null;
    }

    /**
     * Gets the output stream for the current game state.
     * This method returns a string that represents the output stream of the current game state.
     *
     * @return the output stream
     */
    public String getOutputStream() {
        this.ioType = IoType.INPUT;
        if (this.executionState == ExecutionState.RUNNING) {
            if (this.currentActionLeft == ACTION_COUNT) {
                this.renderTurn.setIndex(this.currentPlayerIndex);
                return this.renderTurn.render();
            }
        } else if (this.executionState == ExecutionState.FINISHED) {
            this.executionState = ExecutionState.EXITED;
            return this.renderResult.render();
        }
        return null;
    }

    /**
     * Returns whether the game is currently active or not.
     *
     * @return true if the game is active, false otherwise.
     */
    @Override
    public boolean isActive() {
        return this.executionState != ExecutionState.EXITED;
    }

    /**
     * Gets io type.
     *
     * @return the io type
     */
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
