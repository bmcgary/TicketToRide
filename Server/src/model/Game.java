package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import server.exception.GameOverException;
import server.exception.InternalServerException;
import server.exception.OutOfBoundsException;
import server.exception.PreConditionException;

/**
 * Represents a given game. Most operations are delegated to the PlayerManager and GameBoard
 * Also contains a game history which can be added to and viewed. 
 * @author Trent
 *
 */
public class Game {
	protected GameBoard gameBoard;
	protected PlayerManager playerManager;
	protected static int nextID = 1;
	protected int gameID;
	protected List<String> history;
	protected boolean started;
	protected boolean isGameOver;
	protected String name;

	public Game() {
		this(null);
	}

	public Game(String name){
		if (name == null || name.equals("")) {
			name = "Game" + nextID;
		}
		this.constructHelper(name);
	}
	
	private void constructHelper(String name){
		this.name = name;
		gameBoard = new GameBoard();
		started = false;
		playerManager = new PlayerManager();
		gameID = nextID++;
		isGameOver = false;
		history = new ArrayList<String>();
		history.add("Game initialized");
	}
	
	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public int getGameID() {
		return gameID;
	}
	
	public String getName(){
		return this.name;
	}
	
	public boolean isGameOver(){
		return this.isGameOver;
	}
	
	public void addHistoryMessage(String message){
		history.add(message);
	}
	
	/**
	 * Returns the history as an unmodifiable list of strings
	 * @return the game history
	 */
	public List<String> getHistory(){
		return Collections.unmodifiableList(history);
	}
	
	public void startGame() throws InternalServerException{
		//get all the cards set up on the board
		gameBoard.initialize();
		
		//give each player 4 train cards
		for(Player p : this.getPlayerManager().getPlayers()){
			for(int i = 0; i < 4; ++i){
				playerManager.addTrainCarCard(p.getPlayerID(), gameBoard.drawDeckTrainCar());
			}
		}
		
		//give each player 3 destination cards
		for(Player p : this.getPlayerManager().getPlayers()){
			playerManager.addDestinationRoutesToConsider(p.getPlayerID(), gameBoard.drawDestinationRoutes());
		}
		
		//locks new players out now, declares the game has been started
		started = true;
		this.addHistoryMessage("Game initialized");
	}
	
	public boolean isStarted(){
		return started;
	}

	public boolean canPlayerBuyRoute(int playerID, CityToCityRoute route, Map<TrackColor, Integer> cards) {
		//must be player's current turn
		if(playerManager.getPlayers().get(playerManager.currentTurnIndex).getPlayerID() != playerID){
			return false;
		}
		
		//player cannot be holding destination routes to consider
		if(playerManager.getPlayer(playerID).getDestinationRoutesToConsider()[0] != null){
			return false;
		}
		
		//route must be available
		if(!gameBoard.isRouteAvailable(route)){
			return false;
		}
		
		//player must have the appropriate resources
		if(playerManager.canBuyTrackWithCards(playerID, route.getNumTrains(), route.getTrackColor(), cards)){
			return true;
		}
		return false;
	}

	public void buyRoute(int playerID, CityToCityRoute route, Map<TrackColor, Integer> cards) throws PreConditionException, OutOfBoundsException {
		//remove resources from player
		playerManager.buyTrack(playerID, route.getNumTrains(), route.getTrackColor(), cards);
		this.addHistoryMessage("Player + " + playerID + " bought route " + route.toString());
		
		//assigns the route to the player
		gameBoard.claimRoute(playerID, route);
		
		//return the cards to the gameBoard discarded deck
		List<TrackColor> toDiscard = new ArrayList<TrackColor>();
		for(TrackColor tc : cards.keySet()){
			for(int j = 0; j < cards.get(tc); ++j){
				toDiscard.add(tc);
			}
		}
		gameBoard.discardTrainCards(toDiscard);
		try {
			playerManager.advanceTurn();
		} catch (GameOverException e) {
			this.isGameOver = true;
		}
		

	}

