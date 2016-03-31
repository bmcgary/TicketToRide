package server.command;

import com.google.gson.annotations.SerializedName;
import model.Game;
import server.ServerFacade;
import server.dto.gameplay.GamePlayInfo;
import server.exception.GameNotFoundException;
import server.exception.InternalServerException;
import server.exception.InvalidCredentialsException;
import server.exception.PreConditionException;
import server.responses.GamePlayResponse;
import server.responses.Response;
import server.responses.ResponseWrapper;
import server.responses.TurnStartedNotificationResponse;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class StartGameCommand extends Command {
    @SerializedName("gameId")
    private int gameId;

    public StartGameCommand(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public List<ResponseWrapper> execute(int userID) {
        serverFacade = ServerFacade.getServerFacade();
        List<ResponseWrapper> responses = new ArrayList<>();
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, getName());

        Game game;
        GamePlayInfo gamePlayInfo;
        try {
            serverFacade.startGame(userID, gameId);
            game = serverFacade.getGame(gameId);
            gamePlayInfo = new GamePlayInfo(game);
        } catch (PreConditionException e) {
            responseWrapper.setResponse(new Response("unable to start game").setMessage(e.getMessage()));
            return responses;
        } catch (InternalServerException | GameNotFoundException | InvalidCredentialsException e) {
            responseWrapper.setResponse(Response.newServerErrorResponse());
            return responses;
        }

        List<Integer> playerIds = gamePlayInfo.getPlayerIds();
        responseWrapper.setTargetIds(playerIds).setResponse(new GamePlayResponse(Response.getSuccessString(), gameId));

        SendClientModelInformationCommand command = new SendClientModelInformationCommand(gameId);
        command.setGamePlayInfo(gamePlayInfo);
        responses.addAll(command.execute(playerIds));

        int currentTurn = playerIds.parallelStream().filter(game.getPlayerManager()::isPlayersTurn).findFirst().get();

        responses.add(new ResponseWrapper(playerIds, new TurnStartedNotificationResponse(gameId, currentTurn, false),TurnStartedNotificationResponse.getName()));

        responses.add(responseWrapper);
        return responses;
    }

    public static String getName() {
        return "StartGame";
    }
}
