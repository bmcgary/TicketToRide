package server.command;

import server.BadCredentialsException;
import server.ServerFacade;
import server.responses.Response;
import server.responses.ResponseWrapper;

import java.util.Collections;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class LogoutCommand implements Command {

    @Override
    public ResponseWrapper execute(int userID) {
        ServerFacade serverFacade = ServerFacade.getServerFacade();
        try {
            serverFacade.logout(userID);
        } catch (BadCredentialsException e) {
            return new ResponseWrapper(Collections.singletonList(userID), Response.newInvalidInputResponse());
        }
        return new ResponseWrapper(Collections.singletonList(userID), Response.newSuccessResponse());
    }
}
