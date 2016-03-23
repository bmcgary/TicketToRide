package server.command;

import model.Game;
import server.exception.InvalidCredentialsException;
import server.responses.GamesResponse;
import server.responses.Response;
import server.responses.ResponseWrapper;
import server.dto.lobby.LobbyGameInfo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class UpdateJoinableGamesCommand extends Command {
    public UpdateJoinableGamesCommand() {
        super();
        setCommandName("UpdateJoinableGames");
    }

    @Override
    public List<ResponseWrapper> execute(int userID) {
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, commandName);
        List<Game> games = serverFacade.getJoinableGames(userID);
        List<LobbyGameInfo> lobbyGameInfos = games.parallelStream().map(game -> {
            try {
                return new LobbyGameInfo(game);
            } catch (InvalidCredentialsException e) {
                return null;
            }
        }).collect(Collectors.toList());
        if (lobbyGameInfos.parallelStream().anyMatch(lobbyGameInfo -> lobbyGameInfo == null))
            responseWrapper.setResponse(Response.newServerErrorResponse());
        else
            responseWrapper.setResponse(new GamesResponse(lobbyGameInfos));
        return Collections.singletonList(responseWrapper);
    }
}
