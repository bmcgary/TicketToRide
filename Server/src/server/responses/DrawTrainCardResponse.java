package server.responses;

import com.google.gson.annotations.SerializedName;
import model.TrackColor;

/**
 *
 * Created by rodriggl on 3/26/2016.
 */
public class DrawTrainCardResponse extends GamePlayResponse{
    @SerializedName("cardDrawn")
    private String cardColor;
    @SerializedName("canDrawAgain")
    private boolean canDrawAgain;

    public DrawTrainCardResponse(int gameId, TrackColor color, boolean canDrawAgain) {
        super("Success", gameId);
        this.cardColor = color.toString();
        this.canDrawAgain = canDrawAgain;
    }
}
