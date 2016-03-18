package server.dto.gameplay;

import com.google.gson.annotations.SerializedName;
import model.City;
import model.CityToCityRoute;
import model.Game;
import model.Player;
import server.dto.GameInfo;
import server.dto.PlayerInfo;

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

    public GamePlayInfo(Game game) {
        super.gameId = game.getGameID();
        this.gamePlayerInfos = new ArrayList<>();
        this.privatePlayerInfos = new HashMap<>();
        List<Player> players = game.getPlayerManager().getPlayers();
        List<City> cities = game.getGameBoard().getCities();
        List<CityToCityRoute> routes = game.getGameBoard().getRoutes();
        for (int i = 0; i < players.size(); ++i) {
            List<CityToCityRoute> playerRoutes = game.getGameBoard().getCurrentRoutes().get(i);
            List<Integer> convertedRoutes = playerRoutes.parallelStream().map(routes::indexOf).collect(Collectors.toList());
            Player player = players.get(i);
            this.gamePlayerInfos.add(new GamePlayerInfo(player, i, convertedRoutes));
            this.privatePlayerInfos.put(player.getPlayerID(), new PrivatePlayerInfo(player, i, cities));
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