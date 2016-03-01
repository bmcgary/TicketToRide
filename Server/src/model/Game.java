package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
	
	public Game(){
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
			for(int i = 0; i < 3; ++i){
				playerManager.addDestinationRoutesToConsider(p.getPlayerID(), gameBoard.drawDestinationRoutes());
			}
		}
		
		//locks new players out now, declares the game has been started
		started = true;
		this.addHistoryMessage("Game initialized");
	}
	
	public boolean isStarted(){
		return started;
	}

	public boolean canPlayerBuyRoute(int playerID, CityToCityRoute route) {
		//route must be available
		if(!gameBoard.isRouteAvailable(route)){
			return false;
		}
		
		//player must be able to buy tracks in general
		if(!playerManager.canBuyTrack(playerID, route.getNumTrains())){
			return false;
		}
		
		//player must have the appropriate resources
		for(int i = 0; i < route.getNumTrains(); ++i){	//this allows us to check every combination of wild cards/route color
			Map<TrackColor, Integer> trainCards = new HashMap<TrackColor, Integer>();
			trainCards.put(route.getTrackColor(), route.getNumTrains()-i);
			trainCards.put(TrackColor.None, i);
			if(playerManager.canBuyTrackWithCards(playerID, route.getNumTrains(), route.getTrackColor(), trainCards)){
				return true;
			}
		}
		return false;
	}

	public void buyRoute(int playerID, CityToCityRoute route) throws PreConditionException, OutOfBoundsException {
		//remove resources from player
		for(int i = 0; i < route.getNumTrains(); ++i){	//this allows us to check every combination of wild cards/route color
			Map<TrackColor, Integer> trainCards = new HashMap<TrackColor, Integer>();
			trainCards.put(route.getTrackColor(), route.getNumTrains()-i);
			trainCards.put(TrackColor.None, i);
			if(playerManager.canBuyTrackWithCards(playerID, route.getNumTrains(), route.getTrackColor(), trainCards)){
				//this assumes the player will want to use regular cards before wild cards
				playerManager.buyTrack(playerID, route.getNumTrains(), route.getTrackColor(), trainCards);
				//return the cards to the gameBoard discarded deck
				List<TrackColor> toDiscard = new ArrayList<TrackColor>();
				for(TrackColor tc : trainCards.keySet()){
					for(int j = 0; j < trainCards.get(tc); ++j){
						toDiscard.add(tc);
					}
				}
				gameBoard.discardTrainCards(toDiscard);
				try {
					playerManager.advanceTurn();
				} catch (GameOverException e) {
					this.isGameOver = true;
				}
				return;
			}
		}
		
		//assigns the route to the player
		gameBoard.claimRoute(playerID, route);
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
		
	}

	public boolean canPlayerGetDestinations(int playerID) {
		//must be player's turn
		if(!playerManager.isPlayersTurn(playerID)){
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
