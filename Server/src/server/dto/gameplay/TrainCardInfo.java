package server.dto.gameplay;

import com.google.gson.annotations.SerializedName;
import model.TrackColor;

import java.util.Map;

/**
 *
 * Created by rodriggl on 3/15/2016.
 */
public class TrainCardInfo {
    @SerializedName("color")
    private String trackColor;
    @SerializedName("amount")
    private int count;

    public TrainCardInfo(Map.Entry<TrackColor, Integer> entry) {
        this.trackColor = entry.getKey().toString();
        this.count = entry.getValue();
    }
}
