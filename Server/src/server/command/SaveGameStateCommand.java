package server.command;

import server.responses.Response;
import server.responses.ResponseWrapper;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class SaveGameStateCommand extends Command {
    @Override
    public ResponseWrapper execute(int userID) {
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, commandName);
        serverFacade.saveGameState();
        return responseWrapper.setResponse(Response.newSuccessResponse());
    }
}
