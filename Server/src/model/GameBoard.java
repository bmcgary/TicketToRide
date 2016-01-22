package model;

import java.util.List;
import java.util.Map;

/**
 * Represents and controls all aspects of the game model that comprise the board.
 * @author Trent
 *
 */
public class GameBoard {
	private List<City> cities;
	private List<Route> routes;
	private Map<Integer, List<CityToCityRoute>> currentRoutes;
	private List<DestinationRoute> destinationRoutes;
	private TrackColor[] visibleTrainCarCards;
	private List<TrackColor> deckTrainCarCards;
	private List<TrackColor> discardedTrainCarCards;
	
	/**
	 * Reports whether a destination route can be drawn from the deck
	 * @return true if cards are remaining, false otherwise
	 */
	public boolean canDrawDestinationRoute(){
		return false;
	}
	
	/**
	 * Reports whether a visible Train Car card can be drawn
	 * @param index the index (0-4) wished to be drawn
	 * @return false if there is no card at the given index
	 * @throws OutOfBoundsException if the index is less than 0 or greater than 4
	 */
	public boolean canDrawVisibleTrainCar(int index) throws OutOfBoundsException{
		return false;
	}
	
	/**
	 * Reports whether the train car deck can be drawn from
	 * @return false if there are no cards remaining, true otherwise
	 */
	public boolean canDrawDeckTrainCar(){
		return false;
	}

	/**
	 * Reports whether the given route is available for building
	 * @param route the route desired for building
	 * @return True if the route is unoccupied, false otherwise
	 */
	public boolean isRouteAvailable(CityToCityRoute route){
		return false;
	}
	
	/**
	 * Reports whether a given CityToCityRoute exists on the map or not
	 * @param route the CityToCityRoute being checked for validity
	 * @return true if there exists a Route on the board that matches all the same characteristics, false otherwise
	 */
	public boolean isValidRoute(CityToCityRoute route){
		return false;
	}
	
	/**
	 * Removes three DestinationRoutes from the routes list, returns them to the caller
	 * @return Three destination routes in a list, removed from the deck
	 */
	public List<DestinationRoute> drawDestinationRoutes(){
		return null;
	}
	
	/**
	 * Takes in a list of DestinationRoutes and returns them to the deck
	 * @param routes the routes to be added back to the deck
	 */
	public void returnDestinationRoutes(List<DestinationRoute> routes){
		
	}
	
	/**
	 * Given an index into the visible Train Car cards, returns the appropriate color and replaces that index
	 * @param index The index of the card desired
	 * @return the TrackColor corresponding to that index
	 * @throws OutOfBoundsException if the index is less than 0 or greater than 4
	 */
	public TrackColor drawVisibleTrainCar(int index) throws OutOfBoundsException{
		return TrackColor.None;
	}
	
	/**
	 * Removes a card from the TrainCar deck and returns it
	 * @return the TrackColor pertaining to the card, null if there are no card remaining
	 */
	public TrackColor drawDeckTrainCar(){
		return TrackColor.None;
	}
	
	/**
	 * Adds the given cards to the discard pile
	 * @param cards the cards to be discarded
	 */
	public void discardTrainCards(List<TrackColor> cards){
		
	}
	
	/**
	 * Given a playerID and route, claims that route for the player
	 * @param playerID the Player claiming the route
	 * @param route the route being claimed
	 * @return true if the route is successfully claimed, false otherwise
	 */
	public boolean claimRoute(int playerID, CityToCityRoute route){
		return false;
	}

	public List<City> getCities() {
		return cities;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public Map<Integer, List<CityToCityRoute>> getCurrentRoutes() {
		return currentRoutes;
	}

	public List<DestinationRoute> getDestinationRoutes() {
		return destinationRoutes;
	}

	public TrackColor[] getVisibleTrainCarCards() {
		return visibleTrainCarCards;
	}

	public List<TrackColor> getDeckTrainCarCards() {
		return deckTrainCarCards;
	}

	public List<TrackColor> getDiscardedTrainCarCards() {
		return discardedTrainCarCards;
	}
	
}
