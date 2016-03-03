package server.responses;

import com.google.gson.annotations.SerializedName;
import server.responses.dto.GameInfo;

/**
 *
 * Created by rodriggl on 3/3/2016.
 */
public class GamesResponse extends Response {
    @SerializedName("games")
    GameInfo[] games;

    public GamesResponse(GameInfo[] games, String description) {
        super(description);
        this.games = games;
    }
}
