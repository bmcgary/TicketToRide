package server.command;

import server.exception.BadCredentialsException;
import server.ServerFacade;
import server.responses.Response;
import server.responses.ResponseWrapper;

import java.util.Collections;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class LogoutCommand extends Command {

    @Override
    public ResponseWrapper execute(int userID) {
        ResponseWrapper responseWrapper = new ResponseWrapper(userID, null, commandName);
        try {
            serverFacade.logout(userID);
            responseWrapper.setResponse(Response.newSuccessResponse());
        } catch (BadCredentialsException e) {
            responseWrapper.setResponse(Response.newInvalidInputResponse());
        }
        return responseWrapper;
    }
}
