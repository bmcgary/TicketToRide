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
    @SerializedName("playerOrder")
    private int playerOrder;
    @SerializedName("trainCards")
    private List<TrainCardInfo> trains;
    @SerializedName("destinationCards")
    private List<DestinationCardInfo> destinationCards;
    @SerializedName("possibleDestinationCards")
    private List<DestinationCardInfo> possibleDestinationCards;

    public PrivatePlayerInfo(Player player, int playerOrder, List<City> cities) {
        this.playerOrder = playerOrder;
        this.trains = player.getTrainCarCards().entrySet().parallelStream().map(TrainCardInfo::new).collect(Collectors.toList());
        this.destinationCards = player.getDestinationRoute().parallelStream().map(route -> new DestinationCardInfo(route, cities)).collect(Collectors.toList());
        this.possibleDestinationCards = player.getDestinationRoutesToConsider().parallelStream().map(route -> new DestinationCardInfo(route, cities)).collect(Collectors.toList());
    }
}
