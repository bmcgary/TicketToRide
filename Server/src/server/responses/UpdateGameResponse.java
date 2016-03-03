package server.responses;

import com.google.gson.annotations.SerializedName;
import server.JsonTranslator;
import server.responses.dto.GameInfo;

/**
 * Created by rodriggl on 3/3/2016.
 */
public class UpdateGameResponse {
    @SerializedName("gameId")
    private int gameId;
    @SerializedName("game")
    private GameInfo gameInfo;

    public static void main(String[] args) {
        UpdateGameResponse updateGameResponse = new UpdateGameResponse();
        updateGameResponse.gameId = 2;
        updateGameResponse.gameInfo = null;
        System.out.println(JsonTranslator.getGson().toJson(updateGameResponse));
    }
}
