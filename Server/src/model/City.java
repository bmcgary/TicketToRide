package model;

import java.awt.Point;

/**
 * Represents a city on the map
 * @author Trent
 *
 */
public class City {
	private Point location;
	private String name;
	public City(Point location, String name){
		this.location = location;
		this.name = name;
	}
	
	/**
	 * 
	 * @return Location of the city represented as a point (x, y)
	 */
	public Point getLocation() {
		return location;
	}
	public String getName() {
		return name;
	}
	
	
}
