package server.responses;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by rodriggl on 3/25/2016.
 */
public class TurnStartedNotificationResponse extends GamePlayResponse {
    @SerializedName("playerIndex")
    private int playerIndex;
    @SerializedName("lastRound")
    private boolean isLastRound;

    public TurnStartedNotificationResponse(int gameId, int playerIndex, boolean isLastRound) {
        super(null, gameId);
        this.playerIndex = playerIndex;
        this.isLastRound = isLastRound;
    }

    public static String getName() {
        return "TurnStartedNotification";
    }
}
