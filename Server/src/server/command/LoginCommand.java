package server.command;

import com.google.gson.annotations.SerializedName;
import server.exception.AlreadyLoggedInException;
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
public class LoginCommand extends Command {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    @Override
    public List<ResponseWrapper> execute(int userID) {
        ResponseWrapper responseWrapper = new ResponseWrapper(commandName);
        try {
            userID = serverFacade.login(username, password);
            
            responseWrapper.addTargetId(userID).setResponse(Response.newSuccessResponse());
        } catch (BadCredentialsException e) {
            responseWrapper.setResponse(Response.newInvalidInputResponse());
        } catch (AlreadyLoggedInException e) {
            responseWrapper.addTargetId(userID).setResponse(new Response("alreadyloggedin"));
        }
        return Collections.singletonList(responseWrapper);
    }
}
