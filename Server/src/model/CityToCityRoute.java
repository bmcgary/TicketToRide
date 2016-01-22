package model;

/**
 * Represents a route which may be built upon, extending from one city to the next
 * A CityToCityRoute has no intermediate cities
 * @author Trent
 *
 */
public class CityToCityRoute extends Route {
	private int numTrains;
	private TrackColor trackColor;
	public int getNumTrains() {
		return numTrains;
	}
	public TrackColor getTrackColor() {
		return trackColor;
	}
	
	

}
