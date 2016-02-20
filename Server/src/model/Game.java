package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private GameBoard gameBoard;
	private PlayerManager playerManager;
	private static int nextID = 1;
	private int gameID;
	private List<String> history;
	private boolean started;
	
	public Game(){
		gameBoard = new GameBoard();
		started = false;
		playerManager = new PlayerManager();
		gameID = nextID++;
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
	
	public void startGame(){
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
				playerManager.addDestinationRoutes(p.getPlayerID(), gameBoard.drawDestinationRoutes());
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
		if(!playerManager.canBuyTrack(playerID, route.getNumTrains(), route.getTrackColor())){
			return false;
		}
		
		//player must have the appropriate resources
		for(int i = 0; i < route.getNumTrains(); ++i){	//this allows us to check every combination of wild cards/route color
			Map<TrackColor, Integer> trainCards = new HashMap<TrackColor, Integer>();
			trainCards.put(route.getTrackColor(), route.getNumTrains()-i);
			trainCards.put(TrackColor.None, i);
			if(playerManager.canBuyTrackWithCard(playerID, route.getNumTrains(), route.getTrackColor(), trainCards)){
				return true;
			}
		}
		return false;
	}

	public void buyRoute(int playerID, CityToCityRoute route) throws PreConditionException {
		//helper method
		if(!canPlayerBuyRoute(playerID, route)){
			throw new PreConditionException("Preconditions not met for player " + playerID + " buying the route");
		}
		
		//remove resources from player
		for(int i = 0; i < route.getNumTrains(); ++i){	//this allows us to check every combination of wild cards/route color
			Map<TrackColor, Integer> trainCards = new HashMap<TrackColor, Integer>();
			trainCards.put(route.getTrackColor(), route.getNumTrains()-i);
			trainCards.put(TrackColor.None, i);
			if(playerManager.canBuyTrackWithCard(playerID, route.getNumTrains(), route.getTrackColor(), trainCards)){
				//this assumes the player will want to use regular cards before wild cards
				playerManager.buyTrack(playerID, route.getNumTrains(), route.getTrackColor(), trainCards);
				return;
			}
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
		
		//5 means top of the deck
		if(cardLocation == 5 && gameBoard.canDrawDeckTrainCar()){
			return true;
		}
		
		//0-4 means visible cards
		if(cardLocation >= 0 && cardLocation < 5){
			//if the player already drew this turn, they can't get one of the visible cards
			if(playerManager.drewAlreadyCurrentTurn){
				return false;
			}
			else{
				return gameBoard.canDrawVisibleTrainCar(cardLocation);
			}
		}
		throw new InternalServerException("Trent messed up in Game::canPlayerDrawTrainCard");
	}

	public void drawTrainCard(int playerID, int cardLocation) throws PreConditionException, OutOfBoundsException, InternalServerException {
		//helper method
		if(!this.canPlayerDrawTrainCard(playerID, cardLocation)){
			throw new PreConditionException("Preconditions not met to draw train card");
		}
		
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
		
	}
	
	

}
