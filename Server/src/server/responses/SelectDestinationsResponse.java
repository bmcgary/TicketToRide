package server.responses;

import com.google.gson.annotations.SerializedName;
import server.dto.gameplay.DestinationCardInfo;
import server.dto.gameplay.PrivatePlayerInfo;

import java.util.List;

/**
 *
 * Created by rodriggl on 3/24/2016.
 */
public class SelectDestinationsResponse extends GamePlayResponse {
    @SerializedName("destinationCards")
    private List<DestinationCardInfo> destinationCardInfos;
    public SelectDestinationsResponse(int gameId, PrivatePlayerInfo privateInfo) {
        super("Success", gameId);
        this.destinationCardInfos = privateInfo.getDestinationCards();
    }
}
