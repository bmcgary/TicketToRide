package server.command;

import com.google.gson.annotations.SerializedName;
import model.Game;
import server.dto.gameplay.GamePlayInfo;
import server.exception.GameNotFoundException;
import server.exception.InvalidCredentialsException;
import server.responses.GameEndedResponse;
import server.responses.Response;
import server.responses.ResponseWrapper;
import server.responses.TurnStartedNotificationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public abstract class TurnStartedNotificationCommand extends Command {
    @SerializedName("gameId")
    int gameId;

    transient Game game;
    transient List<Integer> playerIds;

    @Override
    public List<ResponseWrapper> execute(int userID) {
        // Setup responses
        List<ResponseWrapper> responses = new ArrayList<>();
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, commandName);
        try {
            // get game info
            game = serverFacade.getGame(gameId);
            playerIds = new GamePlayInfo(game).getPlayerIds();

            // get base state
            int currentPlayerIndex = getCurrentPlayerIndex(playerIds, game);

            // perform action
            responses.addAll(turnExecute(userID));

            // check state change
            if (game.isGameOver()) {
                // send game over response
                responses.add(new ResponseWrapper(playerIds, new GameEndedResponse(game), GameEndedResponse.getName()));
            } else if (currentPlayerIndex != getCurrentPlayerIndex(playerIds, game)) {
                // send new turn notification
                responses.add(new ResponseWrapper(
                        playerIds,
                        new TurnStartedNotificationResponse(gameId, getCurrentPlayerIndex(playerIds, game), game.getPlayerManager().isFinalRound()),
                        TurnStartedNotificationResponse.getName()));
            }
        } catch (InvalidCredentialsException e) {
            responseWrapper.setResponse(Response.newInvalidInputResponse());
            responses.add(responseWrapper);
        } catch (GameNotFoundException | NoSuchElementException e) {
            responseWrapper.setResponse(Response.newServerErrorResponse());
            responses.add(responseWrapper);
        }
        return responses;
    }

    public abstract List<ResponseWrapper> turnExecute(int userId);

    public static int getCurrentPlayerIndex(List<Integer> ids, Game game) {
        for (int i = 0; i < ids.size(); ++i) {
            if (game.getPlayerManager().isPlayersTurn(ids.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
