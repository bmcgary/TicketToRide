package server.dto;

import com.google.gson.annotations.SerializedName;
import model.Player;
import server.ServerFacade;
import server.exception.InvalidCredentialsException;

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

        // TODO: more elegant error catching!
        // right now, if a user doesn't exist, we will not have reached this point.
        try {
            this.username = ServerFacade.getServerFacade().getUser(player.getPlayerID()).getUsername();
        } catch (InvalidCredentialsException e) {
            this.username = null;
        }
        this.colorName = player.getPlayerColor().toString();
        this.playerId = player.getPlayerID();
    }

    public int getPlayerId() {
        return playerId;
    }
}
