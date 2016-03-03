package server.command;

import com.google.gson.annotations.SerializedName;
import server.exception.AddUserException;
import server.exception.InternalServerException;
import server.responses.Response;
import server.responses.ResponseWrapper;

import java.util.Collections;
import java.util.List;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class RegisterCommand extends Command {
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;
    @SerializedName("email")
    String email;

    @Override
    public List<ResponseWrapper> execute(int userID) {
        ResponseWrapper responseWrapper = new ResponseWrapper(commandName);
        try {
            userID = serverFacade.register(username, password);
            responseWrapper.addTargetId(userID).setResponse(Response.newSuccessResponse());
        } catch (AddUserException e) {
            responseWrapper.setResponse(Response.newInvalidInputResponse());
        } catch (InternalServerException e) {
            responseWrapper.setResponse(Response.newServerErrorResponse());
        }
        return Collections.singletonList(responseWrapper);
    }
}
