package server.dto.lobby;

import com.google.gson.annotations.SerializedName;
import model.City;
import model.Game;
import model.Player;
import server.dto.GameInfo;
import server.dto.PlayerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * Created by rodriggl on 3/3/2016.
 */
public class LobbyGameInfo extends GameInfo {
    @SerializedName("gameName")
    private String gameName;
    @SerializedName("players")
    private List<PlayerInfo> playerInfos;

    public LobbyGameInfo(Game game) {
        super.gameId = game.getGameID();
        this.gameName = game.getName();
        this.playerInfos = game.getPlayerManager().getPlayers().parallelStream().map(PlayerInfo::new).collect(Collectors.toList());
    }

    @Override
    public List<Integer> getPlayerIds() {
        return playerInfos.parallelStream().map(PlayerInfo::getPlayerId).collect(Collectors.toList());
    }
}
