package server.command;

import com.google.gson.annotations.SerializedName;
import model.Game;
import server.dto.gameplay.GamePlayInfo;
import server.exception.GameNotFoundException;
import server.exception.InternalServerException;
import server.exception.PreConditionException;
import server.responses.Response;
import server.responses.ResponseWrapper;

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
        List<ResponseWrapper> responses = new ArrayList<>();
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, commandName);
        responses.add(responseWrapper);

        Game game;
        try {
            serverFacade.startGame(userID, gameId);
            game = serverFacade.getGame(gameId);
        } catch (PreConditionException e) {
            responseWrapper.setResponse(new Response("unable to start game"));
            return responses;
        } catch (InternalServerException | GameNotFoundException e) {
            responseWrapper.setResponse(Response.newServerErrorResponse());
            return responses;
        }

        GamePlayInfo gamePlayInfo = new GamePlayInfo(game);
        List<Integer> playerIds = gamePlayInfo.getPlayerIds();
        responseWrapper.setTargetIds(playerIds).setResponse(Response.newSuccessResponse());

        SendClientModelInformationCommand command = new SendClientModelInformationCommand(gameId);
        command.setGamePlayInfo(gamePlayInfo);
        responses.addAll(command.execute(playerIds));

        return responses;
    }
}
