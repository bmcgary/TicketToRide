package model;

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
	public boolean canBuyTrack(int PlayerID, int trackLength, TrackColor color)
	{
		/*
		 * if(PlayerID != currentPlayerID||PlayerID<0||playerID>0)
		 * {
		 * retur false;
		 * 
		 * }
		 * 
		 * if(trackLength>max_possibleValue||trachLength<min_posssibleValue)
		 * {
		 * 
		 * return false;
		 * }
		 * 
		 * if(color is in use)
		 * {
		 * return false;
		 * }
		 * 
		 */
		
		return true;
	}
	
	/**
	 * check if a player can buy track with card
	 * @param PlayerID
	 * @param trackLength
	 * @param color
	 * @param trainCards
	 * @return
	 */
	public boolean canBuyTrackWithCard(int PlayerID, int trackLength, TrackColor color, Map<TrackColor,Integer> trainCards)
	{
		/*
		 * if(PlayerID != currentPlayerID||PlayerID<0||playerID>0)
		 * {
		 * retur false;
		 * 
		 * }
		 * 
		 * if(trackLength>max_possibleValue||trachLength<min_posssibleValue)
		 * {
		 * 
		 * return false;
		 * }
		 * 
		 * if(color is in use)
		 * {
		 * return false;
		 * }
		 * 
		 * if(map.get(color) < returnNumbertoBuy)
		 * {
		 *   return false;
		 * }
		 *  
		 */
		
		return true;
	}
	/**
	 * go to next turn
	 */
	public void advanceTurn()
	{
		//quit current turn? go to next turn??
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
		}
	}
	/**
	 * add trainCarCard for a player
	 * @param playerID
	 * @param TrackColor
	 */
	public void addTrainCarCard(int playerID, int TrackColor)
	{
		Player player = null;
		for(int i =0; i < players.size();i++)	
		{
			
			if(players.get(i).getPlayerID()==playerID)
			{ 
				
				player = players.get(i);
				
			}
			
			//player.addCarCard;
		}
	}
	/**
	 * add cards to a player identified by player id
	 * @param playerID
	 * @param cards
	 */
	public void addTrainCarCards(int playerID, Map<TrackColor,Integer> cards)
	{
		Player player = null;
		for(int i =0; i < players.size();i++)	
		{
			
			if(players.get(i).getPlayerID()==playerID)
			{ 
				
				player = players.get(i);
				
			}
			
			//player.addCarCard; add mutiple cards
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
}

