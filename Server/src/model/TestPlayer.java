package model;

import java.util.List;
import java.util.Map;

public class TestPlayer extends Player{

	TestPlayer(int ID, PlayerColor color) 
	{
		super(ID, color);
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getNumTrainsLeft() {
		return numTrainsLeft;
	}
	public void setNumTrainsLeft(int numTrainsLeft) {
		this.numTrainsLeft = numTrainsLeft;
	}
	public List<DestinationRoute> getDestinationRoutes() {
		return destinationRoutes;
	}
	public void setDestinationRoutes(List<DestinationRoute> destinationRoutes) {
		this.destinationRoutes = destinationRoutes;
	}
	public List<DestinationRoute> getDestinationRoutesToConsider() {
		return destinationRoutesToConsider;
	}
	public void setDestinationRoutesToConsider(
			List<DestinationRoute> destinationRoutesToConsider) {
		this.destinationRoutesToConsider = destinationRoutesToConsider;
	}
	public Map<TrackColor, Integer> getTrainCarCards() {
		return trainCarCards;
	}
	public void setTrainCarCards(Map<TrackColor, Integer> trainCarCards) {
		this.trainCarCards = trainCarCards;
	}
	public PlayerColor getColor() {
		return color;
	}
	public void setColor(PlayerColor color) {
		this.color = color;
	}
	public int getPointsScored() {
		return pointsScored;
	}
	public void setPointsScored(int pointsScored) {
		this.pointsScored = pointsScored;
	}
	public boolean isLongestRoute() {
		return longestRoute;
	}
	public void setLongestRoute(boolean longestRoute) {
		this.longestRoute = longestRoute;
	}
	
	
}
