package server.command;

import com.google.gson.annotations.SerializedName;
import server.dto.gameplay.GamePlayInfo;
import server.exception.GameNotFoundException;
import server.responses.DataResponse;
import server.responses.Response;
import server.responses.ResponseWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class SendClientModelInformationCommand extends Command {
    @SerializedName("gameId")
    private int gameId;

    private transient GamePlayInfo gamePlayInfo;

    public SendClientModelInformationCommand(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public List<ResponseWrapper> execute(int userID) {
        return execute(Collections.singletonList(userID));
    }

    public List<ResponseWrapper> execute(List<Integer> playerIds) {
        List<ResponseWrapper> responses = new ArrayList<>();
        ResponseWrapper responseWrapper = new ResponseWrapper(commandName);
        responses.add(responseWrapper);
        if (gamePlayInfo == null) {
            try {
                gamePlayInfo = new GamePlayInfo(serverFacade.getGame(gameId));
            } catch (GameNotFoundException e) {
                responseWrapper.setTargetIds(playerIds).setResponse(Response.newServerErrorResponse());
                return responses;
            }
        }
        List<Integer> gamePlayInfoPlayerIds = gamePlayInfo.getPlayerIds();
        List<Integer> playersNotInGame = new ArrayList<>();
        List<Integer> playersInGame = new ArrayList<>();
        playerIds.forEach(playerId -> {
            if (gamePlayInfoPlayerIds.contains(playerId))
                playersInGame.add(playerId);
            else
                playersNotInGame.add(playerId);
        });

        ResponseWrapper notInGame = new ResponseWrapper(playersNotInGame, new Response("not in game"), commandName);
        responses.add(notInGame);

        responseWrapper.setTargetIds(playersInGame).setResponse(Response.newSuccessResponse());
        responses.add(new ResponseWrapper(playersInGame, gamePlayInfo, "PublicClientModelInformation"));

        playersInGame.parallelStream().forEach(playerId -> {
            ResponseWrapper privateResponseWrapper = new ResponseWrapper(playerId, "PrivateClientModelInformation");
            responses.add(privateResponseWrapper.setResponse(gamePlayInfo.getPrivateInfo(playerId)));
        });

        return responses;
    }

    public void setGamePlayInfo(GamePlayInfo gamePlayInfo) {
        this.gamePlayInfo = gamePlayInfo;
    }
}
