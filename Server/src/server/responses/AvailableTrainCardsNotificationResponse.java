package server.responses;

import com.google.gson.annotations.SerializedName;
import model.TrackColor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by rodriggl on 3/26/2016.
 */
public class AvailableTrainCardsNotificationResponse extends GamePlayResponse {
    @SerializedName("availableTrainCards")
    private List<AvailableTrainCardInfo> cards;
    public AvailableTrainCardsNotificationResponse(int gameId, TrackColor[] availableTrackCards) {
        super(null, gameId);
        cards = new ArrayList<>();
        for (int i = 0; i < availableTrackCards.length; ++i) {
            cards.add(new AvailableTrainCardInfo(i, availableTrackCards[i]));
        }
    }

    public static String getName() {
        return "AvailableTrainCardsNotification";
    }

    private class AvailableTrainCardInfo {
        @SerializedName("index")
        private int index;
        @SerializedName("color")
        private String color;

        AvailableTrainCardInfo(int index, TrackColor color) {
            this.index = index;
            this.color = color.toString();
        }
    }
}