	public boolean canPlayerDrawTrainCard(int playerID, int cardLocation) throws OutOfBoundsException, InternalServerException {
		//checks cardLocation validity
		if(cardLocation > 5 || cardLocation < 0){
			throw new OutOfBoundsException("Card Location was: " + cardLocation + ", which is out of bounds");
		}
		
		//must be current player
		if(!playerManager.isPlayersTurn(playerID)){
			return false;
		}
		
		//player cannot have destination cards under consideration
		if(playerManager.getPlayer(playerID).getDestinationRoutesToConsider()[0]!=null){
			return false;
		}
		
		//5 means top of the deck
		if(cardLocation == 5 && gameBoard.canDrawDeckTrainCar()){
			return true;
		}
		
		//0-4 means visible cards
		if(cardLocation >= 0 && cardLocation < 5){
				if(this.playerManager.drewAlreadyCurrentTurn && gameBoard.getVisibleTrainCarCards()[cardLocation] == TrackColor.None){
					return false;	//can't draw visible TrainCard on second draw
				}
				return gameBoard.canDrawVisibleTrainCar(cardLocation);
		}
		throw new InternalServerException("Trent messed up in Game::canPlayerDrawTrainCard");
	}

	public void drawTrainCard(int playerID, int cardLocation) throws PreConditionException, OutOfBoundsException, InternalServerException {
		TrackColor card = null;
		//5 means top of deck
		if(cardLocation == 5){
			card = gameBoard.drawDeckTrainCar();
		}
		else if(cardLocation < 5 || cardLocation >= 0){
			card = gameBoard.drawVisibleTrainCar(cardLocation);
		}
		
		if(card == null){
			throw new InternalServerException("Trent messed up in Game::drawTrainCard");
		}
		
		playerManager.addTrainCarCard(playerID, card);
		if(playerManager.drewAlreadyCurrentTurn || (cardLocation != 5 && card == TrackColor.None)){	//visible trainCard draw ends turn immediately
			try {
				playerManager.advanceTurn();
			} catch (GameOverException e) {
				this.isGameOver = true;
			}
		}
		else{
			playerManager.drewAlreadyCurrentTurn = true;
		}
		
		this.addHistoryMessage("Player " + playerID + " drew a card");
		
	}

	public boolean canPlayerGetDestinations(int playerID) {
		//must be player's turn
		if(!playerManager.isPlayersTurn(playerID) || playerManager.drewAlreadyCurrentTurn){
			return false;
		}
		
		//player cannot already have cards under consideration
		if(playerManager.getPlayer(playerID).getDestinationRoutesToConsider()[0] != null){
			return false;
		}
		
		//game board must have sufficient cards left
		return gameBoard.canDrawDestinationRoute();
	}

	
	public void getDestinations(int playerID) {
		List<DestinationRoute> cards = gameBoard.drawDestinationRoutes();
		playerManager.addDestinationRoutesToConsider(playerID, cards);
		assert(playerManager.isPlayersTurn(playerID)); //getting destinations shouldn't end the turn yet
		assert(playerManager.getPlayers().get(playerID).getDestinationRoutesToConsider().length > 0);
		this.addHistoryMessage("Player " + playerID + " drew destination cards");
	}


	public boolean canPlayerSelectDestinations(int playerID, int[] destinationsSelected) {
		//must be players turn
		if(!playerManager.isPlayersTurn(playerID)){
			return false;
		}
		//if it's the first turn, at least two must be selected instead of the normal one
		if(playerManager.getRoundNumber() == 1 && destinationsSelected.length < 2){
			return false;
		}
		return playerManager.canSelectDestinations(playerID, destinationsSelected);
	}

	public void selectDestinations(int playerID, int[] destinationsSelected) {
		List<DestinationRoute> routes = playerManager.selectDestinations(playerID, destinationsSelected);
		gameBoard.returnDestinationRoutes(routes);
		assert(playerManager.isPlayersTurn(playerID));
		try {
			playerManager.advanceTurn();
		} catch (GameOverException e) {
			this.isGameOver = true;
			return;
		}
		assert(!playerManager.isPlayersTurn(playerID));
		this.addHistoryMessage("Player " + playerID + " selected " + destinationsSelected.length + " cards");
		
	}
	
	public boolean containsPlayer(int playerID){
		for(Player p : playerManager.getPlayers()){
			if(p.getPlayerID() == playerID){
				return true;
			}
		}
		return false;
	}
	
	

}
