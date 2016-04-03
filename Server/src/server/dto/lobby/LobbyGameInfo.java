package server.dto.lobby;

import com.google.gson.annotations.SerializedName;
import model.City;
import model.Game;
import model.Player;
import server.ServerFacade;
import server.dto.GameInfo;
import server.dto.PlayerInfo;
import server.exception.InvalidCredentialsException;

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
    @SerializedName("hasStarted")
    private boolean hasStarted;

    public LobbyGameInfo(Game game) throws InvalidCredentialsException {
        super.gameId = game.getGameID();
        this.gameName = game.getName();

        // check to see if all players are valid
        List<Player> players = game.getPlayerManager().getPlayers();
        for (Player player : players) {
            ServerFacade.getServerFacade().getUser(player.getPlayerID());
        }

        this.playerInfos = game.getPlayerManager().getPlayers().parallelStream().map(PlayerInfo::new).collect(Collectors.toList());
        this.hasStarted = game.isStarted();
    }

    @Override
    public List<Integer> getPlayerIds() {
        return playerInfos.parallelStream().map(PlayerInfo::getPlayerId).collect(Collectors.toList());
    }
}
