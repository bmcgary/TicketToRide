package server.command;

import model.Game;
import server.responses.GamesResponse;
import server.responses.ResponseWrapper;
import server.dto.GameInfo;

import java.util.Collections;
import java.util.List;

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
        GameInfo[] gameInfo = new GameInfo[games.size()];
        for (int i = 0; i < games.size(); ++i) {
            gameInfo[i] = new GameInfo(games.get(i));
        }
        responseWrapper.setResponse(new GamesResponse(gameInfo));
        return Collections.singletonList(responseWrapper);
    }
}
