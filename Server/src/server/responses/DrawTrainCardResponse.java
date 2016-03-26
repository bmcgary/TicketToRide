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
    @SerializedName("canDrawWild")
    private boolean candDrawWild;

    public DrawTrainCardResponse(int gameId, TrackColor color, boolean canDrawWild) {
        super("Success", gameId);
        this.cardColor = color.toString();
        this.candDrawWild = canDrawWild;
    }
}
