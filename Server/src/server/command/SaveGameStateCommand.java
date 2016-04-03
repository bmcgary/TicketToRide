package server.command;

import server.responses.Response;
import server.responses.ResponseWrapper;

import java.util.Collections;
import java.util.List;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class SaveGameStateCommand extends Command {
    @Override
    public List<ResponseWrapper> execute(int userID) {
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, commandName);
        serverFacade.saveGameState();
        return Collections.singletonList(responseWrapper.setResponse(Response.newSuccessResponse()));
    }
}
