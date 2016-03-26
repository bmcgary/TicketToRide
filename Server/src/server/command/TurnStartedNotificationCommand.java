package server.command;

import com.google.gson.annotations.SerializedName;
import model.Game;
import server.ServerFacade;
import server.dto.gameplay.GamePlayInfo;
import server.exception.GameNotFoundException;
import server.exception.InvalidCredentialsException;
import server.responses.GameEndedResponse;
import server.responses.Response;
import server.responses.ResponseWrapper;
import server.responses.TurnStartedNotificationResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public abstract class TurnStartedNotificationCommand extends Command {
    @SerializedName("gameId")
    int gameId;

    transient GamePlayInfo gamePlayInfo;
    transient List<Integer> playerIds;

    @Override
    public List<ResponseWrapper> execute(int userID) {
        // Setup responses
        List<ResponseWrapper> responses = new ArrayList<>();
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, commandName);
        responses.add(responseWrapper);
        try {
            // get game info
            Game game = serverFacade.getGame(gameId);
            gamePlayInfo = new GamePlayInfo(game);
            playerIds = gamePlayInfo.getPlayerIds();

            // get base state
            int currentPlayerId = getCurrentPlayerId(game);

            // perform action
            responses.addAll(turnExecute(userID));

            // check state change
            if (game.isGameOver()) {
                // send game over response
                responses.add(new ResponseWrapper(playerIds, new GameEndedResponse(game), GameEndedResponse.getName()));
            } else if (currentPlayerId != getCurrentPlayerId(game)) {
                // send new turn notification
                responses.add(new ResponseWrapper(
                        playerIds,
                        new TurnStartedNotificationResponse(gameId, currentPlayerId, game.getPlayerManager().isFinalRound()),
                        TurnStartedNotificationResponse.getName()));
            }
        } catch (InvalidCredentialsException e) {
            responseWrapper.setResponse(Response.newInvalidInputResponse());
        } catch (GameNotFoundException e) {
            responseWrapper.setResponse(Response.newServerErrorResponse());
        }
        return responses;
    }

    public abstract List<ResponseWrapper> turnExecute(int userId);

    private int getCurrentPlayerId(Game game) {
        return playerIds.parallelStream().filter(game.getPlayerManager()::isPlayersTurn).findFirst().get();
    }
}
