package server.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 * Created by rodriggl on 3/15/2016.
 */
public abstract class GameInfo {
    @SerializedName("gameID")
    protected int gameId;

    public abstract List<Integer> getPlayerIds();
}
