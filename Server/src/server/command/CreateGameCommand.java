package server.command;

import com.google.gson.annotations.SerializedName;
import model.Game;
import model.PlayerColor;
import server.User;
import server.exception.InternalServerException;
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

        Game game;
        if (gameName == null)
            game = new Game();
        else
            game = new Game(gameName);

        try {
            serverFacade.createGame(game, userID, color);
        } catch (InternalServerException e) {
            e.printStackTrace();
            responseWrapper.setResponse(Response.newServerErrorResponse());
            responses.add(responseWrapper);
            return responses;
        }

        responseWrapper.setResponse(Response.newSuccessResponse());
        responses.add(responseWrapper);

        responses.add(new ResponseWrapper(-1, new UpdateGameResponse(game, true), "UpdateGame"));
        responses.add(new ResponseWrapper(userID, new UpdateGameResponse(game.getGameID()), "UpdateGame"));

        responses.addAll(new UpdateUserGamesCommand().execute(userID));

        return responses;
    }
}
