package server.responses;

import com.google.gson.annotations.SerializedName;
import server.dto.gameplay.DestinationCardInfo;

import java.util.List;

/**
 *
 * Created by rodriggl on 3/30/2016.
 */
public class GetDestinationsResponse extends GamePlayResponse {
    @SerializedName("destinationCards")
    List<DestinationCardInfo> destinationCardInfos;

    public GetDestinationsResponse(int gameId, List<DestinationCardInfo> destinationCardInfos) {
        super(Response.getSuccessString(), gameId);
        this.destinationCardInfos = destinationCardInfos;
    }
}
