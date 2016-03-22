package server.dto.gameplay;

import com.google.gson.annotations.SerializedName;
import model.City;
import model.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Created by rodriggl on 3/15/2016.
 */
public class PrivatePlayerInfo {
    @SerializedName("gameId")
    private int gameId;
    @SerializedName("playerOrder")
    private int playerOrder;
    @SerializedName("trainCards")
    private List<TrainCardInfo> trains;
    @SerializedName("destinationCards")
    private List<DestinationCardInfo> destinationCards;
    @SerializedName("possibleDestinationCards")
    private List<DestinationCardInfo> possibleDestinationCards;

    public PrivatePlayerInfo(int gameId, Player player, int playerOrder) {
        this.gameId = gameId;
        this.playerOrder = playerOrder;
        this.trains = player.getTrainCarCards().entrySet().parallelStream().map(TrainCardInfo::new).collect(Collectors.toList());
        this.destinationCards = player.getDestinationRoute().parallelStream().map(DestinationCardInfo::new).collect(Collectors.toList());
        this.possibleDestinationCards = player.getDestinationRoutesToConsider().parallelStream().map(DestinationCardInfo::new).collect(Collectors.toList());
    }
}
