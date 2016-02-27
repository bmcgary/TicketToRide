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
public class RegisterCommand extends Command {
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
            return new ResponseWrapper(null, Response.newInvalidInputResponse(), super.getCommandName());
        } catch (InternalServerException e) {
            return new ResponseWrapper(null, Response.newServerErrorResponse(), super.getCommandName());
        }
        return new ResponseWrapper(userID, Response.newSuccessResponse(), super.getCommandName());
    }
}
