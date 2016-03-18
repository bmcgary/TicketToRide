package server.command;

import model.Game;
import server.dto.lobby.LobbyGameInfo;
import server.responses.GamesResponse;
import server.responses.ResponseWrapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class UpdateUserGamesCommand extends Command {
    public UpdateUserGamesCommand() {
        super();
        setCommandName("UpdateUserGames");
    }

    @Override
    public List<ResponseWrapper> execute(int userID) {
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, commandName);
        List<Game> games = serverFacade.getUserGames(userID);
        List<LobbyGameInfo> lobbyGameInfos = games.parallelStream().map(LobbyGameInfo::new).collect(Collectors.toList());
        responseWrapper.setResponse(new GamesResponse(lobbyGameInfos));
        return Collections.singletonList(responseWrapper);
    }
}
