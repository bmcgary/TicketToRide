package server.dto;

import com.google.gson.annotations.SerializedName;
import model.Game;
import model.Player;

import java.util.List;

/**
 *
 *
 * Created by rodriggl on 3/3/2016.
 */
public class GameInfo {
    @SerializedName("gameId")
    private int gameId;
    @SerializedName("gameName")
    private String gameName;
    @SerializedName("players")
    private PlayerInfo[] playerInfos;
}
