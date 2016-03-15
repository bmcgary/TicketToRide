package server.command;

import model.Game;
import server.responses.GamesResponse;
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
        List<LobbyGameInfo> lobbyGameInfos = games.parallelStream().map(LobbyGameInfo::new).collect(Collectors.toList());
        responseWrapper.setResponse(new GamesResponse(lobbyGameInfos));
        return Collections.singletonList(responseWrapper);
    }
}
