package server.dto.gameplay;

import com.google.gson.annotations.SerializedName;
import model.City;
import model.CityToCityRoute;
import model.Game;
import model.Player;
import server.ServerFacade;
import server.dto.GameInfo;
import server.dto.PlayerInfo;
import server.exception.InvalidCredentialsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * Created by rodriggl on 3/15/2016.
 */
public class GamePlayInfo extends GameInfo {
    @SerializedName("availableRoutes")
    private List<Integer> availableRoutes;
    @SerializedName("players")
    private List<GamePlayerInfo> gamePlayerInfos;

    private transient Map<Integer, PrivatePlayerInfo> privatePlayerInfos;

    public GamePlayInfo(Game game) throws InvalidCredentialsException {
        super.gameId = game.getGameID();
        this.gamePlayerInfos = new ArrayList<>();
        this.privatePlayerInfos = new HashMap<>();
        List<Player> players = game.getPlayerManager().getPlayers();
        Map<Integer,CityToCityRoute> mapping = ServerFacade.getCityMapping();
        this.availableRoutes = mapping.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
        for (int i = 0; i < players.size(); ++i) {
            List<CityToCityRoute> playerRoutes = game.getGameBoard().getCurrentRoutes().get(players.get(i).getPlayerID());
            List<Integer> convertedRoutes = playerRoutes == null ? null : playerRoutes.parallelStream().map(route -> mapping.entrySet().stream().filter(e -> e.getValue().equals(route)).findFirst().get().getKey()).collect(Collectors.toList());
            Player player = players.get(i);
            this.gamePlayerInfos.add(new GamePlayerInfo(player, i, convertedRoutes));
            this.privatePlayerInfos.put(player.getPlayerID(), new PrivatePlayerInfo(super.gameId, player, i));

            // remove from available list
            if (convertedRoutes != null)
                this.availableRoutes.removeAll(convertedRoutes);
        }
    }

    @Override
    public List<Integer> getPlayerIds() {
        return gamePlayerInfos.parallelStream().map(PlayerInfo::getPlayerId).collect(Collectors.toList());
    }

    public PrivatePlayerInfo getPrivateInfo(Integer playerId) {
        return privatePlayerInfos.get(playerId);
    }
}
