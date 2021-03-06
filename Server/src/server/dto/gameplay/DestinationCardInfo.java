package server.dto.gameplay;

import com.google.gson.annotations.SerializedName;
import model.City;
import model.DestinationRoute;
import server.ServerFacade;

import java.util.List;

/**
 * Wrapper around Destination Route
 *
 * Created by rodriggl on 3/13/2016.
 */
public class DestinationCardInfo {
    @SerializedName("city1")
    private String city1;
    @SerializedName("city2")
    private String city2;
    @SerializedName("points")
    private int points;
    @SerializedName("completed")
    private Boolean completed;

    public DestinationCardInfo(DestinationRoute destinationRoute) {
        this(destinationRoute, false);
    }

    public DestinationCardInfo(DestinationRoute destinationRoute, boolean isPossible) {
        this.city1 = destinationRoute.getStart().getName();
        this.city2 = destinationRoute.getEnd().getName();
        this.points = destinationRoute.getPointValue();
        this.completed = isPossible ? null : destinationRoute.isCompleted();
    }
}
