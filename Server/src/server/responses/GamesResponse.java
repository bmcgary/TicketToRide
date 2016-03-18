package server.responses;

import com.google.gson.annotations.SerializedName;
import server.dto.lobby.LobbyGameInfo;

import java.util.List;

/**
 *
 * Created by rodriggl on 3/3/2016.
 */
public class GamesResponse extends Response {
    @SerializedName("games")
    List<LobbyGameInfo> games;

    public GamesResponse(List<LobbyGameInfo> games) {
        super(getSuccessString());
        this.games = games;
    }
}
