package server.responses;

import com.google.gson.annotations.SerializedName;
import model.Game;
import model.Player;
import server.dto.GameEndPlayerInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by rodriggl on 3/25/2016.
 */
public class GameEndedResponse extends Response {
    @SerializedName("gameId")
    private int gameId;
    @SerializedName("players")
    private List<GameEndPlayerInfo> players;

    public GameEndedResponse(Game game) {
        super(null);
        this.gameId = game.getGameID();
        players = new ArrayList<>();
        List<Player> gamePlayers = game.getPlayerManager().getPlayers();
        for (int i = 0; i < gamePlayers.size(); ++i) {
            players.add(new GameEndPlayerInfo(gamePlayers.get(i), i));
        }
    }

    public static String getName() {
        return "GameEnded";
    }
}
