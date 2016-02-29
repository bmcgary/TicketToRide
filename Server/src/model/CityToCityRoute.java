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
	
	public CityToCityRoute(City start, City end, int numTrains, TrackColor color) {
		super(start, end);
		this.numTrains = numTrains;
		this.trackColor = color;
	}
	public int getNumTrains() {
		return numTrains;
	}
	public TrackColor getTrackColor() {
		return trackColor;
	}
	@Override
	public boolean equals(Object o){
		if(o.getClass() != this.getClass()){
			return false;
		}
		CityToCityRoute obj = (CityToCityRoute) o;
		if(!this.getStart().equals(obj.getStart())){
			return false;
		}
		else if(!this.getEnd().equals(obj.getEnd())){
			return false;
		}
		else if(this.getNumTrains() != obj.getNumTrains()){
			return false;
		}
		else{
			return true;
		}
	}
	

}
