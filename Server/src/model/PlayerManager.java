package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import server.exception.GameOverException;
import server.exception.OutOfBoundsException;

public class PlayerManager {
	protected List<Player> players;
	protected int currentTurnIndex;
	protected int finalTurnIndex;
	protected int round; //keeps track of how many full rotations have occurred. 
	public boolean drewAlreadyCurrentTurn;
	
	public PlayerManager(){
		players = new ArrayList<Player>();
		currentTurnIndex = 0;
		finalTurnIndex = -1;
		round = 0;
		drewAlreadyCurrentTurn = false;
	}
	
	/**
	 * Check if a player can buy track
	 * @param PlayerID
	 * @param trackLength
	 * @return
	 */
	public boolean canBuyTrack(int playerID, int trackLength)
	{
		//player must exist
		if(this.getPlayer(playerID) == null){
			return false;
		}
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
	public boolean canBuyTrackWithCards(int playerID, int trackLength, TrackColor color, Map<TrackColor,Integer> trainCards)
	{
		//helper function
		if(!this.canBuyTrack(playerID, trackLength)){
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
		
		//train cards actually need to be able to buy the track
		if(color != TrackColor.None){	//general case, track isn't gray
			int wildsNeeded = trackLength;
			wildsNeeded -= (trainCards.containsKey(color)) ? trainCards.get(color) : 0;
			if(wildsNeeded > 0 && trainCards.containsKey(TrackColor.None)){
				wildsNeeded -= trainCards.get(TrackColor.None);
			}
			return (wildsNeeded <= 0) ? true : false;
		}
		else{	//gray route, any color is possible
			assert(color == TrackColor.None);
			int wildsNeeded = trackLength;
			
			//uses any one color, plus whatever wilds are desired
			for(TrackColor tc : trainCards.keySet()){
				if(tc == TrackColor.None){
					continue;
				}
				else{
					int cards = trainCards.get(tc);
					if(cards > 0){
						wildsNeeded -= cards;
						break;
					}
				}
			}
			if(wildsNeeded > 0 && trainCards.containsKey(TrackColor.None)){
				wildsNeeded -= trainCards.get(TrackColor.None);
			}
			
			return (wildsNeeded <= 0) ? true : false;
			
		}
	}

	/**
	 * Advances to the next turn by incrementing the current turn index
	 * @throws GameOverException 
	 */
	public void advanceTurn() throws GameOverException
	{
		//detects whether final round has been triggered
		Player currentPlayer = players.get(currentTurnIndex);
		if(currentPlayer.getTrainsLeft() < 3){
			this.finalTurnIndex = currentTurnIndex;
		}
		
		currentTurnIndex += 1;
		currentTurnIndex %= players.size();
		drewAlreadyCurrentTurn = false;
		if(currentTurnIndex == 0){
			round++;
		}
		
		//if the game is over, throw an exception to the Game to end it all
		if(currentTurnIndex == finalTurnIndex){
			throw new GameOverException();
		}
	}
	
	/**
	 * 
	 * add destination routes for a player
	 * @param PlayerID
	 * @param routs
	 */
	protected void addDestinationRoute(int playerID, DestinationRoute route)
	{
		
		Player player = null;
		for(int i =0; i < players.size();i++)	
		{
			
			if(players.get(i).getPlayerID()==playerID)
			{ 
				
				player = players.get(i);
				
			}
			
			player.getDestinationRoute().add(route);
			assert(player.getDestinationRoute().contains(route));
		}
	}
	
	public void addDestinationRoutesToConsider(int playerID, List<DestinationRoute> routes){
		System.out.println("Destination routes to consider size: " + routes.size());
		Player player = null;
		for(Player p : players)	
		{	
			if(p.getPlayerID()==playerID)
			{ 
				player = p;
			}
		}
		if(player != null){
			player.setDestinationRoutesToConsider(routes.toArray(new DestinationRoute[routes.size()]));
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
	 * Removes the relevant resources from the player buying a track of the given characteristics and awards the relevant points
	 * @param PlayerID the player buying the track
	 * @param trackLength the length of track being bought
	 * @param color the color of the track
	 * @param trackCards the cards used to pay for it
	 * @throws OutOfBoundsException 
	 */
	public void buyTrack(int playerID, int trackLength, TrackColor color, Map<TrackColor,Integer> trackCards) throws OutOfBoundsException
	{
		if(trackLength < 1 || trackLength > 6){
			throw new OutOfBoundsException();
		}
		for(Player p : players){
			if(p.getPlayerID() == playerID){
				//award the relevant points
				int pointsToAdd = 0;
				switch(trackLength){
				case 6:
					pointsToAdd = 15;
					break;
				case 5:
					pointsToAdd = 10;
					break;
				case 4:
					pointsToAdd = 7;
					break;
				case 3:
					pointsToAdd = 4;
					break;
				default:
					pointsToAdd = trackLength;
					break;
				}
				p.addPoints(pointsToAdd);
				
				//remove the relevant resources
				Map<TrackColor, Integer> playerCards = p.getTrainCarCards();
				for(TrackColor tc : trackCards.keySet()){
					playerCards.put(tc, playerCards.get(tc) - trackCards.get(tc));
				}
				
				p.useTrains(trackLength);
				break;
			}
			
			
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

	protected Player getPlayer(int playerID){
		Player output = null;
		for(Player p : this.players){
			if(p.getPlayerID() == playerID){
				output = p;
				break;
			}
		}
		return output;
	}
	
	/**
	 * Determines whether the player with the given ID is currently the active player
	 * @param playerID the player being checked
	 * @return true if the player is the active player, false otherwise
	 */
	public boolean isPlayersTurn(int playerID){
		Player p = this.players.get(this.currentTurnIndex);
		return p.getPlayerID() == playerID;
	}
	
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	public boolean canSelectDestinations(int playerID, int[] destinationsSelected) {
		//player must have all the given destination indices
		for(Player p : players){
			if(p.getPlayerID() == playerID){
				for(int i : destinationsSelected){
					if(p.getDestinationRoutesToConsider()[i] == null){
						return false;
					}
				}
				break;
			}
		}
		
		//there must be at least 1 destination selected
		if(destinationsSelected.length < 1){
			return false;
		}
		return true;
	}

	/**
	 * Gives the selected indices to the player's permanent list of Destination cards, returns the rest
	 * @param playerID the Player doing the selection
	 * @param destinationsSelected indices of the selected destination cards
	 * @return all cards not selected
	 */
	public List<DestinationRoute> selectDestinations(int playerID, int[] destinationsSelected) {
		List<DestinationRoute> output = null;
		for(Player p : players){
			if(p.getPlayerID() == playerID){
				output = new ArrayList<DestinationRoute>(Arrays.asList(p.getDestinationRoutesToConsider()));
				for(int i : destinationsSelected){
					DestinationRoute dr = p.getDestinationRoutesToConsider()[i];
					this.addDestinationRoute(playerID, dr);
					output.remove(dr);
				}
				p.setDestinationRoutesToConsider(new DestinationRoute[3]);
			}
			break;
		}
		return output;
	}
	
	/**
	 * Indicates the total number of rounds elapsed. A round requires each player to play in turn
	 * @return the current round number. Setup phase is 0.
	 */
	public int getRoundNumber(){
		return round;
	}
	
	public boolean isFinalRound(){
		return (this.finalTurnIndex >= 0) ? true : false;
	}
}

