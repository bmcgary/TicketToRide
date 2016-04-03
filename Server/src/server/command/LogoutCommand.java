package server.command;

import server.exception.BadCredentialsException;
import server.responses.Response;
import server.responses.ResponseWrapper;

import java.util.Collections;
import java.util.List;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class LogoutCommand extends Command {

    @Override
    public List<ResponseWrapper> execute(int userID) {
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, null, commandName);
        try {
            serverFacade.logout(userID);
            responseWrapper.setResponse(Response.newSuccessResponse());
        } catch (BadCredentialsException e) {
            responseWrapper.setResponse(Response.newInvalidInputResponse());
        }
        return Collections.singletonList(responseWrapper);
    }
}
