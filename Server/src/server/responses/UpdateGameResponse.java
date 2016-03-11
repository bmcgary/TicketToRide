package server.responses;

import com.google.gson.annotations.SerializedName;
import model.Game;
import server.dto.GameInfo;

/**
 *
 * Created by rodriggl on 3/3/2016.
 */
public class UpdateGameResponse extends Response {
    @SerializedName("gameId")
    private int gameId;
    @SerializedName("game")
    private GameInfo gameInfo;

    public UpdateGameResponse(Game game, boolean isNew) {
        super("");
        this.gameId = game.getGameID();
        if (game.getPlayerManager().getPlayers().size() == 5) {
            super.description = "delete";
            this.gameInfo = null;
        } else {
            super.description = isNew ? "add" : "update";
            this.gameInfo = new GameInfo(game);
        }
    }

    public UpdateGameResponse(int gameId) {
        super("delete");
        this.gameId = gameId;
        this.gameInfo = null;
    }
}
