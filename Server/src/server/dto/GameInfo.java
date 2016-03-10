package server.dto;

import com.google.gson.annotations.SerializedName;
import model.Game;
import model.Player;

import java.util.List;

/**
 *
 *
 * Created by rodriggl on 3/3/2016.
 */
public class GameInfo {
    @SerializedName("gameId")
    private int gameId;
    @SerializedName("gameName")
    private String gameName;
    @SerializedName("players")
    private PlayerInfo[] playerInfos;

    public GameInfo(Game game) {
        gameId = game.getGameID();
        gameName = game.getName();
        List<Player> players = game.getPlayerManager().getPlayers();
        playerInfos = new PlayerInfo[players.size()];
        for (int i = 0; i < players.size(); ++i) {
            playerInfos[i] = new PlayerInfo(players.get(i), i);
        }
    }
}
