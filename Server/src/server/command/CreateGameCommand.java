package server.command;

import com.google.gson.annotations.SerializedName;
import model.Game;
import model.PlayerColor;
import server.User;
import server.exception.InternalServerException;
import server.exception.InvalidCredentialsException;
import server.exception.PreConditionException;
import server.responses.GamePlayResponse;
import server.responses.Response;
import server.responses.ResponseWrapper;
import server.responses.UpdateGameResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class CreateGameCommand extends Command {
    @SerializedName("gameName")
    private String gameName;
    @SerializedName("color")
    private String colorName;

    @Override
    public List<ResponseWrapper> execute(int userID) {
        List<ResponseWrapper> responses = new ArrayList<>();
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, commandName);
        PlayerColor color = PlayerColor.getColor(colorName);

        Game game = new Game(gameName);

        try {
            serverFacade.createGame(game, userID, color);
        } catch (InternalServerException | PreConditionException e) {
            e.printStackTrace();
            responseWrapper.setResponse(Response.newServerErrorResponse());
            responses.add(responseWrapper);
            return responses;
        }

        responseWrapper.setResponse(new GamePlayResponse(Response.getSuccessString(), game.getGameID()));
        responses.add(responseWrapper);

        try {
            responses.add(new ResponseWrapper(-1, new UpdateGameResponse(game, true), "UpdateGame"));
        } catch (InvalidCredentialsException e) {
            responses.add(new ResponseWrapper(-1, Response.newServerErrorResponse(), "UpdateGame"));
        }
        responses.add(new ResponseWrapper(userID, new UpdateGameResponse(game.getGameID()), "UpdateGame"));

        responses.addAll(new UpdateUserGamesCommand().execute(userID));

        return responses;
    }
}
