package model;

import java.util.List;
import java.util.Map;

public class TestGameBoard extends GameBoard{

	public List<City> getCities() {
		return cities;
	}
	public List<CityToCityRoute> getRoutes() {
		return routes;
	}
	public Map<Integer, List<CityToCityRoute>> getCurrentRoutes() {
		return currentRoutes;
	}
	public void setCurrentRoutes(Map<Integer, List<CityToCityRoute>> currentRoutes) {
		this.currentRoutes = currentRoutes;
	}
	public List<DestinationRoute> getDestinationRoutes() {
		return destinationRoutes;
	}
	public void setDestinationRoutes(List<DestinationRoute> destinationRoutes) {
		this.destinationRoutes = destinationRoutes;
	}
	public List<TrackColor> getDeckTrainCarCards() {
		return deckTrainCarCards;
	}
	public void setDeckTrainCarCards(List<TrackColor> deckTrainCarCards) {
		this.deckTrainCarCards = deckTrainCarCards;
	}
	public TrackColor[] getVisibleTrainCarCards() {
		return visibleTrainCarCards;
	}
	public void setVisibleTrainCarCards(TrackColor[] visibleTrainCarCards) {
		this.visibleTrainCarCards = visibleTrainCarCards;
	}
	public List<TrackColor> getDiscardedTrainCarCards() {
		return discardedTrainCarCards;
	}
	public void setDiscardedTrainCarCards(List<TrackColor> discardedTrainCarCards) {
		this.discardedTrainCarCards = discardedTrainCarCards;
	}
	
}
