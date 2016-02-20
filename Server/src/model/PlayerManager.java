package model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PlayerManager {
	private List<Player> players;
	private int currentTurnIndex;
	private boolean drewAlreadyCurrentTurn;
	
	/**
	 * Check if a player can buy track
	 * @param PlayerID
	 * @param trackLength
	 * @param color
	 * @return
	 */
	public boolean canBuyTrack(int playerID, int trackLength, TrackColor color)
	{
		//must be current player
		if(players.get(currentTurnIndex).getPlayerID() != playerID){
			return false;
		}
		
		//some boundary checking on trackLength
		if(trackLength > 7 || trackLength < 1){
			return false;
		}
		
		//must have enough trains left to buy track
		if(players.get(currentTurnIndex).getTrainsLeft() < trackLength){
			return false;
		}
		
		return true;
	}
	
	/**
	 * check if a player can buy track with the given cards
	 * @param PlayerID
	 * @param trackLength
	 * @param color
	 * @param trainCards
	 * @return
	 */
	public boolean canBuyTrackWithCard(int playerID, int trackLength, TrackColor color, Map<TrackColor,Integer> trainCards)
	{
		//helper function
		if(!this.canBuyTrack(playerID, trackLength, color)){
			return false;
		}
		
		//player needs to have all the cards in the map
		Player player = players.get(this.currentTurnIndex);
		assert(player.getPlayerID() == playerID);
		for (TrackColor key : trainCards.keySet()){
			Map<TrackColor, Integer> playerCards = player.getTrainCarCards();
			if(!playerCards.containsKey(key) || playerCards.get(key) < trainCards.get(key)){
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Advances to the next turn by incrementing the current turn index
	 */
	public void advanceTurn()
	{
		currentTurnIndex += 1;
		currentTurnIndex %= players.size();
	}
	
	/**
	 * 
	 * add destination routes for a player
	 * @param PlayerID
	 * @param routs
	 */
	public void addDestinationRoutes(int PlayerID,List<DestinationRoute> routes)
	{
		
		Player player = null;
		for(int i =0; i < players.size();i++)	
		{
			
			if(players.get(i).getPlayerID()==PlayerID)
			{ 
				
				player = players.get(i);
				
			}
			
			player.getDestinationRoute().addAll(routes);
			assert(player.getDestinationRoute().containsAll(routes));
		}
	}
	/**
	 * add trainCarCard for a player
	 * @param playerID
	 * @param TrackColor
	 */
	public void addTrainCarCard(int playerID, TrackColor trackColor)
	{
		for(Player player : players){
			if(player.getPlayerID() == playerID){
				player.addTrainCarCard(trackColor);
			}
		}
	}
	/**
	 * add cards to a player identified by player id
	 * @param playerID
	 * @param cards
	 */
	public void addTrainCarCards(int playerID, Map<TrackColor,Integer> cards)
	{
		for(Player player : players){
			if(player.getPlayerID() == playerID){
				for(TrackColor color : cards.keySet()){
					for(int i = 0; i < cards.get(color); ++i){
						player.addTrainCarCard(color);
					}
				}
			}
		}
	}
	

	/**
	 * 
	 * buy track a player indentified by playerid
	 * @param PlayerID
	 * @param trackLength
	 * @param color
	 * @param trackCards
	 */
	
	public void buyTrack(int PlayerID, int trackLength, TrackColor color, Map<TrackColor,Integer> trackCards)
	{
		Player player = null;
		for(int i =0; i < players.size();i++)	
		{
			
			if(players.get(i).getPlayerID()==PlayerID)
			{ 
				
				player = players.get(i);
				
			}
			
			//we need card class here
			//Card tempCard = new Card(trackLength,color);
			//update trackCards
		}
	}
	/**
	 * add a new player identified by playerID and color to the game 
	 * @param playerID
	 * @param color
	 */
	public void addPlayer(int playerID, PlayerColor color)
	{
		Player newPlayer = new Player(playerID,color);
		players.add(newPlayer);
		
	}
	/**
	 * remove a player identified by id
	 * @param playerID
	 */
	public void removePlayer(int playerID)
	{
		int index = 0;
		for(int i =0; i < players.size();i++)	
		{
			
			if(players.get(i).getPlayerID()==playerID)
			{ 
				
				index =i;
				
			}
			

		}
		players.remove(index);
		
	}
	
	/**
	 * Reports the number of players currently in this game
	 * @return the number of players
	 */
	public int getNumPlayers(){
		return players.size();
	}
	
	/**
	 * generate hashcode function
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentTurnIndex;
		result = prime * result + (drewAlreadyCurrentTurn ? 1231 : 1237);
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		return result;
	}
/**
 * generate equal function
 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerManager other = (PlayerManager) obj;
		if (currentTurnIndex != other.currentTurnIndex)
			return false;
		if (drewAlreadyCurrentTurn != other.drewAlreadyCurrentTurn)
			return false;
		if (players == null) {
			if (other.players != null)
				return false;
		} else if (!players.equals(other.players))
			return false;
		return true;
	}

	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}
}

