package model;

import java.util.List;

public class TestGame extends Game{
	
	public GameBoard getGameBoard() {
		return gameBoard;
	}
	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	public PlayerManager getPlayerManager() {
		return playerManager;
	}
	public void setPlayerManager(PlayerManager playerManager) {
		this.playerManager = playerManager;
	}
	public int getGameID() {
		return gameID;
	}
	public List<String> getHistory() {
		return history;
	}
	public void setHistory(List<String> history) {
		this.history = history;
	}
	public boolean isStarted() {
		return started;
	}
	public void setStarted(boolean started) {
		this.started = started;
	}
	public boolean isGameOver() {
		return isGameOver;
	}
	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}
	
	

}
