package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.User;

public class Player {
	private User user;
	private int ID;
	private int numTrainsLeft;
	private List<DestinationRoute> destinationRoutes;
	private Map<TrackColor,Integer> trainCarCards;
	private PlayerColor color;
	private int pointsScored;
	private boolean longestRoute;
	
	
	
	/**
	 * Constructor
	 */
	Player(int ID,PlayerColor color)
	{
		this.ID=ID;
		this.color = color;
	}
	
	//do we add destionation routes here?
	/**
	 * return player's destination routes
	 * 
	 * 
	 */
	public List<DestinationRoute> getDestinationRoute()
	{
		return destinationRoutes;
	}
	/**
	 * return player ID;
	 */
	
	public int getPlayerID()
	{
		return ID;
	}
	
	/*
	 * 
	 * public List<Card> getCardsOnHands(){
	 *  return cards;
	 * }
	 */
	
	
	
	
	/*
	 * 
	 * public PlayerColor getColor()
	 * {
	 * 		return 
	 * }
	 * 
	 */
	
	
	/*
	 * add cards for players?
	 * public void addToHand(Card card){}
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	/*get cards
	 * 
	 * public List<Card> getCards()
	 * {
	 * 		return new List<Card>();
	 * }
	 * 
	 * 
	 * 
	 */
	
	/*
	 * remove cards
	 * 
	 * 
	 * public void RemoveCard(Card card){}
	 * 
	 * 
	 * 
	 */
	
	/*
	 * remove card list
	 * 
	 * public void RemoveCards(List<Card> cards)
	 * 	 
	 * 
	 */
	/**
	 * return how many trains left for the user
	 * @return
	 */
	public int getTrainsLeft()
	{
		return	numTrainsLeft;
	}
	/**
	 * user num trains
	 * @param num
	 */
	public void useTrains(int num)
	{
		
	}
	/**
	 * return player's train car cards
	 * @return
	 */
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
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
