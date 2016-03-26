package server.dto;

import com.google.gson.annotations.SerializedName;
import model.Player;

/**
 *
 * Created by rodriggl on 3/25/2016.
 */
public class GameEndPlayerInfo extends PlayerInfo {
    @SerializedName("playerIndex")
    private int playerIndex;
    @SerializedName("points")
    private int points;
    @SerializedName("hasLongestRoute")
    private boolean hasLongestRoute;


    public GameEndPlayerInfo(Player player, int playerIndex) {
        super(player);
        this.playerIndex = playerIndex;
        this.points = player.getPointsScored();
        this.hasLongestRoute = player.hasLongestRoute();
    }
}
