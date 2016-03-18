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
    @SerializedName("cityIndex1")
    private int cityIndex1;
    @SerializedName("cityIndex2")
    private int cityIndex2;
    @SerializedName("points")
    private int points;

    public DestinationCardInfo(DestinationRoute destinationRoute, List<City> cityList) {
        this.cityIndex1 = cityList.indexOf(destinationRoute.getStart());
        this.cityIndex2 = cityList.indexOf(destinationRoute.getEnd());
        this.points = destinationRoute.getPointValue();
    }
}
