package server.responses;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by rodriggl on 3/13/2016.
 */
public class StartGameResponse extends Response {
    @SerializedName("gameId")
    private int gameId;
    @SerializedName("initialTrainCards")
    private int[] trainCards;
    @SerializedName("initialDestinations")
    private int[] destinations;

    public StartGameResponse() {
        super("game has started");
    }
}
