package server.dto.gameplay;

import com.google.gson.annotations.SerializedName;
import model.Player;
import server.dto.PlayerInfo;

import java.util.List;

/**
 *
 * Created by rodriggl on 3/15/2016.
 */
public class GamePlayerInfo extends PlayerInfo {
    @SerializedName("playerOrder")
    private int playerOrder;
    @SerializedName("routes")
    private List<Integer> routes;
    @SerializedName("trainsLeft")
    private int trainsLeft;

    public GamePlayerInfo(Player player, int playerOrder, List<Integer> routes) {
        super(player);
        this.playerOrder = playerOrder;
        this.routes = routes;
        this.trainsLeft = player.getTrainsLeft();
    }
}
