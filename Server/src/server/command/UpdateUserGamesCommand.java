package server.command;

import server.responses.Response;
import server.responses.ResponseWrapper;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class UpdateUserGamesCommand extends Command {
    @Override
    public ResponseWrapper execute(int userID) {
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, commandName);
        serverFacade.getUserGames(userID);
        return responseWrapper.setResponse(Response.newServerErrorResponse());
    }
}
