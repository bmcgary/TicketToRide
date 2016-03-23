package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a destination route, which a player must complete in order to gain the points.
 * Any routes completed earn its holder points at the end of the game, any uncompleted routes lose points
 * @author Trent
 *
 */
public class DestinationRoute extends Route {
	private int pointValue;
	private boolean completed;
	
	public DestinationRoute(City start, City end, int points){
		super(start, end);
		this.pointValue = points;
	}

	public int getPointValue() {
		return pointValue;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	/**
	 * Determines whether the route has been completed based on a list of the C2C routes handed in
	 * Please note that this calculation goes START to END
	 * @param list the list of cities used to determine completion
	 */
	public void calcCompleted(List<CityToCityRoute> list) {
		if(this.isCompleted()){
			return;
		}
		
		for(CityToCityRoute c2cr: list){
			if(c2cr.getStart().equals(this.getStart())){
				List<CityToCityRoute> reducedList = new ArrayList<CityToCityRoute>(list);
				reducedList.remove(c2cr);
				if(this.rRouteCompleted(c2cr.getEnd(), reducedList)){
					this.setCompleted(true);
					return;
				}
			}
			else if(c2cr.getEnd().equals(this.getStart())){
				List<CityToCityRoute> reducedList = new ArrayList<CityToCityRoute>(list);
				reducedList.remove(c2cr);
				if(this.rRouteCompleted(c2cr.getStart(), reducedList)){
					this.setCompleted(true);
					return;
				}
			}
		}
		
	}
	
	/**
	 * Essentially, this is a dominoes recursive call where you check for another C2CRoute that has the lastCity in it
	 * and you keep searching and removing until you find the end (or don't). 
	 * @param lastCity most recently discovered city along the path
	 * @param list the remaining C2CRoutes to be used to attempt route completion
	 * @return true if the end city is eventually found, false otherwise
	 */
	private boolean rRouteCompleted(City lastCity, List<CityToCityRoute> list){
		for(CityToCityRoute c2cr: list){
			if(c2cr.getStart().equals(lastCity)){
				if(this.getEnd().equals(c2cr.getEnd())){	//base case
					return true;
				}
				List<CityToCityRoute> reducedList = new ArrayList<CityToCityRoute>(list);
				reducedList.remove(c2cr);
				if(this.rRouteCompleted(c2cr.getEnd(), reducedList)){	//recursive call
					return true;
				}
			}
			else if(c2cr.getEnd().equals(lastCity)){
				if(this.getEnd().equals(c2cr.getStart())){	//base case
					return true;
				}
				List<CityToCityRoute> reducedList = new ArrayList<CityToCityRoute>(list);
				reducedList.remove(c2cr);
				if(this.rRouteCompleted(c2cr.getStart(), reducedList)){		//recursive call
					return true;
				}
			}
		}
		return false;
	}
	

}
