package model;

/**
 * Represents a destination route, which a player must complete in order to gain the points.
 * Any routes completed earn its holder points at the end of the game, any uncompleted routes lose points
 * @author Trent
 *
 */
public class DestinationRoute extends Route {
	private int pointValue;

	public int getPointValue() {
		return pointValue;
	}
	

}
