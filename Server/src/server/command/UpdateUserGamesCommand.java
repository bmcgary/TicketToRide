package server.command;

import model.Game;
import server.responses.GamesResponse;
import server.responses.Response;
import server.responses.ResponseWrapper;
import server.responses.dto.GameInfo;

import java.util.List;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class UpdateUserGamesCommand extends Command {
    @Override
    public ResponseWrapper execute(int userID) {
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, commandName);
        List<Game> games = serverFacade.getUserGames(userID);
        GameInfo[] gameInfo = new GameInfo[games.size()];
        for (int i = 0; i < games.size(); ++i) {
            gameInfo[i] = new GameInfo(games.get(i));
        }
        return responseWrapper.setResponse(new GamesResponse(gameInfo, Response.getSuccessString()));
    }
}
