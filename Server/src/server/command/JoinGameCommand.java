package server.command;
import com.google.gson.annotations.SerializedName;
import model.Game;
import model.Player;
import model.PlayerColor;
import server.CommandParser;
import server.User;
import server.exception.GameNotFoundException;
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
public class JoinGameCommand extends Command {
    @SerializedName("gameId")
    private int gameId;
    @SerializedName("color")
    private String colorName;

    @Override
    public List<ResponseWrapper> execute(int userID) {
        List<ResponseWrapper> responses = new ArrayList<>();
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, commandName);
        PlayerColor color = PlayerColor.getColor(colorName);

        if (color == null || !serverFacade.canAddPlayerToGame(userID, gameId, color)) {
            responseWrapper.setResponse(Response.newInvalidInputResponse());
            responses.add(responseWrapper);
            if (color != null) {
                Command command = new UpdateJoinableGamesCommand();
                List<ResponseWrapper> commandResponses = command.preparedExecute(userID);
                responses.addAll(commandResponses);
            }
            return responses;
        } else {
            Game thisGame;
            try {
                serverFacade.addPlayerToGame(userID, gameId, color);
                thisGame = serverFacade.getGame(gameId);
            } catch (PreConditionException | GameNotFoundException e) {
                e.printStackTrace();
                responses.add(responseWrapper.setResponse(Response.newServerErrorResponse()));
                return responses;
            }
            responses.add(responseWrapper.setResponse(new GamePlayResponse(Response.getSuccessString(), gameId)));

            // get game

            // update game responses
            try {
                responses.add(new ResponseWrapper(-1, new UpdateGameResponse(thisGame, false), "UpdateGame"));
            } catch (InvalidCredentialsException e) {
                responses.add(new ResponseWrapper(-1, Response.newServerErrorResponse(), "UpdateGame"));
            }

            // update user games responses
            Command userGames = new UpdateUserGamesCommand();
            List<Player> players = thisGame.getPlayerManager().getPlayers();
            players.stream().forEach(player -> responses.addAll(userGames.preparedExecute(player.getPlayerID())));

            // start game response
            if (thisGame.getPlayerManager().getPlayers().size() == 5) {
                responses.addAll(new StartGameCommand(gameId).preparedExecute(-1));
            }

            return responses;
        }
    }
}
