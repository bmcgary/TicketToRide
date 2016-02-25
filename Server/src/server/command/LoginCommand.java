package server.command;

import com.google.gson.annotations.SerializedName;
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
public class LoginCommand extends Command {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    @Override
    public ResponseWrapper execute(int userID) {
        ServerFacade serverFacade = ServerFacade.getServerFacade();
        try {
            userID = serverFacade.login(username, password);
        } catch (BadCredentialsException e) {
            return new ResponseWrapper(null, Response.newInvalidInputResponse(), super.getCommandName());
        }
        return new ResponseWrapper(userID, Response.newSuccessResponse(), super.getCommandName());
    }
}
