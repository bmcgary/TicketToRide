package server.dto;

import com.google.gson.annotations.SerializedName;
import model.Player;
import server.ServerFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * Created by rodriggl on 3/3/2016.
 */
public class PlayerInfo {
    // transfer information
    @SerializedName("username")
    private String username;
    @SerializedName("color")
    private String colorName;

    // internal information
    private transient int playerId;

    public PlayerInfo(Player player) {
        this.username = ServerFacade.getServerFacade().getAllUsers().parallelStream().filter(user -> player.getPlayerID() == user.getPlayerID()).findFirst().get().getUsername();
        this.colorName = player.getPlayerColor().toString();
        this.playerId = player.getPlayerID();
    }

    public int getPlayerId() {
        return playerId;
    }
}
