package server.dto.gameplay;

import com.google.gson.annotations.SerializedName;
import model.Player;
import server.ServerFacade;
import server.dto.PlayerInfo;

import java.util.List;

/**
 *
 * Created by rodriggl on 3/15/2016.
 */
public class GamePlayerInfo extends PlayerInfo {
    @SerializedName("playerNam")
    private String playerName;
    @SerializedName("playerOrder")
    private int playerOrder;
    @SerializedName("routes")
    private List<Integer> routes;
    @SerializedName("trainsLeft")
    private int trainsLeft;

    public GamePlayerInfo(Player player, int playerOrder, List<Integer> routes) {
        super(player);
        // This is ugly
        this.playerName = ServerFacade.getServerFacade().getAllUsers().parallelStream().filter(user -> player.getPlayerID() == user.getPlayerID()).findFirst().get().getUsername();
        this.playerOrder = playerOrder;
        this.routes = routes;
        this.trainsLeft = player.getTrainsLeft();
    }
}
