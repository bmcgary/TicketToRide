package model;

import java.util.List;

public class TestPlayerManager extends PlayerManager
{
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	public int getCurrentTurnIndex() {
		return currentTurnIndex;
	}
	public void setCurrentTurnIndex(int currentTurnIndex) {
		this.currentTurnIndex = currentTurnIndex;
	}
	public int getFinalTurnIndex() {
		return finalTurnIndex;
	}
	public void setFinalTurnIndex(int finalTurnIndex) {
		this.finalTurnIndex = finalTurnIndex;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public boolean isDrewAlreadyCurrentTurn() {
		return drewAlreadyCurrentTurn;
	}
	public void setDrewAlreadyCurrentTurn(boolean drewAlreadyCurrentTurn) {
		this.drewAlreadyCurrentTurn = drewAlreadyCurrentTurn;
	}
	
	public Player getPlayerByID(int playerID) {
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getPlayerID() == playerID)
			{
				return players.get(i);
			}
		}
		return null;
	}
	
	
}
