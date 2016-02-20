package model;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

	public void initialize() {
		//add 12 of each TrackColor to deck, 16 of locomotive
		for(TrackColor tc : TrackColor.values()){
			int toAdd = 12;
			if(tc == TrackColor.None){
				toAdd = 16;
			}
			for(int i = 0; i < toAdd; ++i){
				deckTrainCarCards.add(tc);
			}
		}
		
		//add 5 cards from TrainCarDeck into the visible pile
		for(int i = 0; i < this.visibleTrainCarCards.length; ++i){
			this.visibleTrainCarCards[i] = this.drawDeckTrainCar();
		}
		
		//use DestinationCards.txt to load in cities, destinationRoutes
		this.loadDestinationRoutes();
		
		//use CityToCityRoutes.txt to load in additional cities, routes list
		//TODO: do this. I'm waiting on @davishyer to get me the list
		
		
		
	}

	private void loadDestinationRoutes(){
		File file = new File("DestinationCards.txt");
		BufferedReader reader = null;
		int line = 0;
		String city1 = null;
		String city2 = null;;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String text = null;
			
			while((text = reader.readLine()) != null){
				line++;
				switch(line % 3){
				case 1:
					city1 = text;
					break;
				case 2:
					city2 = text;
					break;
				case 3:
					DestinationRoute dr = this.makeNewDestinationRoute(city1, city2, Integer.parseInt(text));
					city1 = null;
					city2 = null;
					this.destinationRoutes.add(dr);
				}
				
			}
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		} finally{
			try {
				if(reader != null){
					reader.close();
				}
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	private DestinationRoute makeNewDestinationRoute(String city1, String city2, int points) {
		//both shouldn't be null. That would be bad
		assert(city1 != null && city2 != null);
		
		//if either city doesn't exist yet, add them to the list
		City c1 = null;
		City c2 = null;
		for(City c : this.cities){
			if(c.getName() == city1){
				c1 = c;
			}
			else if(c.getName() == city2){
				c2 = c;
			}
		}
		if(c1 == null){
			c1 = new City(new Point(0, 0), city1);
			this.cities.add(c1);
		}
		if(c2 == null){
			c1 = new City(new Point(0, 0), city2);
			this.cities.add(c2);
		}
		return new DestinationRoute(c1, c2, points);
	}
	
}
