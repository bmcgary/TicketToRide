package model;

/**
 * Represents a route as composed of two cities
 * @author Trent
 *
 */
public abstract class Route {
	private City start;
	private City end;
	
	public City getStart() {
		return start;
	}
	public City getEnd() {
		return end;
	}
	
}
