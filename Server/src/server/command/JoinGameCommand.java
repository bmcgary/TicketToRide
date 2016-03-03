package server.command;
import com.google.gson.annotations.SerializedName;
import model.Player;
import model.PlayerColor;
import server.CommandParser;
import server.User;
import server.responses.Response;
import server.responses.ResponseWrapper;
import server.responses.UpdateGameResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                List<ResponseWrapper> commandResponses = command.execute(userID);
                responses.addAll(commandResponses);
            }
            return responses;
        } else {
            serverFacade.addPlayerToGame(userID, gameId, color);
            // start game if possible
            //Response gameResponse = new UpdateUserGamesResponse(gameId);

            Command userGames = new UpdateUserGamesCommand();
            List<Player> gamePlayers = serverFacade.getAllGames().get(gameId).getPlayerManager().getPlayers();
            gamePlayers.stream().forEach(player -> responses.addAll(userGames.execute(player.getPlayerID())));
            List<Integer> allUserIds = new ArrayList<>();
            serverFacade.getAllUsers().forEach(user -> allUserIds.add(user.getPlayerID()));
            //responses.add(new ResponseWrapper(allUserIds, "updateGame", new UpdateGameResponse()))
            return null;
        }
    }
}
