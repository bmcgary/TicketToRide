package model;

import java.awt.Point;

/**
 * Represents a city on the map
 * @author Trent
 *
 */
public class City {
	private String name;
	public City(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public boolean equals(Object o){
		if(this.getClass() != o.getClass()){
			return false;
		}
		City obj = (City) o;
		if(!this.getName().equals(obj.getName())){
			return false;
		}
		else{
			return true;
		}
	}
	
	
}
