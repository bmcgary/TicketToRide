package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents and controls all aspects of the game model that comprise the board.
 * @author Trent
 *
 */
public class GameBoard {
	private List<City> cities;
	private List<CityToCityRoute> routes;
	private Map<Integer, List<CityToCityRoute>> currentRoutes;
	private List<DestinationRoute> destinationRoutes;
	private List<TrackColor> deckTrainCarCards;
	private TrackColor[] visibleTrainCarCards;
	private List<TrackColor> discardedTrainCarCards;
	
	public GameBoard(){
		cities = new ArrayList<City>();
		routes = new ArrayList<CityToCityRoute>();
		currentRoutes = new HashMap<Integer, List<CityToCityRoute>>();
		destinationRoutes = new ArrayList<DestinationRoute>();
		visibleTrainCarCards = new TrackColor[5];
		deckTrainCarCards = new ArrayList<TrackColor>();
		discardedTrainCarCards = new ArrayList<TrackColor>();
		this.instantiate();
	}
	
	private void instantiate(){
		//TODO: Load in everything here:
		//load in all cities, routes
		//load in the deck of destination Routes
		//load in the deck of drawable TrainCarCards
		//load in the visible TrainCarCards to the array (remove them from the deck)
	}
	/**
	 * Reports whether a destination route can be drawn from the deck
	 * @return true if cards are remaining, false otherwise
	 */
	public boolean canDrawDestinationRoute(){
		if(destinationRoutes.size() > 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Reports whether a visible Train Car card can be drawn
	 * @param index the index (0-4) wished to be drawn
	 * @return false if there is no card at the given index
	 * @throws OutOfBoundsException if the index is less than 0 or greater than 4
	 */
	public boolean canDrawVisibleTrainCar(int index) throws OutOfBoundsException{
		if(index < 0 || index > 4){
			throw new OutOfBoundsException();
		}
		else if(visibleTrainCarCards[index] != null){
			return true;
		}
		return false;
	}
	
	/**
	 * Reports whether the train car deck can be drawn from
	 * If there are no cards left in the deck, tries to shuffle in the discard pile
	 * @return false if there are no cards remaining, true otherwise
	 */
	public boolean canDrawDeckTrainCar(){
		if(this.deckTrainCarCards.size() == 0){
			if(this.discardedTrainCarCards.size() > 0){
				Collections.shuffle(discardedTrainCarCards);
				deckTrainCarCards = discardedTrainCarCards;
				discardedTrainCarCards = new ArrayList<TrackColor>();
			}
			else{
				return false;
			}
		}
		return true;
	}

	/**
	 * Reports whether the given route is available for building
	 * @param route the route desired for building
	 * @return True if the route is unoccupied, false otherwise
	 */
	public boolean isRouteAvailable(CityToCityRoute route){
		for(Integer key : currentRoutes.keySet()){
			List<CityToCityRoute> list = currentRoutes.get(key);
			if(list.contains(route)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Reports whether a given CityToCityRoute exists on the map or not
	 * @param route the CityToCityRoute being checked for validity
	 * @return true if there exists a Route on the board that matches all the same characteristics, false otherwise
	 */
	public boolean isValidRoute(CityToCityRoute route){
		for(CityToCityRoute r : routes){
			if(r.equals(route)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes three DestinationRoutes from the routes list, returns them to the caller
	 * @return Three destination routes in a list, removed from the deck
	 */
	public List<DestinationRoute> drawDestinationRoutes(){
		List<DestinationRoute> output = new ArrayList<DestinationRoute>();
		for(int i = 0; i < 3; ++i){
			output.add(destinationRoutes.get(0));
			destinationRoutes.remove(0);
		}
		return output;
	}
	
	/**
	 * Takes in a list of DestinationRoutes and returns them to the deck
	 * @param routes the routes to be added back to the deck
	 */
	public void returnDestinationRoutes(List<DestinationRoute> routes){
		for(DestinationRoute route : routes){
			destinationRoutes.add(route);
		}
	}
	
	/**
	 * Given an index into the visible Train Car cards, returns the appropriate color and replaces that index
	 * @param index The index of the card desired
	 * @return the TrackColor corresponding to that index
	 * @throws OutOfBoundsException if the index is less than 0 or greater than 4
	 */
	public TrackColor drawVisibleTrainCar(int index) throws OutOfBoundsException{
		if(!this.canDrawVisibleTrainCar(index)){
			throw new OutOfBoundsException();
		}
		else{
			TrackColor output = visibleTrainCarCards[index];
			visibleTrainCarCards[index] = deckTrainCarCards.get(0);
			deckTrainCarCards.remove(0);
			return output;
		}
	}
	
	/**
	 * Removes a card from the TrainCar deck and returns it
	 * @return the TrackColor pertaining to the card, null if there are no card remaining
	 */
	public TrackColor drawDeckTrainCar(){
		TrackColor output = deckTrainCarCards.get(0);
		deckTrainCarCards.remove(0);
		return output;
	}
	
	/**
	 * Adds the given cards to the discard pile
	 * @param cards the cards to be discarded
	 */
	public void discardTrainCards(List<TrackColor> cards){
		for(TrackColor card : cards){
			discardedTrainCarCards.add(card);
		}
	}
	
	/**
	 * Given a playerID and route, claims that route for the player
	 * @param playerID the Player claiming the route
	 * @param route the route being claimed
	 * @return true if the route is successfully claimed, false otherwise
	 */
	public boolean claimRoute(int playerID, CityToCityRoute route){
		if(!this.isRouteAvailable(route)){
			return false;
		}
		//if the player doesn't yet have any claimed routes, add the player to the map
		if(!currentRoutes.containsKey(playerID)){
			currentRoutes.put(playerID, new ArrayList<CityToCityRoute>());
		}
		currentRoutes.get(playerID).add(route);
		return true;
	}

	public List<City> getCities() {
		return Collections.unmodifiableList(cities);
	}

	public List<CityToCityRoute> getRoutes() {
		return Collections.unmodifiableList(routes);
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
