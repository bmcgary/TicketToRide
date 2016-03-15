package server.responses;

import com.google.gson.annotations.SerializedName;
import model.Game;
import server.dto.lobby.LobbyGameInfo;

/**
 *
 * Created by rodriggl on 3/3/2016.
 */
public class UpdateGameResponse extends Response {
    @SerializedName("gameId")
    private int gameId;
    @SerializedName("game")
    private LobbyGameInfo lobbyGameInfo;

    public UpdateGameResponse(Game game, boolean isNew) {
        super("");
        this.gameId = game.getGameID();
        if (game.getPlayerManager().getPlayers().size() == 5) {
            super.description = "delete";
            this.lobbyGameInfo = null;
        } else {
            super.description = isNew ? "add" : "update";
            this.lobbyGameInfo = new LobbyGameInfo(game);
        }
    }

    public UpdateGameResponse(int gameId) {
        super("delete");
        this.gameId = gameId;
        this.lobbyGameInfo = null;
    }
}
