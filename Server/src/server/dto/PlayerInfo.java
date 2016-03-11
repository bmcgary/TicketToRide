package server.dto;

import com.google.gson.annotations.SerializedName;
import model.Player;
import model.PlayerColor;
import server.ServerFacade;

/**
 *
 *
 * Created by rodriggl on 3/3/2016.
 */
public class PlayerInfo {
    private transient final ServerFacade serverFacade = ServerFacade.getServerFacade();
    @SerializedName("username")
    private String username;
    @SerializedName("color")
    private String colorName;
    @SerializedName("playerOrder")
    private int playerOrder;

    public PlayerInfo(Player player, int playerOrder) {
        this.username = serverFacade.getAllUsers().get(player.getPlayerID()).getUsername();
        this.colorName = player.getPlayerColor().toString();
        this.playerOrder = playerOrder;
    }
}
