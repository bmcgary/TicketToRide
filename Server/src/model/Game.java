package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		// TODO Auto-generated method stub
		return false;
	}

	public void buyRoute(int playerID, CityToCityRoute route) {
		// TODO Auto-generated method stub
	}
	
	

}
