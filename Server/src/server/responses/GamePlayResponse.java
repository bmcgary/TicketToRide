package server.responses;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by rodriggl on 3/25/2016.
 */
public class GamePlayResponse extends Response {
    @SerializedName("gameId")
    private int gameId;

    public GamePlayResponse(String description, int gameId) {
        super(description);
        this.gameId = gameId;
    }
}
