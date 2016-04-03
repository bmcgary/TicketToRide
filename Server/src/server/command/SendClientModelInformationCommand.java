package server.command;

import com.google.gson.annotations.SerializedName;
import model.Game;
import server.dto.gameplay.GamePlayInfo;
import server.exception.GameNotFoundException;
import server.exception.InvalidCredentialsException;
import server.responses.AvailableTrainCardsNotificationResponse;
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
    private transient boolean sendPublic = false;

    public SendClientModelInformationCommand(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public List<ResponseWrapper> execute(int userID) {
        return execute(Collections.singletonList(userID));
    }

    public List<ResponseWrapper> execute(List<Integer> playerIds) {
        List<ResponseWrapper> responses = new ArrayList<>();
        ResponseWrapper responseWrapper = new ResponseWrapper(getName());
        responses.add(responseWrapper);
        Game game;
        try {
            game = serverFacade.getGame(gameId);
            if (gamePlayInfo == null) {
                gamePlayInfo = new GamePlayInfo(game);
            }
        } catch (GameNotFoundException | InvalidCredentialsException e) {
            responseWrapper.setTargetIds(playerIds).setResponse(Response.newServerErrorResponse());
            return responses;
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

        if (!playersNotInGame.isEmpty()) {
            ResponseWrapper notInGame = new ResponseWrapper(playersNotInGame, new Response("not in game"), getName());
            responses.add(notInGame);
        }

        playersInGame.parallelStream().forEach(playerId -> {
            ResponseWrapper privateResponseWrapper = new ResponseWrapper(playerId, "PrivateClientModelInformation");
            responses.add(privateResponseWrapper.setResponse(gamePlayInfo.getPrivateInfo(playerId)));
        });

        responseWrapper.setTargetIds(sendPublic ? gamePlayInfoPlayerIds : playersInGame).setResponse(Response.newSuccessResponse());
        responses.add(new ResponseWrapper(playersInGame, gamePlayInfo, "PublicClientModelInformation"));

        AvailableTrainCardsNotificationResponse response = new AvailableTrainCardsNotificationResponse(gameId, game.getGameBoard().getVisibleTrainCarCards());
        responses.add(new ResponseWrapper(playersInGame, response, AvailableTrainCardsNotificationResponse.getName()));

        return responses;
    }

    public void setGamePlayInfo(GamePlayInfo gamePlayInfo) {
        this.gamePlayInfo = gamePlayInfo;
    }

    public SendClientModelInformationCommand setSendPublic(boolean sendPublic) {
        this.sendPublic = sendPublic;
        return this;
    }

    public static String getName() {
        return "SendClientModelInformation";
    }
}
