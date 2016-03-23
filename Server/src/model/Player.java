package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.User;
import server.exception.OutOfBoundsException;

/**
 * Represents a player in a given game. Links to universal users by means of ID
 * @author Chao
 *
 */
public class Player {
	protected int ID;
	protected int numTrainsLeft;
	protected List<DestinationRoute> destinationRoutes;
	protected List<DestinationRoute> destinationRoutesToConsider;
	protected Map<TrackColor,Integer> trainCarCards;
	protected PlayerColor color;
	protected int pointsScored;
	protected boolean longestRoute;
	
	Player(int ID, PlayerColor color)
	{
		this.ID=ID;
		this.color = color;
		this.pointsScored = 0;
		this.trainCarCards = new HashMap<TrackColor, Integer>();
		this.destinationRoutes = new ArrayList<DestinationRoute>();
		this.destinationRoutesToConsider = null;
		this.numTrainsLeft = 45;
	}
	
	public List<DestinationRoute> getDestinationRoute()
	{
		return destinationRoutes;
	}
	
	public List<DestinationRoute> getDestinationRoutesToConsider(){
		return destinationRoutesToConsider;
	}
	
	public void setDestinationRoutesToConsider(List<DestinationRoute> routes){
		this.destinationRoutesToConsider = routes;
	}
	
	public int getPlayerID()
	{
		return ID;
	}
	
	public int getTrainsLeft()
	{
		return	numTrainsLeft;
	}
	
	/**
	 * use num trains
	 * @param num
	 * @throws OutOfBoundsException 
	 */
	public void useTrains(int num) throws OutOfBoundsException
	{
		if(this.numTrainsLeft < num){
			throw new OutOfBoundsException("Not enough trains remaining");
		}
		this.numTrainsLeft -= num;
	}

	public Map<TrackColor,Integer> getTrainCarCards()
	{
		return trainCarCards;
	}
	
	/**
	 * add points
	 * @param num
	 */
	public void addPoints(int num)
	{
		this.pointsScored += num;
	}
	/**
	 * check if the player has the longest route
	 * @return
	 */
	
	public boolean hasLongestRoute()
	{
		return longestRoute;
	}
	
	/**
	 * Adds a train car of the given color to the player
	 * @param trackColor the track color to be added
	 */
	public void addTrainCarCard(TrackColor trackColor) {
		int count = trainCarCards.containsKey(trackColor) ? trainCarCards.get(trackColor) + 1 : 1;
		trainCarCards.put(trackColor, count);
	}
	
	public PlayerColor getPlayerColor(){
		return this.color;
	}
	
	/**
	 * generate hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime
				* result
				+ ((destinationRoutes == null) ? 0 : destinationRoutes
						.hashCode());
		result = prime * result + (longestRoute ? 1231 : 1237);
		result = prime * result + numTrainsLeft;
		result = prime * result + pointsScored;
		result = prime * result
				+ ((trainCarCards == null) ? 0 : trainCarCards.hashCode());
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
		Player other = (Player) obj;
		if (color != other.color)
			return false;
		if (destinationRoutes == null) {
			if (other.destinationRoutes != null)
				return false;
		} else if (!destinationRoutes.equals(other.destinationRoutes))
			return false;
		if (longestRoute != other.longestRoute)
			return false;
		if (numTrainsLeft != other.numTrainsLeft)
			return false;
		if (pointsScored != other.pointsScored)
			return false;
		if (trainCarCards == null) {
			if (other.trainCarCards != null)
				return false;
		} else if (!trainCarCards.equals(other.trainCarCards))
			return false;
		return true;
	}

	public int getDestinationPoints() {
		int output = 0;
		for(DestinationRoute dr : destinationRoutes){
			if(dr.isCompleted()){
				output += dr.getPointValue();
			}
			else{
				output -= dr.getPointValue();
			}
		}
		return output;
	}

}
