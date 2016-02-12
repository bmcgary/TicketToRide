package server.command;

import com.google.gson.annotations.SerializedName;
import server.exception.AddUserException;
import server.exception.InternalServerException;
import server.ServerFacade;
import server.responses.Response;
import server.responses.ResponseWrapper;

import java.util.Collections;

/**
 *
 *
 * Created by rodriggl on 1/29/2016.
 */
public class RegisterCommand implements Command {
    @SerializedName("username")
    String username;
    @SerializedName("password")
    String password;
    @SerializedName("email")
    String email;

    @Override
    public ResponseWrapper execute(int userID) {
        ServerFacade serverFacade = ServerFacade.getServerFacade();
        try {
            userID = serverFacade.register(username, password);
        } catch (AddUserException e) {
            return new ResponseWrapper(-1, Response.newInvalidInputResponse());
        } catch (InternalServerException e) {
            return new ResponseWrapper(-1, Response.newServerErrorResponse());
        }
        return new ResponseWrapper(userID, Response.newSuccessResponse());
    }
}
